package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.*;
import com.company.service.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private TableColumn<Student, String> columnMidlBall;

    @FXML
    private TableColumn<Student, String> columnRating;

    @FXML
    private Label lblDiscipline;

    @FXML
    private DatePicker dateLesson;

    @FXML
    private TextField txtThemeLesson;

    @FXML
    private ComboBox<DisciplineSemester> comboDiscipline;

    @FXML
    private ComboBox<TypeLesson> comboTypeLesson;

    @FXML
    private TextField txtRating;

    @FXML
    private ComboBox<Student> comboStudents;

    @FXML
    private ComboBox<Lesson> comboLesson;

    @FXML
    private Label lblStatus;

    @FXML
    private ComboBox<Discipline> comboSortByDiscipline;

    @FXML
    private ComboBox<CourseGroup> comboSortByGroup;

    @FXML
    private ComboBox<Integer> comboSortBySemester;

    @FXML
    private ComboBox<Qualification> sortByQualification;

    ObservableList<Student> listStudent = FXCollections.observableArrayList();
    ObservableList<DisciplineSemester> listDisciplineSemester = FXCollections.observableArrayList();
    ObservableList<TypeLesson> listTypeLesson = FXCollections.observableArrayList();
    ObservableList<Lesson> listLesson = FXCollections.observableArrayList();

    ObservableList<Discipline> listSortDiscipline = FXCollections.observableArrayList();
    ObservableList<CourseGroup> listSortGroup = FXCollections.observableArrayList();
    ObservableList<Progress> listProgresses = FXCollections.observableArrayList();
    ObservableList<Integer> listSemesters = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
    ObservableList<Qualification> listQualification = FXCollections.observableArrayList();

    private int numberSemester;

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableProgress(listStudent);
        sortBySemester();
        sortByDiscipline(listStudent);
        setSortByQualification();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Dao<Student, Integer> daoStudent = new StudentService(factory);
        listStudent.addAll(daoStudent.returnAll());
        comboStudents.setItems(listStudent);

        Dao<DisciplineSemester, Integer> disciplineSemesterDao = new DisciplineSemesterService(factory);
        listDisciplineSemester.addAll(disciplineSemesterDao.returnAll());
        comboDiscipline.setItems(listDisciplineSemester);

        Dao<TypeLesson, Integer> typeLessonDao = new TypeLessonService(factory);
        listTypeLesson.addAll(typeLessonDao.returnAll());
        comboTypeLesson.setItems(listTypeLesson);

        Dao<Lesson, Integer> lessonDao = new LessonService(factory);
        listLesson.addAll(lessonDao.returnAll());
        comboLesson.setItems(listLesson);

        Dao<Discipline, Integer> disciplineDao = new DisciplineService(factory);
        listSortDiscipline.addAll(disciplineDao.returnAll());
        comboSortByDiscipline.setItems(listSortDiscipline);

        Dao<CourseGroup, Integer> groupDao = new CourseGroupService(factory);
        listSortGroup.addAll(groupDao.returnAll());
        comboSortByGroup.setItems(listSortGroup);

        Dao<Progress, Integer> progressIntegerDao = new ProgressService(factory);
        listProgresses.addAll(progressIntegerDao.returnAll());

        Dao<Qualification, Integer> qualificationIntegerDao = new QualificationService(factory);
        listQualification.addAll(qualificationIntegerDao.returnAll());
        sortByQualification.setItems(listQualification);
    }

    private void setSortByQualification(){
        sortByQualification.valueProperty().addListener((obj, oldValue, newValue) -> {
            ObservableList<Lesson> list = FXCollections.observableArrayList();
            for (Lesson lesson: listLesson) {
                if (lesson.getDisciplineSemester()
                        .getDisciplineLearningPlan()
                        .getLearningPlan().getQualification().getName().equals(newValue.getName())){
                    list.add(lesson);
                }
            }
            comboLesson.setItems(list);
        });
    }

    private void sortByDiscipline(ObservableList<Student> listStudent) {
        comboSortByDiscipline.valueProperty().addListener((obj, oldValue, newValue) -> {
            lblDiscipline.setText(newValue.getName());
            initTableProgress(listStudent);
            sortByGroup(listStudent);
        });
    }

    private void sortByGroup(ObservableList<Student> listStudent) {

        ObservableList<Student> students = FXCollections.observableArrayList();
        comboSortByGroup.valueProperty().addListener((obj, oldValue, newValue) -> {
            students.clear();
            for (Student student: listStudent) {
                if (student.getCourseGroup().getGrup().getId() == newValue.getId()){
                    students.addAll(student);
                }
            }
            initTableProgress(students);
        });
    }

    private void sortBySemester() {
        comboSortBySemester.setItems(listSemesters);
        comboSortBySemester.valueProperty().addListener((observableValue, integer, t1) ->{
            numberSemester = t1;
        });
    }

    private void initTableProgress(ObservableList<Student> listStudent){
        tableProgress.setItems(listStudent);
        columnFirstName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getFirst_name()));
        columnLastName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getLast_name()));
        columnPatronymic.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getPatronymic()));
        columnRating.setCellValueFactory(p -> {

//            listRatings;
            ObservableList<String> list = FXCollections.observableArrayList();
            Set<Progress> listProgress = p.getValue().getProgresses();
                for (Progress progress : listProgress) {
                    if (progress.getStudent().getId() == p.getValue().getId()
                            && progress.getLesson()
                            .getDisciplineSemester()
                            .getDisciplineLearningPlan()
                            .getDiscipline().getName().equals(lblDiscipline.getText())
                            && progress.getLesson()
                            .getDisciplineSemester()
                            .getSemester().getNumberSemester() == numberSemester){

//                        if true {write value};
                            String str = progress.getLesson().getDateLesson().toString().substring(0, 10) + " - " + progress.getRating();
                            list.add(str);
                    }
                }

                if (list.size() != 0) {
                    return new SimpleObjectProperty<>(String.valueOf(list));
                } else {
                    return new SimpleObjectProperty<>("н");
                }
        });
        columnRating.setEditable(true);
        columnRating.cellValueFactoryProperty();

