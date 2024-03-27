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
    public void addTeacher(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto) {
        if (getTeacherByName(addCourseWithTeacherRequestDto.getTeacherName()).isEmpty()) {
            repository.addTeacher(addCourseWithTeacherRequestDto);
        }
    }

    @Override
    public void addCourse(AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto) {
        repository.addCourse(addCourseWithTeacherRequestDto,
                getTeacherByName(addCourseWithTeacherRequestDto.getTeacherName()).get().getId());
    }

    @Override
    public void addStudent(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto) {
        repository.addStudent(addStudentWithCourseRequestDto);
    }

    @Override
    public void addSubscription(AddStudentWithCourseRequestDto addStudentWithCourseRequestDto) {
        Optional<Student> student = repository.getStudentByName(addStudentWithCourseRequestDto.getStudentName());
        Optional<Course> course = repository.getCourseByName(addStudentWithCourseRequestDto.getCourseName());
        repository.addSubscription(student.get().getId(), course.get().getId());
    }

    @Override
    public List<TeacherResponseDto> getAllTeachers() {
        List<Teacher> teachers = repository.getAllTeachers();
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
    public List<CourseWithStudentsResponseDto> getAllCourses() {
        List<Course> courses = repository.getAllCourses();
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

    private Optional<Teacher> getTeacherByName(String name) {
        return repository.getTeacherByName(name);
    }
}
