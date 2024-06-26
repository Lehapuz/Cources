package org.example.controller;

import com.google.gson.Gson;
import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;
import org.example.dto.response.CourseWithStudentsResponseDto;
import org.example.dto.response.MessageResponseDto;
import org.example.dto.response.TeacherResponseDto;
import org.example.repository.ConnectionPool;
import org.example.service.ServiceInstance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListCommand {

    private static ListCommand instance;
    private static final String COURSE_NAME = "courseName";
    private static final String DURATION = "duration";
    private static final String PRICE = "price";
    private static final String TEACHER_NAME = "teacherName";
    private static final String STUDENT_NAME = "studentName";
    private static final String AGE = "age";
    private static final String FORMAT = "application/json";
    private static final String ENCODING = "UTF-8";
    private final MessageResponseDto messageResponseDto = new MessageResponseDto("Operation has succeed");

    private ListCommand() {
    }

    public static ListCommand getInstance() {
        if (instance == null) {
            instance = new ListCommand();
        }
        return instance;
    }

    public void addCourseWithTeacher(HttpServletRequest req, HttpServletResponse resp) {
        AddCourseWithTeacherRequestDto addCourseWithTeacherRequestDto = new AddCourseWithTeacherRequestDto(
                req.getParameter(COURSE_NAME), Integer.parseInt(req.getParameter(DURATION)),
                Double.parseDouble(req.getParameter(PRICE)),
                req.getParameter(TEACHER_NAME), Integer.parseInt(req.getParameter(AGE)));
        ServiceInstance.getInstance().getService().addTeacher(addCourseWithTeacherRequestDto, ConnectionPool.getInstance());
        ServiceInstance.getInstance().getService().addCourse(addCourseWithTeacherRequestDto,
                ServiceInstance.getInstance().getService().getTeacherByName(req.getParameter(TEACHER_NAME),
                        ConnectionPool.getInstance()).get().getId(), ConnectionPool.getInstance());
        String json = new Gson().toJson(messageResponseDto);
        resp.setContentType(FORMAT);
        resp.setCharacterEncoding(ENCODING);
        try {
            resp.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStudent(HttpServletRequest req, HttpServletResponse resp) {
        AddStudentWithCourseRequestDto addStudentWithCourseRequestDto = new AddStudentWithCourseRequestDto(
                req.getParameter(STUDENT_NAME), Integer.parseInt(req.getParameter(AGE)), req.getParameter(COURSE_NAME));
        ServiceInstance.getInstance().getService().addStudent(addStudentWithCourseRequestDto, ConnectionPool.getInstance());
        ServiceInstance.getInstance().getService().addSubscription(addStudentWithCourseRequestDto, ConnectionPool.getInstance());
        String json = new Gson().toJson(messageResponseDto);
        resp.setContentType(FORMAT);
        resp.setCharacterEncoding(ENCODING);
        try {
            resp.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getTeachers(HttpServletRequest req, HttpServletResponse resp) {
        List<TeacherResponseDto> teacherResponseDtoList = ServiceInstance.getInstance().getService()
                .getAllTeachers(ConnectionPool.getInstance());
        String json = new Gson().toJson(teacherResponseDtoList);
        resp.setContentType(FORMAT);
        resp.setCharacterEncoding(ENCODING);
        try {
            resp.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getCourses(HttpServletRequest req, HttpServletResponse resp) {
        List<CourseWithStudentsResponseDto> courseWithStudentsResponseDtost = ServiceInstance
                .getInstance().getService().getAllCourses(ConnectionPool.getInstance());
        String json = new Gson().toJson(courseWithStudentsResponseDtost);
        resp.setContentType(FORMAT);
        resp.setCharacterEncoding(ENCODING);
        try {
            resp.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
