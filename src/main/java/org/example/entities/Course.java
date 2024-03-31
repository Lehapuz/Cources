package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    private String name;
    private int duration;
    private double price;
    private Teacher teacher;
    private List<Student> students;
}
