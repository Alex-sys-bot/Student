package com.company.service;

import com.company.dao.Dao;
import com.company.model.Discipline;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class DisciplineService implements Dao<Discipline, Integer> {

    private final SessionFactory factory;

    public DisciplineService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Discipline discipline) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(discipline);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Discipline discipline) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(discipline);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Discipline discipline) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(discipline);
            session.getTransaction().commit();
        }
    }

    @Override
    public Discipline returnById(Integer integer) {
        try (Session session = factory.openSession()){
           return session.get(Discipline.class, integer);
        }
    }

    @Override
    public List<Discipline> returnAll() {
        try (Session session = factory.openSession()){
            Query<Discipline> query = session.createQuery("from Discipline");
            return query.list();
        }
    }
}
