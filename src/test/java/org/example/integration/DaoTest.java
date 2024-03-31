package org.example.integration;

import org.example.MockData;
import org.example.entities.Course;
import org.example.entities.Student;
import org.example.entities.Teacher;
import org.example.repository.ConnectionPool;
import org.example.repository.Repository;
import org.example.repository.RepositoryInstance;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoTest {

    private ConnectionPool connectionPool;
    private Repository repository;

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
        repository = RepositoryInstance.getInstance().getRepository();
    }


    @Test
    @Order(4)
    void test_get_teacher_by_name() {
        int expectedAge = 41;
        Optional<Teacher> teacher = repository.getTeacherByName("Alex", connectionPool);
        int resultAge = teacher.get().getAge();
        Assertions.assertEquals(expectedAge, resultAge);
    }

    @Test
    @Order(5)
    void test_get_course_by_name() {
        int expectedDuration = 10;
        Optional<Course> course = repository.getCourseByName("drawing", connectionPool);
        int resultDuration = course.get().getDuration();
        Assertions.assertEquals(expectedDuration, resultDuration);
    }

    @Test
    @Order(6)
    void test_get_student_by_name() {
        int expectedAge = 29;
        Optional<Student> student = repository.getStudentByName("Sasha", connectionPool);
        int resultAge = student.get().getAge();
        Assertions.assertEquals(expectedAge, resultAge);
    }

    @Test
    @Order(7)
    void test_add_teacher() {
        repository.addTeacher(MockData.getAddCourseWithTeacherRequestDto(), connectionPool);
        int expectedAge = 31;
        Optional<Teacher> teacher = repository.getTeacherByName("Slava", connectionPool);
        int resultAge = teacher.get().getAge();
        Assertions.assertEquals(expectedAge, resultAge);
    }

    @Test
    @Order(8)
    void test_add_course() {
        repository.addCourse(MockData.getAddCourseWithTeacherRequestDto(), 10, connectionPool);
        int expectedDuration = 12;
        Optional<Course> course = repository.getCourseByName("speaking", connectionPool);
        int resultDuration = course.get().getDuration();
        Assertions.assertEquals(expectedDuration, resultDuration);
    }

    @Test
    @Order(9)
    void test_add_student() {
        repository.addStudent(MockData.getAddStudentWithCourseRequestDto(), connectionPool);
        int expectedAge = 25;
        Optional<Student> student = repository.getStudentByName("Vasil", connectionPool);
        int resultAge = student.get().getAge();
        Assertions.assertEquals(expectedAge, resultAge);
    }

    @Test
    @Order(3)
    void test_add_subscription() {
        int expectedSize = 2;
        repository.addSubscription(20, 20, connectionPool);
        List<Course> courses = repository.getAllCourses(connectionPool);
        int resultSize = 0;
        for (Course course : courses){
            if (course.getName().equals("reading")){
                resultSize = course.getStudents().size();
            }
        }
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    @Order(1)
    void test_get_all_teachers() {
        int expectedSize = 2;
        int resultSize = repository.getAllTeachers(connectionPool).size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    @Order(2)
    void test_get_all_courses() {
        int expectedSize = 2;
        int resultSize = repository.getAllCourses(connectionPool).size();
        Assertions.assertEquals(expectedSize, resultSize);
    }
}
