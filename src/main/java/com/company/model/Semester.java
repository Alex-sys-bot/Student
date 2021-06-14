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
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number_semester", nullable = false)
    private int numberSemester;

    @OneToMany(mappedBy = "semester", fetch = FetchType.EAGER)
    private Set<DisciplineSemester> disciplineSemesters;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Override
    public String toString() {
        return String.format("Курс: %d Семестр: %d", course.getNumberCourse(), numberSemester);
    }
}
