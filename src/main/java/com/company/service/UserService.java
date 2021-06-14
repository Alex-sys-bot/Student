package com.company.service;

import com.company.dao.Dao;
import com.company.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserService implements Dao<User, Integer> {

    private final SessionFactory factory;

    public UserService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(User user) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public User returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(User.class,integer);
        }
    }

    @Override
    public List<User> returnAll() {
        try (Session session = factory.openSession()){
            Query<User> query = session.createQuery("from  User");
            return query.list();
        }
    }
}
