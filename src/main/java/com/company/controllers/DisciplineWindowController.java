package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.Discipline;
import com.company.service.DisciplineService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DisciplineWindowController {

    @FXML
    private TableView<Discipline> tableDiscipline;

    @FXML
    private TableColumn<Discipline, Integer> columnId;

    @FXML
    private TableColumn<Discipline, String> columnNameDiscipline;

    @FXML
    private TextField txtNameDiscipline;

    @FXML
    private Label lblStatus;

    @FXML
    private Button addDiscipline;

    @FXML
    private Button updateDiscipline;

    private final ObservableList<Discipline> listDiscipline = FXCollections.observableArrayList();
    private Discipline discipline;

    @FXML
    public void initialize(){
        takeDataFromDataBase();
        initTableDiscipline();
        takeDataFromTable();
    }

    public void close(){
        addDiscipline.setDisable(true);
        updateDiscipline.setDisable(true);
    }

    public void open(){
        addDiscipline.setDisable(false);
        updateDiscipline.setDisable(false);
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Dao<Discipline, Integer> daoDiscipline = new DisciplineService(factory);
        listDiscipline.addAll(daoDiscipline.returnAll());
    }

    private void initTableDiscipline(){
        tableDiscipline.setItems(listDiscipline);
        columnId.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getId()));
        columnNameDiscipline.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getName()));
    }

    private void takeDataFromTable(){
        tableDiscipline.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) -> {
            discipline = newValue;
            txtNameDiscipline.setText(discipline.getName());
        });
    }

    @FXML
    void addDiscipline(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtNameDiscipline.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("?????????????????? ????????: ???????????????? ????????????????????.");
        } else {
            Dao<Discipline, Integer> daoDiscipline = new DisciplineService(factory);
            Discipline discipline = new Discipline();
            discipline.setName(txtNameDiscipline.getText());
            daoDiscipline.save(discipline);
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("???????????????????? ??????????????????.");

            listDiscipline.clear();
            initialize();
        }
    }

    @FXML
    void updateDiscipline(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        if (txtNameDiscipline.getText().isEmpty()){
            lblStatus.setTextFill(Color.RED);
            lblStatus.setText("?????????????????? ????????: ???????????????? ????????????????????.");
        } else {
            Dao<Discipline, Integer> daoDiscipline = new DisciplineService(factory);
            Discipline upDiscipline;
            upDiscipline = discipline;
            upDiscipline.setName(txtNameDiscipline.getText());
            daoDiscipline.update(upDiscipline);

            listDiscipline.clear();
            initialize();
        }
    }
}
