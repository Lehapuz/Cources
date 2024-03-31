package org.example.service;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.dto.response.CourseWithStudentsResponseDto;
import org.example.dto.response.TeacherResponseDto;
import org.example.entities.Teacher;
import org.example.repository.ConnectionPool;

import java.util.List;
import java.util.Optional;

public interface Service {

    void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, ConnectionPool connectionPool);

    void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, int teacherId,
                   ConnectionPool connectionPool);

    void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto, ConnectionPool connectionPool);

    void addSubscription(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto, ConnectionPool connectionPool);

    List<TeacherResponseDto> getAllTeachers(ConnectionPool connectionPool);

    List<CourseWithStudentsResponseDto> getAllCourses(ConnectionPool connectionPool);

    Optional<Teacher> getTeacherByName(String name, ConnectionPool connectionPool);
}
