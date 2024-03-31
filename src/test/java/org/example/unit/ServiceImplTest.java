package org.example.unit;

import org.example.MockData;
import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.repository.ConnectionPool;
import org.example.repository.impl.RepositoryImpl;
import org.example.service.impl.ServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public final class ServiceImplTest {

    @Mock
    private RepositoryImpl repository;
    private ConnectionPool connectionPool;
    @InjectMocks
    private ServiceImpl service;

    @Container
    static PostgreSQLContainer postgresqlContainer =
            (PostgreSQLContainer)
                    new PostgreSQLContainer("postgres:14-alpine")
                            .withDatabaseName("education_test")
                            .withUsername("postgres")
                            .withPassword("postgres")
                            .withInitScript("db.sql")
                            .withReuse(true);

    @BeforeAll
    static void beforeAll() {
        postgresqlContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresqlContainer.stop();
    }


    @BeforeEach
    void setup() {
        try {
            connectionPool = new ConnectionPool(postgresqlContainer.getJdbcUrl());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void add_teacher_test() {
        AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto = MockData.getAddCourseWithTeacherRequestDto();
        service.addTeacher(addCourseWithTeacherRequestDto, connectionPool);
        verify(repository).getTeacherByName("Slava", connectionPool);
        verify(repository).addTeacher(addCourseWithTeacherRequestDto, connectionPool);
    }

    @Test
    void add_course_test() {
        AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto = new AddCourseWithTeacherRequestDto();
        service.addCourse(addCourseWithTeacherRequestDto, MockData.getTeacher3().getId(), connectionPool);
        verify(repository).addCourse(addCourseWithTeacherRequestDto, MockData.getTeacher3().getId(),
                connectionPool);
    }

    @Test
    void add_student() {
        AddStudentWithCourseRequestDto addStudentWithCourseRequestDto = MockData.getAddStudentWithCourseRequestDto();
        service.addStudent(addStudentWithCourseRequestDto, connectionPool);
        verify(repository).addStudent(addStudentWithCourseRequestDto, connectionPool);
    }

    @Test
    void add_subscription() {
        AddStudentWithCourseRequestDto addStudentWithCourseRequestDto = MockData.getAddStudentWithCourseRequestDto();
        when(repository.getStudentByName(addStudentWithCourseRequestDto.getStudentName(), connectionPool))
                .thenReturn(Optional.of(MockData.getStudent1()));
        when(repository.getCourseByName(addStudentWithCourseRequestDto.getCourseName(), connectionPool))
                .thenReturn(Optional.of(MockData.getCourse3()));
        service.addSubscription(addStudentWithCourseRequestDto, connectionPool);
        verify(repository).addSubscription(1, 3, connectionPool);
    }

    @Test
    void get_all_teachers() {
        int expectedSize = 2;
        when(repository.getAllTeachers(connectionPool)).thenReturn(List
                .of(MockData.getTeacher1(), MockData.getTeacher2()));
        int resultSize = service.getAllTeachers(connectionPool).size();
        Assertions.assertEquals(expectedSize, resultSize);
        service.getAllTeachers(connectionPool);
    }

    @Test
    void get_all_courses() {
        int expectedSize = 2;
        when(repository.getAllCourses(connectionPool)).thenReturn(List
                .of(MockData.getCourse1(), MockData.getCourse2()));
        int resultSize = service.getAllCourses(connectionPool).size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    void get_teacher_by_name() {
        int expectedId = 2;
        when(repository.getTeacherByName("Fedor", connectionPool))
                .thenReturn(Optional.of(MockData.getTeacher2()));
        int resultId = service.getTeacherByName("Fedor", connectionPool).get().getId();
        Assertions.assertEquals(expectedId, resultId);
    }
}
