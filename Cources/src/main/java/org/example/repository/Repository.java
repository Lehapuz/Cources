package org.example.repository;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.entities.Course;
import org.example.entities.Student;
import org.example.entities.Teacher;

import java.util.List;
import java.util.Optional;

public interface Repository {

    Optional<Teacher> getTeacherByName(String name);

    Optional<Course> getCourseByName(String name);

    Optional<Student> getStudentByName(String name);

    void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto);

    void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, int teacherId);

    void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto);
    
    void addSubscription(int studentId, int CourseId);

    List<Teacher> getAllTeachers();

    List<Course> getAllCourses();
}
