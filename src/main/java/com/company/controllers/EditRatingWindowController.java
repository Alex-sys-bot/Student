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

    @FXML
    private Button updateRating;

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

    public void close(){
        updateRating.setDisable(true);
    }

    public void open(){
        updateRating.setDisable(false);
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

    private void exportToExel() throws IOException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(new Stage());
        String pathToFile = file.getAbsolutePath();

        int sizeTableProgress = tableProgress.getColumns().size();
        XSSFWorkbook progress = new XSSFWorkbook();
        Sheet sheet = progress.createSheet();

        Row columnNames = sheet.createRow(0);

        for (int i = 0; i < sizeTableProgress; i++) {
            Cell cell = columnNames.createCell(i);
            cell.setCellValue(tableProgress.getColumns().get(i).getText());
        }

        for (int i = 0; i < tableProgress.getItems().size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell lastName = row.createCell(0);
            Cell firstName = row.createCell(1);
            Cell patronymic = row.createCell(2);
            Cell group = row.createCell(3);
            Cell date = row.createCell(4);
            Cell rating = row.createCell(5);
            Cell themLesson = row.createCell(6);
            Cell discipline = row.createCell(7);

            lastName.setCellValue(tableProgress.getItems().get(i).getStudent().getLast_name());
            firstName.setCellValue(tableProgress.getItems().get(i).getStudent().getFirst_name());
            patronymic.setCellValue(tableProgress.getItems().get(i).getStudent().getPatronymic());
            group.setCellValue(tableProgress.getItems().get(i).getStudent().getCourseGroup().getGrup().getNameGroup());
            date.setCellValue(String.valueOf(tableProgress.getColumns().get(4).getCellObservableValue(i).getValue()));
            rating.setCellValue(tableProgress.getItems().get(i).getRating());
            themLesson.setCellValue(tableProgress.getItems().get(i).getLesson().getThemeLesson());
            discipline.setCellValue(String.valueOf(tableProgress.getColumns().get(7).getCellObservableValue(i).getValue()));
        }

//        sheet.autoSizeColumn(1);
            progress.write(new FileOutputStream(pathToFile + ".xlsx"));
            progress.close();

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

    @FXML
    void buttonExport() throws IOException {
        exportToExel();
    }
}

