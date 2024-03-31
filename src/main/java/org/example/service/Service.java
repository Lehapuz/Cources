package org.example.service;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.dto.response.CourseWithStudentsResponseDto;
import org.example.dto.response.TeacherResponseDto;
import org.example.entities.Teacher;

import java.util.List;
import java.util.Optional;

public interface Service {

    void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto);

    void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto, int teacherId);

    void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto);

    void addSubscription(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto);

    List<TeacherResponseDto> getAllTeachers();

    List<CourseWithStudentsResponseDto> getAllCourses();

    Optional<Teacher> getTeacherByName(String name);
}
