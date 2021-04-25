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
@Table(name = "discipline_learning_plan")
public class DisciplineLearningPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "max_hours", nullable = false)
    private int maxHours;

    @Column(name = "audit_hours", nullable = false)
    private int auditHours;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "learning_plan_id",nullable = false)
    private LearningPlan learningPlan;

    @OneToMany(mappedBy = "disciplineLearningPlan")
    private Set<DisciplineSemester> disciplineSemesters;

    @Override
    public String toString() {
        return String.format("Специальнсть: %s, Квалификация: %s, Учебный план: %s года, Дисциплина: %s ",
                learningPlan.getQualification().getSpecialty().getName(),
                learningPlan.getQualification().getName(), learningPlan.getYearOfAdmission()
                        .toString().substring(0, 10), discipline);
    }



}
