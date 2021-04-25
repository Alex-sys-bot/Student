package com.company.service;

import com.company.dao.Dao;
import com.company.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class StudentService implements Dao<Student, Integer> {

    private final SessionFactory factory;

    public StudentService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Student student) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Student student) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Student student) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public Student returnById(Integer integer) {
        try (Session session = factory.openSession()){
         return session.get(Student.class, integer);
        }
    }

    @Override
    public List<Student> returnAll() {
        try (Session session = factory.openSession()){
            Query<Student> query = session.createQuery("from Student");
            return query.list();
        }
    }
}
