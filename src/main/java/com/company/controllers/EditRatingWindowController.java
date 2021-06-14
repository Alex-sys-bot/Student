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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EditRatingWindowController {

    @FXML
    private ComboBox<Grup> comboGroup;

    @FXML
    private ComboBox<Semester> comboSemester;

    @FXML
    private ComboBox<Discipline> comboDiscipline;

    @FXML
    private ComboBox<Student> comboStudent;

    @FXML
    private TextField txtRating;

    @FXML
    private TableView<Progress> tableProgress;

    @FXML
    private TableColumn<Progress, String> columnLastName;

    @FXML
    private TableColumn<Progress, String> columnFirstName;

    @FXML
    private TableColumn<Progress, String> columnPatronymic;

    @FXML
    private TableColumn<Progress, String> columnGroup;

    @FXML
    private TableColumn<Progress, String> columnDate;

    @FXML
    private TableColumn<Progress, Integer> columnRating;

    @FXML
    private TableColumn<Progress, String> columnLesson;

    @FXML
    private TableColumn<Progress, String> columnDiscipline;

    @FXML
    private Label lblStatus;

    private final ObservableList<Progress> listProgress = FXCollections.observableArrayList();
    private final ObservableList<Grup> listGroups = FXCollections.observableArrayList();
    private final ObservableList<Semester> listSemesters = FXCollections.observableArrayList();
    private final ObservableList<Student> listStudents = FXCollections.observableArrayList();
    private final ObservableList<Discipline> listDiscipline = FXCollections.observableArrayList();

    private Progress progress;

    private int rating;

    public void initialize(){
        takeDataFromDataBase();
        initTableProgress();
        sortByGroup();
        takeDataFromTable();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<Progress,Integer> progressIntegerDao = new ProgressService(factory);
        listProgress.addAll(progressIntegerDao.returnAll());

        Dao<Grup, Integer> groupIntegerDao = new GroupService(factory);
        listGroups.addAll(groupIntegerDao.returnAll());
        comboGroup.setItems(listGroups);

        Dao<Semester,Integer> semesterIntegerDao = new SemesterService(factory);
        listSemesters.addAll(semesterIntegerDao.returnAll());
        comboSemester.setItems(listSemesters);

        Dao<Student, Integer> studentIntegerDao = new StudentService(factory);
        listStudents.addAll(studentIntegerDao.returnAll());

        Dao<Discipline,Integer> disciplineIntegerDao = new DisciplineService(factory);
        listDiscipline.addAll(disciplineIntegerDao.returnAll());
        comboDiscipline.setItems(listDiscipline);
    }

    private void initTableProgress(){
        tableProgress.setItems(listProgress);

        columnLastName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getStudent().getLast_name()));
        columnFirstName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getStudent().getFirst_name()));
        columnPatronymic.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getStudent().getPatronymic()));
        columnGroup.setCellValueFactory(p -> new SimpleObjectProperty<>(
                p.getValue().getStudent().getCourseGroup().getGrup().getNameGroup()));
        columnDate.setCellValueFactory(p -> new SimpleObjectProperty<>(
                p.getValue().getLesson().getDateLesson().toString().substring(0,10)));
        columnRating.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getRating()));
        columnLesson.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getLesson().getThemeLesson()));
        columnDiscipline.setCellValueFactory(p -> new SimpleObjectProperty<>(
                p.getValue().getLesson().getDisciplineSemester().getDisciplineLearningPlan().getDiscipline().getName()));
    }

    private void sortByGroup(){
        ObservableList<Progress> list = FXCollections.observableArrayList();
        ObservableList<Student> students = FXCollections.observableArrayList();
        comboGroup.valueProperty().addListener((obj, oldValue, newValue) -> {
            list.clear();
            for (Progress progress: listProgress) {
                if (progress.getStudent().getCourseGroup().getGrup().getNameGroup().equals(newValue.getNameGroup())){
                    list.add(progress);
                }
            }

            students.clear();
            for (Student student: listStudents) {
                if (student.getCourseGroup().getGrup().getNameGroup().equals(newValue.getNameGroup())){
                    students.addAll(student);
                }
            }
            comboStudent.setItems(students);

            tableProgress.setItems(list);
            sortByDiscipline(list);
        });
    }

    private void sortBySemester(ObservableList<Progress> listProgress){
        ObservableList<Progress> list = FXCollections.observableArrayList();
        comboSemester.valueProperty().addListener((obj, oldValue, newValue) -> {
            list.clear();
            for (Progress progress: listProgress) {
                if (progress.getLesson().getDisciplineSemester()
                        .getSemester().getNumberSemester() == newValue.getNumberSemester()){
                    list.add(progress);
                }
            }
            tableProgress.setItems(list);
            sortByStudent(list);
        });
    }

    private void sortByStudent(ObservableList<Progress> listProgress){
        ObservableList<Progress> list = FXCollections.observableArrayList();
        comboStudent.valueProperty().addListener((obj, oldValue, newValue) -> {
            list.clear();
            for (Progress progress: listProgress) {
                if (progress.getStudent().getNumberCreditBook().equals(newValue.getNumberCreditBook())){
                    list.add(progress);
                }
            }
            tableProgress.setItems(list);
            sortByDiscipline(list);
        });
    }

    private void sortByDiscipline(ObservableList<Progress> listProgress){
        ObservableList<Progress> list = FXCollections.observableArrayList();
        comboDiscipline.valueProperty().addListener((obj, oldValue, newValue) -> {
            list.clear();
            for (Progress progress: listProgress) {
                if (progress.getLesson().getDisciplineSemester().getDisciplineLearningPlan()
                        .getDiscipline().getName().equals(newValue.getName())){
                    list.add(progress);
                }
            }
            tableProgress.setItems(list);
            sortBySemester(list);
        });
    }

    private void takeDataFromTable(){
        tableProgress.getSelectionModel().selectedItemProperty().addListener((obj,oldValue,newValue) ->{
            if (newValue != null){
                progress = newValue;
                txtRating.setText(String.valueOf(progress.getRating()));
                rating = Integer.parseInt(txtRating.getText());
            }
        });
    }

    private void clearScreen(){
        listDiscipline.clear();
        listStudents.clear();
        listGroups.clear();
        listProgress.clear();
        listSemesters.clear();
        initialize();
    }

    @FXML
    void buttonUpdateRating(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<Progress, Integer> progressIntegerDao = new ProgressService(factory);

        if (txtRating.getText().isEmpty()){
            progress.setRating(progress.getRating());
            lblStatus.setText("Поле Оценка пустое. Данные вернулись к исходному состоянию");
        } else {
            progress.setRating(Integer.parseInt(txtRating.getText()));
            lblStatus.setText("Данные изменены");
        }

        progressIntegerDao.update(progress);
        clearScreen();
    }
}

