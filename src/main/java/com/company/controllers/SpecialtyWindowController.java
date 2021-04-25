package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.Qualification;
import com.company.model.Specialty;
import com.company.service.QualificationService;
import com.company.service.SpecialtyService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class SpecialtyWindowController {

    @FXML
    private TableView<Qualification> tableSpecialty;

    @FXML
    private TableColumn<Qualification, String> columnNameSpecialty;

    @FXML
    private TableColumn<Qualification, String> columnCodeSpecialty;

    @FXML
    private TableColumn<Qualification, String> columnQualificationsSpecialty;

    @FXML
    private TextField txtNameSpecialty;

    @FXML
    private TextField txtCodeSpecialty;


    @FXML
    private TextField txtNameQualification;

    @FXML
    private ComboBox<Specialty> comboSpecialties;

    @FXML
    private Label lblStatus;

    @FXML
    private Button buttonMainWindowHide;

    @FXML
    private ComboBox<Specialty> comboSortSpecialties;


    ObservableList<Specialty> listSpecialty = FXCollections.observableArrayList();
    ObservableList<Qualification> listQualification = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableSpecialty();
        sortSpecialtyByName();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<Specialty, Integer> daoSpecialty = new SpecialtyService(factory);
        listSpecialty.addAll(daoSpecialty.returnAll());

        Dao<Qualification, Integer> daoQualification = new QualificationService(factory);
        listQualification.addAll(daoQualification.returnAll());
    }

    private void initTableSpecialty(){
        comboSpecialties.setItems(listSpecialty);

        tableSpecialty.setItems(listQualification);
        columnNameSpecialty.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getSpecialty().getName()));
        columnCodeSpecialty.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getSpecialty().getCode()));
        columnQualificationsSpecialty.setCellValueFactory(s -> new SimpleObjectProperty<>(s.getValue().getName()));
    }

    private void sortSpecialtyByName(){
        comboSortSpecialties.setItems(listSpecialty);
        comboSortSpecialties.valueProperty().addListener((obj, oldValue, newValue) -> {
            ObservableList<Qualification> sortListSpecialty = FXCollections.observableArrayList();
            for (Qualification qualification: listQualification) {
                if (qualification.getSpecialty().getName().equals(newValue.getName())){
                    sortListSpecialty.addAll(qualification);
                }
            }
            tableSpecialty.setItems(sortListSpecialty);
        });
    }

    @FXML
    void buttonAddQualification(ActionEvent event){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<Qualification, Integer> daoQualification = new QualificationService(factory);

        if (txtNameQualification.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Название квалификации пустое");
        } else if(comboSpecialties.getValue() == null){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Специальность не выбрана");
        } else if(!txtNameQualification.getText().isEmpty() && comboSpecialties.getValue() != null) {
            Qualification qualification = new Qualification();
            qualification.setName(txtNameQualification.getText());
            qualification.setSpecialty(comboSpecialties.getValue());
            daoQualification.save(qualification);
            lblStatus.setText("Выполнено");
        }

//        clear screen;
        listQualification.clear();
        listSpecialty.clear();
        initialize();
    }

    @FXML
    void buttonAddSpecialty(ActionEvent event){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<Specialty, Integer> daoSpecialty = new SpecialtyService(factory);

        if (txtNameSpecialty.getText().isEmpty() && txtCodeSpecialty.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поля: Название специальности и Код специальности пустые");
        } else if(txtNameSpecialty.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Название специальности пустое");
        } else if(txtCodeSpecialty.getText().isEmpty()) {
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("Поле: Код специальности пустое");
        } else if (!txtNameSpecialty.getText().isEmpty() && !txtCodeSpecialty.getText().isEmpty()){
            Specialty specialty = new Specialty();
            specialty.setName(txtNameSpecialty.getText());
            specialty.setCode(txtCodeSpecialty.getText());
            daoSpecialty.save(specialty);

            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Выполнено");
        }


//        clear screen;
        listQualification.clear();
        listSpecialty.clear();
        initialize();
    }


}
