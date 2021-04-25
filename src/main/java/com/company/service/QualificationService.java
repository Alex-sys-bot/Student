package com.company.service;

import com.company.dao.Dao;
import com.company.model.Qualification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class QualificationService implements Dao<Qualification, Integer> {

    private final SessionFactory factory;

    public QualificationService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Qualification qualification) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(qualification);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Qualification qualification) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(qualification);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Qualification qualification) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(qualification);
            session.getTransaction().commit();
        }
    }

    @Override
    public Qualification returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(Qualification.class,integer);
        }
    }

    @Override
    public List<Qualification> returnAll() {
        try (Session session = factory.openSession()){
            Query<Qualification>query = session.createQuery("from Qualification ");
            return query.list();
        }
    }
}
