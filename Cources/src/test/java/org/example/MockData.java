package org.example;

import org.example.dto.request.AddCourseWithTeacherRequestDto;
import org.example.dto.request.AddStudentWithCourseRequestDto;

public class MockData {

    public static AddCourseWithTeacherRequestDto getAddCourseWithTeacherRequestDto(){
        return new AddCourseWithTeacherRequestDto("speaking", 12,
                54.4, "Slava", 31);
    }

    public static AddStudentWithCourseRequestDto getAddStudentWithCourseRequestDto(){
        return new AddStudentWithCourseRequestDto("Vasil", 25, "drawing");
    }
}
