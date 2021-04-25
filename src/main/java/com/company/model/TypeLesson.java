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
public class TypeLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String typeName;

    @OneToMany(mappedBy = "typeLesson", fetch = FetchType.EAGER)
    private Set<Lesson> lessons;

    @Override
    public String toString() {
        return typeName;
    }
}