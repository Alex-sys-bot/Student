package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.*;
import com.company.service.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class StudentWindowController {

    @FXML
    private TableView<Student> tableStudent;

    @FXML
    private TableColumn<Student, String> columnLastName;

    @FXML
    private TableColumn<Student, String> columnFirstName;

    @FXML
    private TableColumn<Student, String> columnPatronymic;

    @FXML
    private TableColumn<Student, String> columnDateBirthday;

    @FXML
    private TableColumn<Student, String> columnNumberPhone;

    @FXML
    private TableColumn<Student, String> columnDateOfReceipt;

    @FXML
    private TableColumn<Student, String> columnNumberCreditOfBook;

    @FXML
    private TableColumn<Student, String> columnGroup;

    @FXML
    private TableColumn<Student, Integer> columnId;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtPatronimyc;

    @FXML
    private TextField txtNumberPhone;

    @FXML
    private TextField txtNumberCreditOfBook;

    @FXML
    private ComboBox<CourseGroup> comboGroup;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private DatePicker dateOfReceipt;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtSearch;

    private final ObservableList<Student> listStudent = FXCollections.observableArrayList();
    private final ObservableList<CourseGroup> listGroup = FXCollections.observableArrayList();
    private Student student = new Student();

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableStudent();
        comboGroup.setItems(listGroup);
        searchStudent();
        takeDataFromTable();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Dao<Student, Integer> daoStudent = new StudentService(factory);
        listStudent.addAll(daoStudent.returnAll());

        Dao<CourseGroup, Integer> daoCourseGroupGroup = new CourseGroupService(factory);
        listGroup.addAll(daoCourseGroupGroup.returnAll());
    }

    private void searchStudent(){
        txtSearch.textProperty().addListener((obj, oldValue, newValue) -> {
            FilteredList<Student> list = new FilteredList<>(listStudent, s -> {
               if (newValue == null || newValue.isEmpty()){
                   return true;
               }

               if (s.getFirst_name().toLowerCase().contains(newValue.toLowerCase())){
                   return true;
               }

                if (s.getLast_name().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }

                if (s.getPatronymic().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }

                if (s.getPhone().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }

                if (s.getNumberCreditBook().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }

                if (s.getCourseGroup().getCipher().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }

                if (s.getCourseGroup().getGrup().getNameGroup().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }

               return false;
            });
            tableStudent.setItems(list);
        });
    }

    private void initTableStudent(){
        tableStudent.setItems(listStudent);
        columnId.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getId()));
        columnLastName.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getLast_name()));
        columnFirstName.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getFirst_name()));
        columnPatronymic.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getPatronymic()));
        columnDateBirthday.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getDateBirthday().toString().substring(0,10)));
        columnDateOfReceipt.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getReceiptDate().toString().substring(0,10)));
        columnNumberCreditOfBook.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getNumberCreditBook()));
        columnNumberPhone.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getPhone()));
        columnGroup.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getCourseGroup().getGrup().getNameGroup()));
    }

    private void clearScreen(){
        listStudent.clear();
        listGroup.clear();
        initialize();
    }

    private void takeDataFromTable(){
        tableStudent.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) ->{
            if (newValue != null) {
                student = newValue;
                txtLastName.setText(student.getLast_name());
                txtFirstName.setText(student.getFirst_name());
                txtPatronimyc.setText(student.getPatronymic());
                dateBirthday.setValue(LocalDate.parse(student.getDateBirthday().toString().substring(0, 10)));
                txtNumberPhone.setText(student.getPhone());
                dateOfReceipt.setValue(LocalDate.parse(student.getReceiptDate().toString().substring(0, 10)));
                txtNumberCreditOfBook.setText(student.getNumberCreditBook());
            }
        });
    }

    @FXML
    void buttonAddStudent(ActionEvent event) throws ParseException {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtFirstName.getText().isEmpty()
                && txtLastName.getText().isEmpty()
                && txtPatronimyc.getText().isEmpty()
                && txtNumberCreditOfBook.getText().isEmpty()
                && comboGroup.getValue() == null
                && dateBirthday.getValue() == null
                && dateOfReceipt.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Обязательные поля пусты.");
        } else if (txtFirstName.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Имя не задано");
        } else if (txtLastName.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Фамилия не задано");
        } else if (txtPatronimyc.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Отчество не задано");
        } else if (dateBirthday.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: День рождения не задано");
        } else if (dateOfReceipt.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дата поступления не задано");
        } else if (txtNumberCreditOfBook.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Номер зачётной книги не задано");
        } else if (comboGroup.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Группа не выбрана");
        } else if (!txtFirstName.getText().isEmpty()
                && !txtLastName.getText().isEmpty()
                && !txtPatronimyc.getText().isEmpty()
                && !txtNumberCreditOfBook.getText().isEmpty()
                && comboGroup.getValue() != null
                && dateBirthday.getValue() != null
                && dateOfReceipt.getValue() != null){
            lblStatus.setTextFill(Color.GREEN);

            Dao<Student, Integer> daoStudent = new StudentService(factory);
            Student student = new Student();
            student.setFirst_name(txtFirstName.getText());
            student.setLast_name(txtLastName.getText());
            student.setPatronymic(txtPatronimyc.getText());
            student.setNumberCreditBook(txtNumberCreditOfBook.getText());
            student.setPhone(txtNumberPhone.getText());
            student.setCourseGroup(comboGroup.getValue());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date birthday = simpleDateFormat.parse(dateBirthday.getValue().toString());
            student.setDateBirthday(birthday);

            Date receiptDate = simpleDateFormat.parse(dateOfReceipt.getValue().toString());
            student.setReceiptDate(receiptDate);

            daoStudent.save(student);

            lblStatus.setText("Студент добавлен");

//            clear screen;
            clearScreen();
        }
    }

    @FXML
    void buttonUpdateStudent(ActionEvent event) throws ParseException {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtFirstName.getText().isEmpty()
                && txtLastName.getText().isEmpty()
                && txtPatronimyc.getText().isEmpty()
                && txtNumberCreditOfBook.getText().isEmpty()
                && comboGroup.getValue() == null
                && dateBirthday.getValue() == null
                && dateOfReceipt.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Обязательные поля пусты.");
        } else if (txtFirstName.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Имя не задано");
        } else if (txtLastName.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Фамилия не задано");
        } else if (txtPatronimyc.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Отчество не задано");
        } else if (dateBirthday.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: День рождения не задано");
        } else if (dateOfReceipt.getValue() == null) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Дата поступления не задано");
        } else if (txtNumberCreditOfBook.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Номер зачётной книги не задано");
        } else if (!txtFirstName.getText().isEmpty()
                && !txtLastName.getText().isEmpty()
                && !txtPatronimyc.getText().isEmpty()
                && !txtNumberCreditOfBook.getText().isEmpty()
                && dateBirthday.getValue() != null
                && dateOfReceipt.getValue() != null){
            lblStatus.setTextFill(Color.GREEN);

            Dao<Student, Integer> daoStudent = new StudentService(factory);
            student.setFirst_name(txtFirstName.getText());
            student.setLast_name(txtLastName.getText());
            student.setPatronymic(txtPatronimyc.getText());
            student.setNumberCreditBook(txtNumberCreditOfBook.getText());
            if (comboGroup.getValue() == null){
                student.setCourseGroup(student.getCourseGroup());
            } else {
                student.setCourseGroup(comboGroup.getValue());
            }
            student.setPhone(txtNumberPhone.getText());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date birthday = simpleDateFormat.parse(dateBirthday.getValue().toString());
            student.setDateBirthday(birthday);

            Date receiptDate = simpleDateFormat.parse(dateOfReceipt.getValue().toString());
            student.setReceiptDate(receiptDate);

            daoStudent.update(student);

            lblStatus.setText("Данные изменены");
//            clear screen;
            clearScreen();
        }
    }
}
