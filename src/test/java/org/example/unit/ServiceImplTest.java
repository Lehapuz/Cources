package org.example.unit;

import org.example.MockData;
import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.repository.ConnectionPool;
import org.example.repository.impl.RepositoryImpl;
import org.example.service.impl.ServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public final class ServiceImplTest {

    @Mock
    RepositoryImpl repository;
    @InjectMocks
    ServiceImpl service;

    @Test
    void add_teacher_test() {
        AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto = MockData.getAddCourseWithTeacherRequestDto();
        service.addTeacher(addCourseWithTeacherRequestDto);
        verify(repository).getTeacherByName("Slava", ConnectionPool.getInstance());
        verify(repository).addTeacher(addCourseWithTeacherRequestDto, ConnectionPool.getInstance());
    }

    @Test
    void add_course_test() {
        AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto = new AddCourseWithTeacherRequestDto();
        service.addCourse(addCourseWithTeacherRequestDto, MockData.getTeacher3().getId());
        verify(repository).addCourse(addCourseWithTeacherRequestDto, MockData.getTeacher3().getId(),
                ConnectionPool.getInstance());
    }

    @Test
    void add_student() {
        AddStudentWithCourseRequestDto addStudentWithCourseRequestDto = MockData.getAddStudentWithCourseRequestDto();
        service.addStudent(addStudentWithCourseRequestDto);
        verify(repository).addStudent(addStudentWithCourseRequestDto, ConnectionPool.getInstance());
    }

    @Test
    void add_subscription() {
        AddStudentWithCourseRequestDto addStudentWithCourseRequestDto = MockData.getAddStudentWithCourseRequestDto();
        when(repository.getStudentByName(addStudentWithCourseRequestDto.getStudentName(), ConnectionPool.getInstance()))
                .thenReturn(Optional.of(MockData.getStudent1()));
        when(repository.getCourseByName(addStudentWithCourseRequestDto.getCourseName(), ConnectionPool.getInstance()))
                .thenReturn(Optional.of(MockData.getCourse3()));
        service.addSubscription(addStudentWithCourseRequestDto);
        verify(repository).addSubscription(1, 3, ConnectionPool.getInstance());
    }

    @Test
    void get_all_teachers() {
        int expectedSize = 2;
        when(repository.getAllTeachers(ConnectionPool.getInstance())).thenReturn(List
                .of(MockData.getTeacher1(), MockData.getTeacher2()));
        int resultSize = service.getAllTeachers().size();
        Assertions.assertEquals(expectedSize, resultSize);
        service.getAllTeachers();
    }

    @Test
    void get_all_courses() {
        int expectedSize = 2;
        when(repository.getAllCourses(ConnectionPool.getInstance())).thenReturn(List
                .of(MockData.getCourse1(), MockData.getCourse2()));
        int resultSize = service.getAllCourses().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    void get_teacher_by_name() {
        int expectedId = 2;
        when(repository.getTeacherByName("Fedor", ConnectionPool.getInstance()))
                .thenReturn(Optional.of(MockData.getTeacher2()));
        int resultId = service.getTeacherByName("Fedor").get().getId();
        Assertions.assertEquals(expectedId, resultId);
    }
}
