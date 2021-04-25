package com.company.service;

import com.company.dao.Dao;
import com.company.model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CourseService implements Dao<Course, Integer> {

    private final SessionFactory factory;

    public CourseService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(Course course) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Course course) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Course course) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(course);
            session.getTransaction().commit();
        }
    }

    @Override
    public Course returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(Course.class, integer);
        }
    }

    @Override
    public List<Course> returnAll() {
        try (Session session = factory.openSession()){
            Query<Course> query = session.createQuery("from Course");
            return query.list();
        }
    }
}
