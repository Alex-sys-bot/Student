package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.Role;
import com.company.model.Student;
import com.company.model.User;
import com.company.service.RoleService;
import com.company.service.UserService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AdministratorWindowController {

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> columnId;

    @FXML
    private TableColumn<User, String> columnLogin;

    @FXML
    private TableColumn<User, String> columnPass;

    @FXML
    private TableColumn<User, String> columnRole;

    @FXML
    private TextField txtLogin;

    @FXML
    private ComboBox<Role> comboRole;

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblStatus;

    private  User user;

    private final ObservableList<User> listUsers = FXCollections.observableArrayList();
    private final ObservableList<Role> listRoles = FXCollections.observableArrayList();

    public void initialize(){
        takeDataFromDataBase();
        initTableUsers();
        search();
        takeDataFromTable();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<User,Integer> userIntegerDao = new UserService(factory);
        listUsers.addAll(userIntegerDao.returnAll());

        Dao<Role, Integer> roleIntegerDao = new RoleService(factory);
        listRoles.addAll(roleIntegerDao.returnAll());
        comboRole.setItems(listRoles);
    }

    private void search(){
        txtSearch.textProperty().addListener((obj, oldValue, newValue) -> {
            FilteredList<User> list = new FilteredList<>(listUsers, s -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                if (s.getLogin().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }

                return false;
            });
            tableUsers.setItems(list);
        });
    }

    private void initTableUsers(){
        tableUsers.setItems(listUsers);
        columnId.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getId()));
        columnLogin.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getLogin()));
        columnPass.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getPassword()));
        columnRole.setCellValueFactory(u -> new SimpleObjectProperty<>(u.getValue().getRole().getRole()));
    }

    private void clearScreen(){
        listRoles.clear();
        listUsers.clear();
        initialize();
    }

    private void takeDataFromTable(){
        tableUsers.getSelectionModel().selectedItemProperty().addListener((obj, oldValue, newValue) -> {
            if (newValue != null){
                user = newValue;
                txtLogin.setText(newValue.getLogin());
                txtPass.setText((newValue.getPassword()));
            }
        });
    }

    @FXML
    void buttonAddUser(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<User, Integer> userIntegerDao = new UserService(factory);

        User user = new User();
        if (txtLogin.getText().isEmpty()){
            lblStatus.setText("Поле Логин пусто");
        } else if (txtPass.getText().isEmpty()){
            lblStatus.setText("Поле Пароль пусто");
        } else if (comboRole.getValue() == null){
            lblStatus.setText("Поле Роль пусто");
        } else {
            user.setLogin(txtLogin.getText());
            user.setPassword(txtPass.getText());
            user.setRole(comboRole.getValue());
            userIntegerDao.save(user);
            clearScreen();
            lblStatus.setText("Пользователь добавлен");
        }
    }

    @FXML
    void buttonUpdateUser(ActionEvent event) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<User, Integer> userIntegerDao = new UserService(factory);

        if (txtLogin.getText().isEmpty()){
            lblStatus.setText("Поле Логин пусто");
        } else if (txtPass.getText().isEmpty()){
            lblStatus.setText("Поле Пароль пусто");
        } else if (comboRole.getValue() == null){
            lblStatus.setText("Поле Роль пусто");
        } else {
            user.setRole(user.getRole());
            user.setLogin(txtLogin.getText());
            user.setPassword(txtPass.getText());
            user.setRole(comboRole.getValue());
            userIntegerDao.update(user);
            clearScreen();
            lblStatus.setText("Данные изменены");
        }
    }

}
