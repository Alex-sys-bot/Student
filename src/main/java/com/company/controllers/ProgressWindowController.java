package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.Progress;
import com.company.model.Student;
import com.company.service.StudentService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProgressWindowController {

    @FXML
    private TableView<Student> tableProgress;

    @FXML
    private TableColumn<Student, String> columnLastName;

    @FXML
    private TableColumn<Student, String> columnFirstName;

    @FXML
    private TableColumn<Student, String> columnPatronymic;

    @FXML
    private TableColumn<Student, String> columnDate;

    @FXML
    private TableColumn<Student, String> columnMidlBall;

    @FXML
    private TableColumn<Student, String> columnRating;

    @FXML
    private Label lblDiscipline;

    ObservableList<Student> lisStudent = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableProgress();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Dao<Student, Integer> daoStudent = new StudentService(factory);
        lisStudent.addAll(daoStudent.returnAll());
    }

    private void initTableProgress(){
        tableProgress.setItems(lisStudent);
        columnFirstName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getFirst_name()));
        columnLastName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getLast_name()));
        columnPatronymic.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getPatronymic()));
        columnRating.setCellValueFactory(p -> {

            List<Integer> list = new ArrayList<>();
            Set<Progress> listProgress = p.getValue().getProgresses();

            for (Progress progress: listProgress) {
                if (progress.getStudent().getId() == p.getValue().getId()
                        && progress.getLesson()
                        .getDisciplineSemester()
                        .getDisciplineLearningPlan()
                        .getDiscipline().getName().equals("Математика")){
                    list.add(progress.getRating());
                }
            }
            if (list.size() != 0) {
                return new SimpleObjectProperty<>(String.valueOf(list));
            } else {
                return new SimpleObjectProperty<>("н");
            }
        });



//        Calculating the average score;
        columnMidlBall.setCellValueFactory(p -> {
            Set<Progress> progresses = p.getValue().getProgresses();
            int rating = 0;
            int sizeRating = 0;
            lblDiscipline.setText("Математика");

            if (!progresses.isEmpty()) {
                for (Progress progress : progresses) {
//                   add comboBox value;
                    if (progress.getLesson().getDisciplineSemester()
                            .getDisciplineLearningPlan().getDiscipline()
                            .getName().equals(lblDiscipline.getText())) {
                        sizeRating += 1;
                        rating += progress.getRating();
                    }
                }
                return new SimpleObjectProperty<>(String.format("%.1f",(double) rating / sizeRating));
            }

            return new SimpleObjectProperty<>(String.valueOf(0));
        });
    }
}

