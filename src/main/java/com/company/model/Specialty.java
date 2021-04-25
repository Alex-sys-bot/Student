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
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @OneToMany(mappedBy = "specialty", fetch = FetchType.EAGER)
    Set<Qualification> qualifications;

    @Override
    public String toString() {
        return name;

    }
}