//        Calculating the average score;
        columnMidlBall.setCellValueFactory(p -> {
            Set<Progress> progresses = p.getValue().getProgresses();
            int rating = 0;
            int sizeRating = 0;

            if (!progresses.isEmpty()) {
                for (Progress progress : progresses) {
                    if (progress.getLesson().getDisciplineSemester()
                            .getDisciplineLearningPlan().getDiscipline()
                            .getName().equals(lblDiscipline.getText())
                            && progress.getLesson().getDisciplineSemester()
                            .getSemester().getNumberSemester() == numberSemester) {
                        sizeRating += 1;
                        rating += progress.getRating();
                    }
                }
                return new SimpleObjectProperty<>(String.format("%.1f",(double) rating / sizeRating));
            } else
            return new SimpleObjectProperty<>("0");
        });
    }

    private void clearScreen(){
        listLesson.clear();
        listTypeLesson.clear();
        listStudent.clear();
        listDisciplineSemester.clear();
        listSortGroup.clear();
        listSortDiscipline.clear();
        initialize();
    }

    @FXML
    void buttonAddLesson(ActionEvent event) throws ParseException {
        if (dateLesson.getValue() == null && txtThemeLesson.getText().isEmpty()
                && comboDiscipline.getValue() == null && comboTypeLesson.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля пусты.");
        } else if (dateLesson.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дата проведения урока пусто.");
        } else if (comboDiscipline.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дисциплина из семестра пусто.");
        } else if (comboTypeLesson.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Тип урока пусто.");
        } else if (txtThemeLesson.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Тема урока пусто.");
        } else {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Dao<Lesson, Integer> lessonDao = new LessonService(factory);
            Lesson lesson = new Lesson();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateLesson.getValue().toString().substring(0,10));
            lesson.setDateLesson(date);
            lesson.setTypeLesson(comboTypeLesson.getValue());
            lesson.setDisciplineSemester(comboDiscipline.getValue());
            lesson.setThemeLesson(txtThemeLesson.getText());
            lessonDao.save(lesson);

            clearScreen();

            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Урок добавлен.");
        }
    }

    @FXML
    void buttonAddRating(ActionEvent event) {
        if (txtRating.getText().isEmpty() && comboStudents.getValue() == null && comboLesson.getValue() == null ){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля пусты.");
        } else if (txtRating.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Оценка пусто.");
        } else if (comboStudents.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Студент пусто.");
        } else if (comboLesson.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Урок пусто.");
        }  else {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Dao<Progress, Integer> progressDao = new ProgressService(factory);
            Progress progress = new Progress();
            progress.setRating(Integer.parseInt(txtRating.getText()));
            progress.setStudent(comboStudents.getValue());
            progress.setLesson(comboLesson.getValue());

            progressDao.save(progress);

            clearScreen();

            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Оценка добавлена");
        }
    }

    @FXML
    void buttonUpdateRating(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/EditRatingWindow.fxml"));
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}

