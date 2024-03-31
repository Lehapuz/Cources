package org.example.repository.impl;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.entities.Course;
import org.example.entities.Student;
import org.example.entities.Teacher;
import org.example.repository.ConnectionPool;
import org.example.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryImpl implements Repository {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String DURATION = "duration";
    private static final String PRICE = "price";
    private static final String TEACHER_ID = "teacher_id";
    private static final String COURSE_ID = "course_id";
    private static final String STUDENT_ID = "student_id";

    @Override
    public Optional<Teacher> getTeacherByName(String name, ConnectionPool connectionPool) {
        String sql = "SELECT * FROM teachers WHERE name = ?";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt(ID));
                teacher.setName(resultSet.getString(NAME));
                teacher.setAge(resultSet.getInt(AGE));
                connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(teacher);
            }
            connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> getCourseByName(String name, ConnectionPool connectionPool) {
        String sql = "SELECT * FROM courses WHERE name = ?";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt(ID));
                course.setName(resultSet.getString(NAME));
                course.setDuration(resultSet.getInt(DURATION));
                course.setPrice(resultSet.getDouble(PRICE));
                connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(course);
            }
            connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> getStudentByName(String name, ConnectionPool connectionPool) {
        String sql = "SELECT * FROM students WHERE name = ?";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(ID));
                student.setName(resultSet.getString(NAME));
                student.setAge(resultSet.getInt(AGE));
                connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(student);
            }
            connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto,
                           ConnectionPool connectionPool) {
        String sql = "INSERT INTO teachers (name, age) VALUES (?, ?)";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addCourseWithTeacherRequestDto.getTeacherName());
            preparedStatement.setInt(2, addCourseWithTeacherRequestDto.getAge());
            preparedStatement.execute();
            connectionPool.givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            throw new RuntimeException("Can not add teacher");
        }
    }

    @Override
    public void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, int teacherId,
                          ConnectionPool connectionPool) {
        String sql = "INSERT INTO courses (name, duration, price, teacher_id) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addCourseWithTeacherRequestDto.getCourseName());
            preparedStatement.setInt(2, addCourseWithTeacherRequestDto.getDuration());
            preparedStatement.setDouble(3, addCourseWithTeacherRequestDto.getPrice());
            preparedStatement.setInt(4, teacherId);
            preparedStatement.execute();
            connectionPool.givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            throw new RuntimeException("Can not add course");
        }
    }

    @Override
    public void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto,
                           ConnectionPool connectionPool) {
        String sql = "INSERT INTO students (name, age) VALUES (?, ?)";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addStudentWithCourseRequestDto.getStudentName());
            preparedStatement.setInt(2, addStudentWithCourseRequestDto.getAge());
            preparedStatement.execute();
            connectionPool.givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Can not add student");
        }
    }

    @Override
    public void addSubscription(int studentId, int courseId, ConnectionPool connectionPool) {
        String sql = "INSERT INTO subscriptions (student_id, course_id) VALUES (?, ?)";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();
            connectionPool.givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Can not add student");
        }
    }

    @Override
    public List<Teacher> getAllTeachers(ConnectionPool connectionPool) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Course> courses = new ArrayList<>();
                String subSql = "SELECT * FROM courses";
                PreparedStatement subPreparedStatement = connection.prepareStatement(subSql);
                ResultSet subResultSet = subPreparedStatement.executeQuery();
                while (subResultSet.next()) {
                    if (subResultSet.getInt(TEACHER_ID) == resultSet.getInt(ID)) {
                        Course course = new Course();
                        course.setId(subResultSet.getInt(ID));
                        course.setName(subResultSet.getString(NAME));
                        course.setDuration(subResultSet.getInt(DURATION));
                        course.setPrice(subResultSet.getDouble(PRICE));
                        courses.add(course);
                    }
                }
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt(ID));
                teacher.setName(resultSet.getString(NAME));
                teacher.setAge(resultSet.getInt(AGE));
                teacher.setCourses(courses);
                teachers.add(teacher);
            }
            connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }

    @Override
    public List<Course> getAllCourses(ConnectionPool connectionPool) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Student> students = new ArrayList<>();
                String subSql = "SELECT * FROM subscriptions";
                PreparedStatement subPreparedStatement = connection.prepareStatement(subSql);
                ResultSet subResultSet = subPreparedStatement.executeQuery();
                while (subResultSet.next()) {
                    if (subResultSet.getInt(COURSE_ID) == resultSet.getInt(ID)) {
                        Optional<Student> student = getStudentById(subResultSet.getInt(STUDENT_ID), connectionPool);
                        students.add(student.get());
                    }
                }
                Course course = new Course();
                course.setId(resultSet.getInt(ID));
                course.setName(resultSet.getString(NAME));
                course.setDuration(resultSet.getInt(DURATION));
                course.setPrice(resultSet.getDouble(PRICE));
                course.setStudents(students);
                courses.add(course);
            }
            connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    private Optional<Student> getStudentById(int id, ConnectionPool connectionPool) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(ID));
                student.setName(resultSet.getString(NAME));
                student.setAge(resultSet.getInt(AGE));
                connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(student);
            }
            connectionPool.givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
