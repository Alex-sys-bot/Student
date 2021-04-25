package com.company.service;

import com.company.dao.Dao;
import com.company.model.DisciplineLearningPlan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class DesciplineLeaningPlanService implements Dao<DisciplineLearningPlan, Integer> {

    private final SessionFactory factory;

    public DesciplineLeaningPlanService(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public void save(DisciplineLearningPlan disciplineLearningPlan) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.save(disciplineLearningPlan);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(DisciplineLearningPlan disciplineLearningPlan) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(disciplineLearningPlan);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(DisciplineLearningPlan disciplineLearningPlan) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(disciplineLearningPlan);
            session.getTransaction().commit();
        }
    }

    @Override
    public DisciplineLearningPlan returnById(Integer integer) {
        try (Session session = factory.openSession()){
           return session.get(DisciplineLearningPlan.class,integer);
        }
    }

    @Override
    public List<DisciplineLearningPlan> returnAll() {
        try (Session session = factory.openSession()){
            Query<DisciplineLearningPlan> query = session.createQuery("from DisciplineLearningPlan");
            return query.list();
        }
    }
}
