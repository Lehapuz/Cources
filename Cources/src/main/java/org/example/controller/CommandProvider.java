package org.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandProvider {

    private static final String COMMAND = "command";
    private static final String ADD_COURSE_WITH_TEACHER = "add_course_with_teacher";
    private static final String ADD_STUDENT_TO_COURSE = "add_student_to_course";
    private static final String GET_TEACHERS_WITH_COURSES = "get_teachers_with_courses";
    private static final String GET_COURSES_WITH_STUDENTS = "get_courses_with_students";
    private static CommandProvider instance;

    private CommandProvider() {
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter(COMMAND) == null){
            throw new IllegalArgumentException("Bad request");
        }
        switch (req.getParameter(COMMAND)){
            case ADD_COURSE_WITH_TEACHER -> ListCommand.getInstance().addCourseWithTeacher(req, resp);
            case ADD_STUDENT_TO_COURSE -> ListCommand.getInstance().addStudent(req, resp);
            case GET_TEACHERS_WITH_COURSES -> ListCommand.getInstance().getTeachers(req, resp);
            case GET_COURSES_WITH_STUDENTS -> ListCommand.getInstance().getCourses(req, resp);
        }
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }
}
