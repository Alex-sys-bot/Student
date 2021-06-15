package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.Teachers;
import com.company.service.TeachersService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TeachersWindowController {

    @FXML
    private TableView<Teachers> tableTeachers;

    @FXML
    private TableColumn<Teachers, Integer> columnId;

    @FXML
    private TableColumn<Teachers, String> columnLastName;

    @FXML
    private TableColumn<Teachers, String> columnFirstName;

    @FXML
    private TableColumn<Teachers, String> columnPatronymic;

    @FXML
    private TextField txtLastName;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtPatronymic;

    @FXML
    private Button addTeacher;

    @FXML
    private Button updateTeacher;

    private final ObservableList<Teachers> listTeachers = FXCollections.observableArrayList();
    private Teachers teachers;

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableDiscipline();
        takeDataFromTable();
    }

    public void close(){
        addTeacher.setDisable(true);
        updateTeacher.setDisable(true);
    }

    public void open(){
        addTeacher.setDisable(false);
        updateTeacher.setDisable(false);
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Dao<Teachers, Integer> daoTeachers = new TeachersService(factory);
        listTeachers.addAll(daoTeachers.returnAll());
    }

    private void initTableDiscipline(){
        tableTeachers.setItems(listTeachers);
        columnId.setCellValueFactory(t -> new SimpleObjectProperty<>(t.getValue().getId()));
        columnLastName.setCellValueFactory(t -> new SimpleObjectProperty<>(t.getValue().getLastName()));
        columnFirstName.setCellValueFactory(t -> new SimpleObjectProperty<>(t.getValue().getFirstName()));
        columnPatronymic.setCellValueFactory(t -> new SimpleObjectProperty<>(t.getValue().getPatronymic()));
    }

    private void takeDataFromTable(){
        tableTeachers.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) -> {
            teachers = newValue;
            txtLastName.setText(teachers.getLastName());
            txtFirstName.setText(teachers.getFirstName());
            txtPatronymic.setText(teachers.getPatronymic());
        });
    }

    @FXML
    void buttonAddTeacher(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtLastName.getText().isEmpty() && txtFirstName.getText().isEmpty() && txtPatronymic.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля пусты.");
        } else if (txtLastName.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Заполните поле: Фамилия.");
        } else if (txtFirstName.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Заполните поле: Имя.");
        } else if (txtPatronymic.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Заполните поле: Отчество.");
        } else {
            Dao<Teachers, Integer> daoTeacher = new TeachersService(factory);
            Teachers teachers = new Teachers();
            teachers.setLastName(txtLastName.getText());
            teachers.setFirstName(txtFirstName.getText());
            teachers.setPatronymic(txtPatronymic.getText());
            daoTeacher.save(teachers);

            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Преподователь добавлен.");

            listTeachers.clear();
            initialize();
        }
    }

    @FXML
    void buttonUpdateTeacher(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtLastName.getText().isEmpty() && txtFirstName.getText().isEmpty() && txtPatronymic.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля пусты.");
        } else if (txtLastName.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Заполните поле: Фамилия.");
        } else if (txtFirstName.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Заполните поле: Имя.");
        } else if (txtPatronymic.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Заполните поле: Отчество.");
        } else {
            Dao<Teachers, Integer> daoTeacher = new TeachersService(factory);
            Teachers upTeachers;
            upTeachers = teachers;
            upTeachers.setLastName(txtLastName.getText());
            upTeachers.setFirstName(txtFirstName.getText());
            upTeachers.setPatronymic(txtPatronymic.getText());
            daoTeacher.update(teachers);

            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Преподователь добавлен.");

            listTeachers.clear();
            initialize();
        }
    }

}
