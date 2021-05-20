package com.company;


import com.company.dao.Dao;
import com.company.model.Teachers;
import com.company.service.TeachersService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Console {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<Teachers, Integer> teachersDao = new TeachersService(factory);

        Teachers teachers = new Teachers();
        teachers.setPatronymic("Викторович");
        teachers.setFirstName("Иван");
        teachers.setLastName("Волков");

    }
}
