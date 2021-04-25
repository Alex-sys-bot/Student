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
@Table
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String themeLesson;

    @Column(nullable = false)
    private Date dateLesson;

    @ManyToOne
    private TypeLesson typeLesson;

    @ManyToOne
    private DisciplineSemester disciplineSemester;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
    private Set<Progress> progresses;

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", themeLesson='" + themeLesson + '\'' +
                ", dateLesson=" + dateLesson +
                ", typeLesson=" + typeLesson +
                ", disciplineSemester=" + disciplineSemester +
                '}';
    }
}
