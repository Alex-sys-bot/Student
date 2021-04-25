package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class ProgramStart extends Application {

//  start window;
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.setScene(new Scene(parent));
        stage.show();
    }

//  start program;
    public static void main(String[] args){
        launch(args);
    }
}
