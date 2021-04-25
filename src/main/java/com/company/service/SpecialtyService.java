package com.company.service;

import com.company.dao.Dao;
import com.company.model.Specialty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class SpecialtyService implements Dao<Specialty, Integer> {

    private final SessionFactory factory;

    public SpecialtyService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Specialty specialty) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(specialty);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Specialty specialty) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(specialty);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Specialty specialty) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(specialty);
            session.getTransaction().commit();
        }
    }

    @Override
    public Specialty returnById(Integer id) {
        try (Session session = factory.openSession()){
           return session.get(Specialty.class, id);
        }
    }

    @Override
    public List<Specialty> returnAll() {
        try (Session session = factory.openSession()){
            Query<Specialty> query = session.createQuery("from Specialty ");
            return query.list();
        }
    }
}
