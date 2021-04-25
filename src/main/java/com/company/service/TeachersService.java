package com.company.service;

import com.company.dao.Dao;
import com.company.model.Teachers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TeachersService implements Dao<Teachers, Integer> {

    private final SessionFactory factory;

    public TeachersService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Teachers teachers) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(teachers);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Teachers teachers) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(teachers);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Teachers teachers) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(teachers);
            session.getTransaction().commit();
        }
    }

    @Override
    public Teachers returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(Teachers.class, integer);
        }
    }

    @Override
    public List<Teachers> returnAll() {
        try (Session session = factory.openSession()){
            Query<Teachers> query = session.createQuery("from Teachers");
            return query.list();
        }
    }
}
