package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.*;
import com.company.service.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;


public class LearningPlanWindowController {

    @FXML
    private TextField txtAllHours;

    @FXML
    private TextField txtAllAuditHours;

    @FXML
    private TextField txtAuditHoursToSemester;

    @FXML
    private ComboBox<Qualification> comboQualification;

    @FXML
    private ComboBox<LearningPlan> comboLearningPlan;

    @FXML
    private ComboBox<Discipline> comboDiscipline;

    @FXML
    private ComboBox<DisciplineLearningPlan> comboDisciplineFromLearningPlane;

    @FXML
    private ComboBox<Semester> comboSemesters;

    @FXML
    private ComboBox<Teachers> comboTeachers;
    @FXML
    private DatePicker dateOfReceipt;

    @FXML
    private TableView<DisciplineSemester> tableLearningPlane;

    @FXML
    private TableColumn<DisciplineSemester, String> columnSpecialty;

    @FXML
    private TableColumn<DisciplineSemester, String> columnQualification;

    @FXML
    private TableColumn<DisciplineSemester, String> columnLearningPlane;

    @FXML
    private TableColumn<DisciplineSemester, String> columnDiscipline;

    @FXML
    private TableColumn<DisciplineSemester, Integer> columnAllHours;

    @FXML
    private TableColumn<DisciplineSemester, Integer> columnAllAuditHours;

    @FXML
    private TableColumn<DisciplineSemester, String> columnAuditHoursToSemester;

    @FXML
    private TableColumn<DisciplineSemester, String> columnTeachers;

    @FXML
    private Label lblStatus;

    private final ObservableList<DisciplineSemester> listDisciplineSemester = FXCollections.observableArrayList();
    private final ObservableList<Specialty> listSpecialty = FXCollections.observableArrayList();
    private final ObservableList<Qualification> listQualification = FXCollections.observableArrayList();
    private final ObservableList<LearningPlan> listLearningPlan = FXCollections.observableArrayList();
    private final ObservableList<DisciplineLearningPlan> listDisciplineLearningPlan = FXCollections.observableArrayList();
    private final ObservableList<Semester> listSemester = FXCollections.observableArrayList();
    private final ObservableList<Teachers> listTeacher = FXCollections.observableArrayList();
    private final ObservableList<Discipline> listDiscipline = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTable();
        initComboBox();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Dao<DisciplineSemester, Integer> daoDisciplineSemester = new DisciplineSemesterService(factory);
        listDisciplineSemester.addAll(daoDisciplineSemester.returnAll());

        Dao<Specialty ,Integer> specialtyIntegerDao = new SpecialtyService(factory);
        listSpecialty.addAll(specialtyIntegerDao.returnAll());

        Dao<Qualification, Integer> qualificationIntegerDao = new QualificationService(factory);
        listQualification.addAll(qualificationIntegerDao.returnAll());

        Dao<LearningPlan, Integer> learningPlanIntegerDao = new LearningPlanService(factory);
        listLearningPlan.addAll(learningPlanIntegerDao.returnAll());

        Dao<DisciplineLearningPlan, Integer> disciplineLearningPlanIntegerDao = new DesciplineLeaningPlanService(factory);
        listDisciplineLearningPlan.addAll(disciplineLearningPlanIntegerDao.returnAll());

        Dao<Semester, Integer> semesterIntegerDao = new SemesterService(factory);
        listSemester.addAll(semesterIntegerDao.returnAll());

        Dao<Teachers, Integer> teachersIntegerDao = new TeachersService(factory);
        listTeacher.addAll(teachersIntegerDao.returnAll());

