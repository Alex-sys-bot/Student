package com.company.controllers;

import com.company.dao.Dao;
import com.company.model.User;
import com.company.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.OneToMany;
import java.io.IOException;

public class AuthorisationWindowController {

    @FXML
    private Button hide;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblStatus;

    private final ObservableList<User> listUsers = FXCollections.observableArrayList();

    public void initialize(){
        takeDataFromDataBase();
    }

    private void takeDataFromDataBase(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<User,Integer> userIntegerDao = new UserService(factory);
        listUsers.addAll(userIntegerDao.returnAll());
    }

    @FXML
    void buttonSignIn(ActionEvent event) throws IOException {

        if (txtLogin.getText().isEmpty()){
            lblStatus.setText("Поле Логин пустое");
        } else if (txtPassword.getText().isEmpty()){
            lblStatus.setText("Поле Пароль пустое");
        } else {
            for (User user : listUsers) {
                if (!user.getLogin().equals(txtLogin.getText()) || !user.getPassword().equals(txtPassword.getText())){
                    lblStatus.setText("Неверные Логин или Пароль");
                } else if (user.getLogin().equals(txtLogin.getText()) || user.getPassword().equals(txtPassword.getText())){
                    Stage stage = new Stage();
                    Parent parent = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));
                    stage.setTitle("Студент");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
                    stage.setScene(new Scene(parent));
                    stage.show();

                    hide.getScene().getWindow().hide();
                }
            }
        }
    }
}
