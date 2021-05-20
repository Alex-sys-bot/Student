package com.company.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private AnchorPane parentWindow;

    @FXML
    private Tab tabGroup;

    @FXML
    private AnchorPane anchorPaneGroup;

    @FXML
    private Tab tabStudent;

    @FXML
    private AnchorPane anchorPaneStudent;

    @FXML
    private AnchorPane anchorPaneProgress;

    @FXML
    private MenuItem hide;

    private AnchorPane group;
    private AnchorPane student;
    private AnchorPane progress;

    @FXML
    public void initialize() {
//        call windows;
        callWindowStudent();
        callWindowGroup();
        callWindowProgress();

//        Auto size;
        rubberWindow();
    }

    private void callWindowGroup() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/groupWindow.fxml"));
        try {
            group = loader.load();
            anchorPaneGroup.getChildren().add(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void callWindowStudent(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/studentWindow.fxml"));
        try {
         student = loader.load();
         anchorPaneStudent.getChildren().add(student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void callWindowProgress(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/progressWindow.fxml"));
        try {
            progress = loader.load();
            anchorPaneProgress.getChildren().add(progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rubberWindow() {
        parentWindow.widthProperty().addListener((obj, oldValue, newValue) -> {
            group.setPrefWidth(newValue.doubleValue());
            student.setPrefWidth(newValue.doubleValue());
            progress.setPrefWidth(newValue.doubleValue());

            anchorPaneGroup.setPrefWidth(newValue.doubleValue());
            anchorPaneStudent.setPrefWidth(newValue.doubleValue());
            anchorPaneProgress.setPrefWidth(newValue.doubleValue());
        });
    }

    @FXML
    public void buttonOpenDisciplineWindow(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/disciplineWindow.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    @FXML
    void buttonOpenLearningPlaneWindow(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/learningPlanWindow.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void buttonOpenSpecialtyWindow(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/specialtyWindow.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void buttonOpenTeachersWindow(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/teachersWindow.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    @FXML
    void buttonCloseProgram(ActionEvent event) throws IOException {
        System.exit(1);
    }

    @FXML
    void upScreen(){
        initialize();
    }

}