        Dao<Discipline, Integer> disciplineIntegerDao = new DisciplineService(factory);
        listDiscipline.addAll(disciplineIntegerDao.returnAll());
    }

    private void initTable(){
        tableLearningPlane.setItems(listDisciplineSemester);
        columnSpecialty.setCellValueFactory(lp -> new SimpleObjectProperty<>(
                lp.getValue().getDisciplineLearningPlan().getLearningPlan().getQualification().getSpecialty().getName()));
        columnQualification.setCellValueFactory(lp -> new SimpleObjectProperty<>(
                lp.getValue().getDisciplineLearningPlan().getLearningPlan().getQualification().getName()));
        columnLearningPlane.setCellValueFactory(
                lp -> new SimpleObjectProperty<>(lp.getValue().getDisciplineLearningPlan().getLearningPlan().getYearOfAdmission().toString().substring(0,10)));
        columnDiscipline.setCellValueFactory(
                lp -> new SimpleObjectProperty<>(lp.getValue().getDisciplineLearningPlan().getDiscipline().getName()));
        columnAllHours.setCellValueFactory(lp -> new SimpleObjectProperty<>(lp.getValue().getDisciplineLearningPlan().getMaxHours()));
        columnAllAuditHours.setCellValueFactory(lp -> new SimpleObjectProperty<>(lp.getValue().getDisciplineLearningPlan().getAuditHours()));
        columnAuditHoursToSemester.setCellValueFactory(lp -> new SimpleObjectProperty<>(
                lp.getValue().getAudit_hours() + " " + "за" + " " + lp.getValue().getSemester().getNumberSemester() + " " + "семестр"));
    }

    private void initComboBox(){
        comboDiscipline.setItems(listDiscipline);
        comboQualification.setItems(listQualification);
        comboLearningPlan.setItems(listLearningPlan);
        comboSemesters.setItems(listSemester);
        comboTeachers.setItems(listTeacher);
        comboDisciplineFromLearningPlane.setItems(listDisciplineLearningPlan);
    }

    private void clearScreen(){
        listSemester.clear();
        listLearningPlan.clear();
        listTeacher.clear();
        listDiscipline.clear();
        listDisciplineLearningPlan.clear();
        listQualification.clear();
        listSpecialty.clear();
        listDisciplineSemester.clear();

        initialize();
    }

    @FXML
    void buttonAddDisciplineToLearningPlane(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtAllHours.getText().isEmpty()
                && txtAllHours.getText().isEmpty()
                && comboLearningPlan.getValue() == null
                && comboDiscipline.getValue() == null){

            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля пусты.");

        } else if (txtAllHours.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Всего часов пусто.");

        } else if (txtAllAuditHours.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Аудиторные часы пусто.");

        } else if (comboLearningPlan.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Учебный план пусто.");

        } else if (comboDiscipline.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дисциплина пусто.");

        } else {
            Dao<DisciplineLearningPlan, Integer> disciplineLearningDao = new DesciplineLeaningPlanService(factory);
            DisciplineLearningPlan disciplineLearningPlan = new DisciplineLearningPlan();
            disciplineLearningPlan.setMaxHours(Integer.parseInt(txtAllHours.getText()));
            disciplineLearningPlan.setAuditHours(Integer.parseInt(txtAllAuditHours.getText()));
            disciplineLearningPlan.setLearningPlan(comboLearningPlan.getValue());
            disciplineLearningPlan.setDiscipline(comboDiscipline.getValue());
            disciplineLearningDao.save(disciplineLearningPlan);

            clearScreen();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Учебный план обновлён.");
        }
    }

    @FXML
    void buttonAddDisciplineToSemester(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtAuditHoursToSemester.getText().isEmpty()
                && comboDisciplineFromLearningPlane.getValue() == null
                && comboSemesters.getValue() == null
                && comboTeachers.getValue() == null) {

            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля пусты.");
        } else if (txtAuditHoursToSemester.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Аудиторные часы в семестре пусто");
        } else if (comboDisciplineFromLearningPlane.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дисциплина  из учебного плана пусто");
        } else if (comboSemesters.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Семестре пусто");
        } else if (comboTeachers.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Преподаватель пусто");
        } else {
            Dao<DisciplineSemester, Integer> disciplineSemesterDao = new DisciplineSemesterService(factory);
            DisciplineSemester disciplineSemester = new DisciplineSemester();
            disciplineSemester.setAudit_hours(Integer.parseInt(txtAuditHoursToSemester.getText()));
            disciplineSemester.setDisciplineLearningPlan(comboDisciplineFromLearningPlane.getValue());
            disciplineSemester.setSemester(comboSemesters.getValue());
            disciplineSemester.setTeachers(Collections.singleton(comboTeachers.getValue()));
            disciplineSemesterDao.save(disciplineSemester);

            clearScreen();

            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Учебный план обновлён.");
        }
    }

    @FXML
    void buttonAddLearningYear(ActionEvent event) throws ParseException {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if(dateOfReceipt.getValue() == null && comboQualification.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля пусты");
        } else if (dateOfReceipt.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Год поступления учебного плана пусто.");
        } else if (comboQualification.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Квалификация.");
        } else {
            Dao<LearningPlan, Integer> learningPlanIntegerDao = new LearningPlanService(factory);
            LearningPlan learningPlan = new LearningPlan();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateOfReceipt.getValue().toString());
            learningPlan.setYearOfAdmission(date);
            learningPlan.setQualification(comboQualification.getValue());
            learningPlanIntegerDao.save(learningPlan);

           clearScreen();

            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Учебный план добавлен.");
        }
    }

    @FXML
    void buttonUpDisciplineLearningPlane(ActionEvent event) {
    }

    @FXML
    void buttonUpDisciplineToSemester(ActionEvent event) {
    }

}
