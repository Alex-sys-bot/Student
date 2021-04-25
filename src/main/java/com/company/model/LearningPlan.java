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
@Table(name = "learning_plan")
public class LearningPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "year_of_admission", nullable = false)
    private Date yearOfAdmission;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qualification_id", nullable = false)
    private Qualification qualification;

    @OneToMany(mappedBy = "learningPlan")
    private Set<DisciplineLearningPlan> disciplineLearningPlans;

    @Override
    public String toString() {
        return qualification + " " + yearOfAdmission;
    }
}
