package com.company.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    private Lesson lesson;

    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", rating=" + rating +
                ", student=" + student +
                ", lesson=" + lesson +
                '}';
    }
}
