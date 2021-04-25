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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "patronymic", nullable = false)
    private String patronymic;

    @Column(name = "date_birthday", nullable = false)
    private Date dateBirthday;

    @Column(name = "receipt_date", nullable = false)
    private Date receiptDate;

    @Column
    private String phone;

    @Column(name = "number_credit_book", nullable = false)
    private String numberCreditBook;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_group_id", nullable = false)
    private CourseGroup courseGroup;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private Set<Progress> progresses;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", dateBirthday=" + dateBirthday +
                ", receiptDate=" + receiptDate +
                ", phone='" + phone + '\'' +
                ", numberCreditBook='" + numberCreditBook + '\'' +
                ", courseGroup=" + courseGroup +
                '}';
    }
}
