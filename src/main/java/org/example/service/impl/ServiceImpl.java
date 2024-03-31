package org.example.service.impl;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.dto.response.CourseResponseDto;
import org.example.dto.response.CourseWithStudentsResponseDto;
import org.example.dto.response.StudentResponseDto;
import org.example.dto.response.TeacherResponseDto;
import org.example.entities.Course;
import org.example.entities.Student;
import org.example.entities.Teacher;
import org.example.repository.ConnectionPool;
import org.example.repository.Repository;
import org.example.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceImpl implements Service {

    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto,
                           ConnectionPool connectionPool) {
        if (getTeacherByName(addCourseWithTeacherRequestDto.getTeacherName(), connectionPool).isEmpty()) {
            repository.addTeacher(addCourseWithTeacherRequestDto, connectionPool);
        }
    }

    @Override
    public void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, int teacherId,
                          ConnectionPool connectionPool) {
        repository.addCourse(addCourseWithTeacherRequestDto,
                teacherId, connectionPool);
    }

    @Override
    public void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto,
                           ConnectionPool connectionPool) {
        repository.addStudent(addStudentWithCourseRequestDto, connectionPool);
    }

    @Override
    public void addSubscription(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto,
                                ConnectionPool connectionPool) {
        Optional<Student> student = repository.getStudentByName(addStudentWithCourseRequestDto.getStudentName(),
                connectionPool);
        Optional<Course> course = repository.getCourseByName(addStudentWithCourseRequestDto.getCourseName(),
                connectionPool);
        repository.addSubscription(student.get().getId(), course.get().getId(),
                connectionPool);
    }

    @Override
    public List<TeacherResponseDto> getAllTeachers(ConnectionPool connectionPool) {
        List<Teacher> teachers = repository.getAllTeachers(connectionPool);
        List<TeacherResponseDto> teacherResponseDtoList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
            for (Course course : teacher.getCourses()) {
                CourseResponseDto courseResponseDto = new CourseResponseDto(course.getId(),
                        course.getName(), course.getDuration(), course.getPrice());
                courseResponseDtoList.add(courseResponseDto);
            }
            TeacherResponseDto teacherResponseDto = new TeacherResponseDto(teacher.getId(),
                    teacher.getName(), teacher.getAge(), courseResponseDtoList);
            teacherResponseDtoList.add(teacherResponseDto);
        }
        return teacherResponseDtoList;
    }

    @Override
    public List<CourseWithStudentsResponseDto> getAllCourses(ConnectionPool connectionPool) {
        List<Course> courses = repository.getAllCourses(connectionPool);
        List<CourseWithStudentsResponseDto> courseWithStudentsResponseDtoList = new ArrayList<>();
        for (Course course : courses) {
            List<StudentResponseDto> studentResponseDtoList = new ArrayList<>();
            for (Student student : course.getStudents()) {
                StudentResponseDto studentResponseDto = new StudentResponseDto(student.getId(),
                        student.getName(), student.getAge());
                studentResponseDtoList.add(studentResponseDto);
            }
            CourseWithStudentsResponseDto courseResponseDtoList = new CourseWithStudentsResponseDto(
                    course.getId(), course.getName(), course.getDuration(), course.getPrice(),
                    studentResponseDtoList);
            courseWithStudentsResponseDtoList.add(courseResponseDtoList);
        }
        return courseWithStudentsResponseDtoList;
    }

    @Override
    public Optional<Teacher> getTeacherByName(String name, ConnectionPool connectionPool) {
        return repository.getTeacherByName(name, connectionPool);
    }
}
