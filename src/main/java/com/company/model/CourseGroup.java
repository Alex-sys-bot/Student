package com.company.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "course_group")
public class CourseGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String cipher;

    @Column(name = "learning_year", nullable = false)
    private String learningYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    private Grup grup;

    @OneToMany(mappedBy = "courseGroup", fetch = FetchType.EAGER)
    private Set<Student> students;

    @Override
    public String toString() {
        return cipher;
    }
}
