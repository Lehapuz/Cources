package org.example.repository.impl;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.entities.Course;
import org.example.entities.Student;
import org.example.entities.Subscription;
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
    @Override
    public Optional<Teacher> getTeacherByName(String name) {
        String sql = "SELECT * FROM teachers WHERE name = ?";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setAge(resultSet.getInt("age"));
                ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(teacher);
            }
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> getCourseByName(String name) {
        String sql = "SELECT * FROM courses WHERE name = ?";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setDuration(resultSet.getInt("duration"));
                course.setPrice(resultSet.getDouble("price"));
                ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(course);
            }
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> getStudentByName(String name) {
        String sql = "SELECT * FROM students WHERE name = ?";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(student);
            }
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto) {
        String sql = "INSERT INTO teachers (name, age) VALUES (?, ?)";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addCourseWithTeacherRequestDto.getTeacherName());
            preparedStatement.setInt(2, addCourseWithTeacherRequestDto.getAge());
            preparedStatement.execute();
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            throw new RuntimeException("Can not add teacher");
        }
    }

    @Override
    public void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, int teacherId) {
        String sql = "INSERT INTO courses (name, duration, price, teacher_id) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addCourseWithTeacherRequestDto.getCourseName());
            preparedStatement.setInt(2, addCourseWithTeacherRequestDto.getDuration());
            preparedStatement.setDouble(3, addCourseWithTeacherRequestDto.getPrice());
            preparedStatement.setInt(4, teacherId);
            preparedStatement.execute();
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Can not add course");
        }
    }

    @Override
    public void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto) {
        String sql = "INSERT INTO students (name, age) VALUES (?, ?)";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addStudentWithCourseRequestDto.getStudentName());
            preparedStatement.setInt(2, addStudentWithCourseRequestDto.getAge());
            preparedStatement.execute();
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Can not add student");
        }
    }

    @Override
    public void addSubscription(int studentId, int courseId) {
        String sql = "INSERT INTO subscriptions (student_id, course_id) VALUES (?, ?)";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement);
        } catch (SQLException | InterruptedException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Can not add student");
        }
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Course> courses = new ArrayList<>();
                String subSql = "SELECT * FROM courses";
                PreparedStatement subPreparedStatement = connection.prepareStatement(subSql);
                ResultSet subResultSet = subPreparedStatement.executeQuery();
                while (subResultSet.next()) {
                    if (subResultSet.getInt("teacher_id") == resultSet.getInt("id")) {
                        Course course = new Course();
                        course.setId(subResultSet.getInt("id"));
                        course.setName(subResultSet.getString("name"));
                        course.setDuration(subResultSet.getInt("duration"));
                        course.setPrice(subResultSet.getDouble("price"));
                        courses.add(course);
                    }
                }
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setAge(resultSet.getInt("age"));
                teacher.setCourses(courses);
                teachers.add(teacher);
            }
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " - main");
                List<Student> students = new ArrayList<>();
                String subSql = "SELECT * FROM subscriptions";
                PreparedStatement subPreparedStatement = connection.prepareStatement(subSql);
                ResultSet subResultSet = subPreparedStatement.executeQuery();
                while (subResultSet.next()) {
                    if (subResultSet.getInt("course_id") == resultSet.getInt("id")) {
                        System.out.println(subResultSet.getInt("course_id"));
                        System.out.println(subResultSet.getInt("student_id"));
                        System.out.println(resultSet.getInt("id"));
                        Optional<Student> student = getStudentById(subResultSet.getInt("student_id"));
                        students.add(student.get());
                    }
                }
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setDuration(resultSet.getInt("duration"));
                course.setPrice(resultSet.getDouble("price"));
                course.setStudents(students);
                courses.add(course);
            }
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    private Optional<Student> getStudentById(int id) {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        try {
            Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
                return Optional.of(student);
            }
            ConnectionPool.getInstance().givenAwayConnection(connection, preparedStatement, resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
