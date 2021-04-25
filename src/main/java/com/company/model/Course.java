package com.company.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number_course",nullable = false)
    private int numberCourse;

    @OneToMany(mappedBy = "course")
    private Set<Semester> semesters;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<CourseGroup> courseGroups;

    @Override
    public String toString() {
        return String.valueOf(numberCourse);
    }
}
