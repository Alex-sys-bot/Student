package com.company;


import com.company.dao.Dao;
import com.company.model.*;
import com.company.service.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Console {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

//        Specialty specialty = new Specialty();
//        Dao<Specialty, Integer> daoSpecialty = new SpecialtyService(factory);
//        specialty.setName("Дизайн");
//        specialty.setCode("3305.12");
//        daoSpecialty.save(specialty);
//
//        Qualification qualification = new Qualification();
//        Dao<Qualification, Integer> daoQualification = new QualificationService(factory);
//        qualification.setName("Дизайнер программного обеспечения");
//        qualification.setName("Дизайнер сайтов");
//        qualification.setSpecialty(specialty);
//        daoQualification.save(qualification);

//        Dao<Student, Integer> daoStudent = new StudentService(factory);
//        List<Student> list = new ArrayList<>();
//        Set<Progress> progresses = new HashSet<>();
//
//        list.addAll(daoStudent.returnAll());
//
//        for (Student student: list) {
//            System.out.println(student.getProgresses().iterator().next().getRating());
//        }
    }

}
