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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private ComboBox<Semester> comboSortBySemester;


    ObservableList<Student> listStudent = FXCollections.observableArrayList();
    ObservableList<DisciplineSemester> listDisciplineSemester = FXCollections.observableArrayList();
    ObservableList<TypeLesson> listTypeLesson = FXCollections.observableArrayList();
    ObservableList<Lesson> listLesson = FXCollections.observableArrayList();

    ObservableList<Discipline> listSortDiscipline = FXCollections.observableArrayList();
    ObservableList<CourseGroup> listSortGroup = FXCollections.observableArrayList();
    ObservableList<Semester> listSortSemester = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableProgress(listStudent);
        sortByDiscipline();
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

        Dao<Semester, Integer> semesterDao = new SemesterService(factory);
        listSortSemester.addAll(semesterDao.returnAll());
        comboSortBySemester.setItems(listSortSemester);
    }

    private void sortByDiscipline(){
        ObservableList<Student> students = FXCollections.observableArrayList();
        comboSortByDiscipline.valueProperty().addListener((obj, oldValue, newValue) -> {
            students.clear();
            lblDiscipline.setText(newValue.getName());

            for (Student student: listStudent) {
                students.addAll(student);
            }
            initTableProgress(students);
            sortByGroup(students);
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
            tableProgress.setItems(students);
            sortBySemester(students);
        });
    }

    private void sortBySemester(ObservableList<Student> listStudent){
        comboSortBySemester.valueProperty().addListener((obj, oldValue, newValue) -> {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();

            Dao<Semester, Integer> semesterIntegerDao = new SemesterService(factory);

            ObservableList<Student> students = FXCollections.observableArrayList();
            students.clear();

            Set<Semester> semesterSet = new HashSet<>();
            semesterSet.addAll(semesterIntegerDao.returnAll());

            for (Semester semester: semesterSet) {
                if (semester.getId() == newValue.getId()) {
                    System.out.println(semester);
                    for (Student student: listStudent) {
//                        students.addAll(query.list());
                    }
                }
            }

//            tableProgress.setItems(students);
        });
    }

    private void initTableProgress(ObservableList<Student> listStudent){
        tableProgress.setItems(listStudent);
        columnFirstName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getFirst_name()));
        columnLastName.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getLast_name()));
        columnPatronymic.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getPatronymic()));
        columnRating.setCellValueFactory(p -> {

            ObservableList<String> list = FXCollections.observableArrayList();
            Set<Progress> listProgress = p.getValue().getProgresses();
                for (Progress progress : listProgress) {
                    if (progress.getStudent().getId() == p.getValue().getId()
                            && progress.getLesson()
                            .getDisciplineSemester()
                            .getDisciplineLearningPlan()
                            .getDiscipline().getName().equals(lblDiscipline.getText())) {
                        String str = progress.getLesson().getDateLesson().toString().substring(0, 10) + " - " + progress.getRating();
                        list.add(str);
                    }
                }

                if (list.size() != 0) {
                    return new SimpleObjectProperty<>(String.valueOf(list));
                } else {
                    return new SimpleObjectProperty<>("");
                }
        });



//        Calculating the average score;
        columnMidlBall.setCellValueFactory(p -> {
            Set<Progress> progresses = p.getValue().getProgresses();
            int rating = 0;
            int sizeRating = 0;

            if (!progresses.isEmpty()) {
                for (Progress progress : progresses) {
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

    private void clearScreen(){
        listLesson.clear();
        listTypeLesson.clear();
        listStudent.clear();
        listDisciplineSemester.clear();
        listSortGroup.clear();
        listSortSemester.clear();
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
}

