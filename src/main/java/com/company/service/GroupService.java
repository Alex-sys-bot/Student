package com.company.service;

import com.company.dao.Dao;
import com.company.model.Grup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class GroupService implements Dao<Grup, Integer> {

    private final SessionFactory factory;

    public GroupService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Grup grup) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(grup);
            session.getTransaction();
        }
    }

    @Override
    public void update(Grup grup) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(grup);
            session.getTransaction();
        }
    }

    @Override
    public void delete(Grup grup) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(grup);
            session.getTransaction();
        }
    }

    @Override
    public Grup returnById(Integer integer) {
        try(Session session = factory.openSession()){
            return session.get(Grup.class, integer);
        }
    }

    @Override
    public List<Grup> returnAll() {
        try (Session session = factory.openSession()){
            Query<Grup> query = session.createQuery("from Grup");
            return query.list();
        }
    }
}
