package com.company.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Grup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number_order", nullable = false)
    private String numberOrder;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String nameGroup;

    @OneToMany(mappedBy = "grup")
    private Set<CourseGroup> courseGroups;

    @Override
    public String toString() {
        return "Grup{" +
                "id=" + id +
                ", numberOrder='" + numberOrder + '\'' +
                ", date=" + date +
                '}';
    }
}
