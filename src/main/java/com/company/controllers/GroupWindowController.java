package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.Course;
import com.company.model.CourseGroup;
import com.company.model.Grup;
import com.company.model.Student;
import com.company.service.CourseGroupService;
import com.company.service.CourseService;
import com.company.service.GroupService;
import com.company.service.StudentService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class GroupWindowController {

    @FXML
    private TableView<CourseGroup> tableCourseGroup;

    @FXML
    private TableColumn<CourseGroup, Integer> columnId;

    @FXML
    private TableColumn<CourseGroup, Integer> columnNumberOfOrder;

    @FXML
    private TableColumn<CourseGroup, String> columnDateCreateGroup;

    @FXML
    private TableColumn<CourseGroup, String> columnCipherGroup;

    @FXML
    private TableColumn<CourseGroup, String> columnNameGroup;

    @FXML
    private TableColumn<CourseGroup, Integer> columnCourseGroup;

    @FXML
    private TableColumn<CourseGroup, Integer> columnQuantityStudents;


    @FXML
    private TextField txtNumberOrder;

    @FXML
    private TextField txtCipherGroup;

    @FXML
    private DatePicker dateCreateGroup;

    @FXML
    private ComboBox<Course> comboCourse;

    @FXML
    private TextField txtNameGroup;

    @FXML
    private TextField txtLearningYear;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblQuantityAllStudentsInCourse;

    private final ObservableList<CourseGroup> listCourseGroup = FXCollections.observableArrayList();
    private final ObservableList<Course> listCourse = FXCollections.observableArrayList();
    private final ObservableList<Student> listStudent = FXCollections.observableArrayList();

    private CourseGroup courseGroup = new CourseGroup();
    private int quantityStudent = 0;

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableCourseGroup();
        comboCourse.setItems(listCourse);
        takeDataFromTable();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Dao<CourseGroup, Integer> daoCourseGroup = new CourseGroupService(factory);
        listCourseGroup.addAll(daoCourseGroup.returnAll());

        Dao<Course, Integer> daoCourse = new CourseService(factory);
        listCourse.addAll(daoCourse.returnAll());

        Dao<Student, Integer> daoStudent = new StudentService(factory);
        listStudent.addAll(daoStudent.returnAll());
    }

    private void initTableCourseGroup(){
        tableCourseGroup.setItems(listCourseGroup);
        columnId.setCellValueFactory(cg -> new SimpleObjectProperty<>(cg.getValue().getId()));
        columnNumberOfOrder.setCellValueFactory(cg -> new SimpleObjectProperty<>(cg.getValue().getGrup().getNumberOrder()));
        columnDateCreateGroup.setCellValueFactory(cg -> new SimpleObjectProperty<>(
                cg.getValue().getGrup().getDate().toString().substring(0,10)));
        columnCipherGroup.setCellValueFactory(cg -> new SimpleObjectProperty<>(cg.getValue().getCipher()));
        columnNameGroup.setCellValueFactory(cg -> new SimpleObjectProperty<>(cg.getValue().getGrup().getNameGroup()));
        columnCourseGroup.setCellValueFactory(cg -> new SimpleObjectProperty<>(cg.getValue().getCourse().getNumberCourse()));
        columnQuantityStudents.setCellValueFactory(cg -> new SimpleObjectProperty<>(cg.getValue().getStudents().size()));

//      number of students on the course
        quantityStudent = 0;
        for (Student student: listStudent) {
            if (student.getCourseGroup().getCourse().getNumberCourse() > 0){
                quantityStudent += 1;
                lblQuantityAllStudentsInCourse.setText("Общее колличество студентов: " + quantityStudent);
            }

        }
    }


    private void takeDataFromTable(){
        tableCourseGroup.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) -> {
            if (newValue != null) {
                courseGroup = newValue;
                txtNameGroup.setText(courseGroup.getGrup().getNameGroup());
                txtNumberOrder.setText(String.valueOf(courseGroup.getGrup().getNumberOrder()));
                txtCipherGroup.setText(courseGroup.getCipher());
                txtLearningYear.setText(courseGroup.getLearningYear());
                dateCreateGroup.setValue(LocalDate.parse(courseGroup.getGrup().getDate().toString().substring(0,10)));
            }
        });
    }

    private void clearScreen(){
        listStudent.clear();
        listCourse.clear();
        listCourseGroup.clear();
        initialize();
    }

    @FXML
    void buttonAddGroup(ActionEvent event) throws ParseException {
//        check null;
        if (txtNumberOrder.getText().isEmpty()
                && dateCreateGroup.getValue() == null
                && txtNameGroup.getText().isEmpty()
                && txtCipherGroup.getText().isEmpty()
                && txtLearningYear.getText().isEmpty()
                && comboCourse.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Обязательные поля пусты.");
        } else if (txtNumberOrder.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Номер приказа формирования группы пусто");
        } else if (dateCreateGroup.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дата формирования группы пусто");
        } else if (txtNameGroup.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Название группы пусто");
        } else if (txtCipherGroup.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Шифр группы пусто");
        } else if (txtLearningYear.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Учебный год пусто");
        } else if (comboCourse.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Номер курса пусто");
        } else {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Dao<Grup, Integer> daoGroup = new GroupService(factory);
            Grup group = new Grup();
            group.setNumberOrder(Integer.parseInt(txtNumberOrder.getText()));
            Date dateCreate = format.parse(dateCreateGroup.getValue().toString());
            group.setDate(dateCreate);
            group.setNameGroup(txtNameGroup.getText());
            daoGroup.save(group);

            Dao<CourseGroup, Integer> daoCourseGroup = new CourseGroupService(factory);
            CourseGroup courseGroup = new CourseGroup();
            courseGroup.setCipher(txtCipherGroup.getText());
            courseGroup.setLearningYear(txtLearningYear.getText());
            courseGroup.setGrup(group);
            courseGroup.setCourse(comboCourse.getValue());
            daoCourseGroup.save(courseGroup);

//            clear screen;
            clearScreen();

            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Группа добавлена");
        }
    }

    @FXML
    void buttonUpdateGroup(ActionEvent event) throws ParseException {
        if (txtNumberOrder.getText().isEmpty()
                && dateCreateGroup.getValue() == null
                && txtNameGroup.getText().isEmpty()
                && txtCipherGroup.getText().isEmpty()
                && txtLearningYear.getText().isEmpty()
                && comboCourse.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Обязательные поля пусты.");
        } else if (txtNumberOrder.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Номер приказа формирования группы пусто");
        } else if (dateCreateGroup.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дата формирования группы пусто");
        } else if (txtNameGroup.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Название группы пусто");
        } else if (txtCipherGroup.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Шифр группы пусто");
        } else if (txtLearningYear.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Учебный год пусто");
        } else {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Dao<Grup, Integer> daoGroup = new GroupService(factory);
            Grup grup = daoGroup.returnById(courseGroup.getGrup().getId());
            grup.setNumberOrder(Integer.parseInt(txtNumberOrder.getText()));
            Date dateCreate = format.parse(dateCreateGroup.getValue().toString());
            grup.setDate(dateCreate);
            grup.setNameGroup(txtNameGroup.getText());
            daoGroup.update(grup);

            Dao<CourseGroup, Integer> daoCourseGroup = new CourseGroupService(factory);
            courseGroup.setCipher(txtCipherGroup.getText());
            courseGroup.setLearningYear(txtLearningYear.getText());
            courseGroup.setGrup(grup);
            if (comboCourse.getValue() == null) {
                courseGroup.setCourse(courseGroup.getCourse());
            } else {
                courseGroup.setCourse(comboCourse.getValue());
            }
            daoCourseGroup.update(courseGroup);

            clearScreen();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Изменения внесены");
        }
    }
}
