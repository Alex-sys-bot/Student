package com.company.service;

import com.company.dao.Dao;
import com.company.model.TypeLesson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TypeLessonService implements Dao<TypeLesson, Integer> {

    private final SessionFactory factory;

    public TypeLessonService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(TypeLesson typeLesson) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(typeLesson);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(TypeLesson typeLesson) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(typeLesson);
            session.getTransaction().commit();
        }
    }

        @Override
        public void delete (TypeLesson typeLesson){
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                session.delete(typeLesson);
                session.getTransaction().commit();
            }
        }

        @Override
        public TypeLesson returnById (Integer integer) {
            try (Session session = factory.openSession()) {
                return session.get(TypeLesson.class, integer);
            }
        }

        @Override
        public List<TypeLesson> returnAll () {
            try(Session session = factory.openSession()){
                Query<TypeLesson> query = session.createQuery("from TypeLesson");
                return query.list();
            }
        }
    }
