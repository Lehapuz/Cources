package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseWithStudentsResponseDto {
    int id;
    String name;
    int duration;
    double price;
    List<StudentResponseDto> studentResponseDtoList;
}
