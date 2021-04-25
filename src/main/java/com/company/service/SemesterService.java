package com.company.service;

import com.company.dao.Dao;
import com.company.model.Semester;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class SemesterService implements Dao<Semester, Integer> {

    private final SessionFactory factory;

    public SemesterService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Semester semester) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(semester);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Semester semester) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(semester);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Semester semester) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(semester);
            session.getTransaction().commit();
        }
    }

    @Override
    public Semester returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(Semester.class, integer);
        }
    }

    @Override
    public List<Semester> returnAll() {
        try (Session session = factory.openSession()){
            Query<Semester> query = session.createQuery("from Semester");
            return query.list();
        }
    }
}
