package com.company.service;

import com.company.dao.Dao;
import com.company.model.Lesson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LessonService implements Dao<Lesson, Integer> {

    private final SessionFactory factory;

    public LessonService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Lesson lesson) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(lesson);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Lesson lesson) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(lesson);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Lesson lesson) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(lesson);
            session.getTransaction().commit();
        }
    }

    @Override
    public Lesson returnById(Integer integer) {
        try(Session session = factory.openSession()){
            return session.get(Lesson.class, integer);
        }
    }

    @Override
    public List<Lesson> returnAll() {
        try(Session session = factory.openSession()){
            Query<Lesson> query = session.createQuery("from Lesson");
            return query.list();
        }
    }
}
