package org.example.unit;

import org.example.MockData;
import org.example.repository.ConnectionPool;
import org.example.repository.Repository;
import org.example.repository.impl.RepositoryImpl;
import org.example.service.Service;
import org.example.service.impl.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public final class ServiceImplTest {

    @Mock
    RepositoryImpl repository;
    @InjectMocks
    ServiceImpl service;

//    @Test
//    void add_teacher(){
//        //when(repository.addTeacher(MockData.getAddCourseWithTeacherRequestDto(), ConnectionPool.getInstance()))
//        //        .thenReturn()
//
//        service.addTeacher(MockData.getAddCourseWithTeacherRequestDto());
//    }
//
//    @Test
//    void add_course(){
//        //when(repository.addTeacher(MockData.getAddCourseWithTeacherRequestDto(), ConnectionPool.getInstance()))
//        //        .thenReturn()
//
//        service.addCourse(MockData.getAddCourseWithTeacherRequestDto());
//    }
//
//    @Test
//    void add_student(){
//        //when(repository.addTeacher(MockData.getAddCourseWithTeacherRequestDto(), ConnectionPool.getInstance()))
//        //        .thenReturn()
//
//        service.addStudent(MockData.getAddStudentWithCourseRequestDto());
//    }
//
//    @Test
//    void add_subscription(){
//        //when(repository.addTeacher(MockData.getAddCourseWithTeacherRequestDto(), ConnectionPool.getInstance()))
//        //        .thenReturn()
//
//        service.addSubscription(MockData.getAddStudentWithCourseRequestDto());
//    }
//
//    @Test
//    void get_all_teachers(){
//        //when(repository.addTeacher(MockData.getAddCourseWithTeacherRequestDto(), ConnectionPool.getInstance()))
//        //        .thenReturn()
//
//        service.getAllTeachers();
//    }
//
//    @Test
//    void get_all_courses(){
//        //when(repository.addTeacher(MockData.getAddCourseWithTeacherRequestDto(), ConnectionPool.getInstance()))
//        //        .thenReturn()
//
//        service.getAllCourses();
//    }



}
