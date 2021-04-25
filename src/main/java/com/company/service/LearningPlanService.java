package com.company.service;

import com.company.dao.Dao;
import com.company.model.LearningPlan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LearningPlanService implements Dao<LearningPlan, Integer> {

    private final SessionFactory factory;

    public LearningPlanService(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(LearningPlan learningPlan) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(learningPlan);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(LearningPlan learningPlan) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(learningPlan);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(LearningPlan learningPlan) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(learningPlan);
            session.getTransaction().commit();
        }
    }

    @Override
    public LearningPlan returnById(Integer integer) {
        try (Session session = factory.openSession()){
            return session.get(LearningPlan.class, integer);
        }
    }

    @Override
    public List<LearningPlan> returnAll() {
        try (Session session = factory.openSession()){
            Query<LearningPlan> query = session.createQuery("from LearningPlan");
            return query.list();
        }
    }
}
