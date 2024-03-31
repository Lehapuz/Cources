package org.example;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.entities.Course;
import org.example.entities.Student;
import org.example.entities.Subscription;
import org.example.entities.Teacher;

import java.util.ArrayList;

public class MockData {

    public static AddCourseWithTeacherRequestDto getAddCourseWithTeacherRequestDto() {
        return new AddCourseWithTeacherRequestDto("speaking", 12,
                54.4, "Slava", 31);
    }

    public static AddStudentWithCourseRequestDto getAddStudentWithCourseRequestDto() {
        return new AddStudentWithCourseRequestDto("Vasil", 25, "drawing");
    }

    public static Teacher getTeacher1() {
        return new Teacher(1, "Oleg", 30, new ArrayList<>());
    }

    public static Teacher getTeacher2() {
        return new Teacher(2, "Fedor", 35, new ArrayList<>());
    }

    public static Teacher getTeacher3() {
        return new Teacher(3, "Slava", 35, new ArrayList<>());
    }

    public static Course getCourse1() {
        return new Course(1, "reading", 5, 8.8, getTeacher1(), new ArrayList<>());
    }

    public static Course getCourse2() {
        return new Course(2, "drawing", 6, 7.2, getTeacher1(), new ArrayList<>());
    }

    public static Course getCourse3() {
        return new Course(3, "speaking", 6, 7.2, getTeacher1(), new ArrayList<>());
    }

    public static Student getStudent1() {
        return new Student(1, "Vasil", 25, new ArrayList<>());
    }

    public static Subscription getSubscription1() {
        return new Subscription(2, 1, 1);
    }
}
