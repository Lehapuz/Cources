package org.example.repository;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.entities.Course;
import org.example.entities.Student;
import org.example.entities.Teacher;

import java.util.List;
import java.util.Optional;

public interface Repository {

    Optional<Teacher> getTeacherByName(String name, ConnectionPool connectionPool);

    Optional<Course> getCourseByName(String name, ConnectionPool connectionPool);

    Optional<Student> getStudentByName(String name, ConnectionPool connectionPool);

    void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, ConnectionPool connectionPool);

    void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, int teacherId,
                   ConnectionPool connectionPool);

    void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto, ConnectionPool connectionPool);
    
    void addSubscription(int studentId, int CourseId, ConnectionPool connectionPool);

    List<Teacher> getAllTeachers(ConnectionPool connectionPool);

    List<Course> getAllCourses(ConnectionPool connectionPool);
}
