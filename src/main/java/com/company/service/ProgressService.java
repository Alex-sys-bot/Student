package com.company.service;

import com.company.dao.Dao;
import com.company.model.Progress;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProgressService implements Dao<Progress, Integer> {

    private final SessionFactory factory;

    public ProgressService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Progress progress) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(progress);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Progress progress) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(progress);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Progress progress) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(progress);
            session.getTransaction().commit();
        }
    }

    @Override
    public Progress returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(Progress.class, integer);
        }
    }

    @Override
    public List<Progress> returnAll() {
        try (Session session = factory.openSession()){
            Query<Progress> query = session.createQuery("from Progress");
            return query.list();
        }
    }
}
