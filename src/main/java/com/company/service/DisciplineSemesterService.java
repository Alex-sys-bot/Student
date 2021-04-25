package com.company.service;

import com.company.dao.Dao;
import com.company.model.DisciplineSemester;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class DisciplineSemesterService implements Dao<DisciplineSemester, Integer> {

    private final SessionFactory factory;

    public DisciplineSemesterService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(DisciplineSemester disciplineSemester) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(disciplineSemester);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(DisciplineSemester disciplineSemester) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(disciplineSemester);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(DisciplineSemester disciplineSemester) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(disciplineSemester);
            session.getTransaction().commit();
        }
    }

    @Override
    public DisciplineSemester returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(DisciplineSemester.class, integer);
        }
    }

    @Override
    public List<DisciplineSemester> returnAll() {
        try (Session session = factory.openSession()){
            Query<DisciplineSemester> query = session.createQuery("from DisciplineSemester");
            return query.list();
        }
    }
}
