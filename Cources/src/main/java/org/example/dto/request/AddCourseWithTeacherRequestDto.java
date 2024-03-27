package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCourseWithTeacherRequestDto {
    String courseName;
    int duration;
    double price;
    String teacherName;
    int age;
}
