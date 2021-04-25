package com.company.service;

import com.company.dao.Dao;
import com.company.model.CourseGroup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CourseGroupService implements Dao<CourseGroup, Integer> {

    private final SessionFactory factory;

    public CourseGroupService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(CourseGroup courseGroup) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(courseGroup);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(CourseGroup courseGroup) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(courseGroup);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(CourseGroup courseGroup) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(courseGroup);
            session.getTransaction().commit();
        }
    }

    @Override
    public CourseGroup returnById(Integer integer) {
        try (Session session = factory.openSession()){
          return session.get(CourseGroup.class, integer);
        }
    }

    @Override
    public List<CourseGroup> returnAll() {
        try (Session session = factory.openSession()){
            Query<CourseGroup> query = session.createQuery("from CourseGroup");
            return query.list();
        }
    }
}
