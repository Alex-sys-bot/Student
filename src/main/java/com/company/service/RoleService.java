package com.company.service;

import com.company.dao.Dao;
import com.company.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class RoleService implements Dao<Role, Integer> {

    private final SessionFactory factory;

    public RoleService(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public void save(Role role) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Role role) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Role role) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(role);
            session.getTransaction().commit();
        }
    }

    @Override
    public Role returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(Role.class,integer);
        }
    }

    @Override
    public List<Role> returnAll() {
        try (Session session = factory.openSession()){
            Query<Role> query = session.createQuery("from Role ");
            return query.list();
        }
    }
}
