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
public class Teachers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "patronymic",nullable = false)
    private String patronymic;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "descipline_semester_has_teachers",
    joinColumns = @JoinColumn(name = "teachers_id"),
    inverseJoinColumns = @JoinColumn(name = "discipline_semester_id"))
    private Set<DisciplineSemester> disciplineSemesters;

    @Override
    public String toString() {
        return String.format("%s %s %s", lastName, firstName, patronymic
        );
    }
}
