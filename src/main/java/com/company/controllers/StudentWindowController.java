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
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @FXML
    private Button addStudent;

    @FXML
    private Button updateStudent;

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

    public void checkerRole(){
        addStudent.setDisable(true);
        updateStudent.setDisable(true);
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

    private void exportToExel() throws IOException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(new Stage());
        String pathToFile = file.getAbsolutePath();

        int sizeTableStudent = tableStudent.getColumns().size();
        XSSFWorkbook student = new XSSFWorkbook();
        Sheet sheet = student.createSheet();
        Row nameColumns = sheet.createRow(0);

        for (int i = 0; i < sizeTableStudent; i++) {
            Cell cell = nameColumns.createCell(i);
            cell.setCellValue(tableStudent.getColumns().get(i).getText());
        }

        for (int i = 0; i < tableStudent.getItems().size(); i++) {
            Row valueCell = sheet.createRow(i + 1);

            Cell id = valueCell.createCell(0);
            Cell lastName = valueCell.createCell(1);
            Cell firstName = valueCell.createCell(2);
            Cell patronymic = valueCell.createCell(3);
            Cell dateBirthday = valueCell.createCell(4);
            Cell receiptOfDate = valueCell.createCell(5);
            Cell numberOfCreditBook = valueCell.createCell(6);
            Cell group = valueCell.createCell(7);
            Cell numberPhone  = valueCell.createCell(8);

            id.setCellValue(tableStudent.getItems().get(i).getId());
            lastName.setCellValue(tableStudent.getItems().get(i).getLast_name());
            firstName.setCellValue(tableStudent.getItems().get(i).getFirst_name());
            patronymic.setCellValue(tableStudent.getItems().get(i).getPatronymic());
            dateBirthday.setCellValue(String.valueOf(tableStudent.getColumns().get(4).getCellObservableValue(i).getValue()));
            receiptOfDate.setCellValue(String.valueOf(tableStudent.getColumns().get(5).getCellObservableValue(i).getValue()));
            numberOfCreditBook.setCellValue(tableStudent.getItems().get(i).getNumberCreditBook());
            group.setCellValue(tableStudent.getItems().get(i).getCourseGroup().getGrup().getNameGroup());
            numberPhone.setCellValue(tableStudent.getItems().get(i).getPhone());
        }

        student.write(new FileOutputStream(pathToFile + ".xlsx"));
        student.close();
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

    @FXML
    void buttonExport(ActionEvent event) throws IOException {
        exportToExel();
    }
}
