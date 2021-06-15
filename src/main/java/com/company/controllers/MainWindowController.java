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
import org.apache.bcel.generic.IF_ACMPEQ;

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
    private MenuItem discipline;

    @FXML
    private MenuItem teachers;

    @FXML
    private MenuItem specialty;

    @FXML
    private MenuItem learningPlan;

    @FXML
    private MenuItem administrator;

    private AnchorPane group;
    private AnchorPane student;
    private AnchorPane progress;

    private String role;

    @FXML
    public void initialize() {
//        call windows;
        checkerRole();
        callWindowStudent();
        callWindowGroup();
        callWindowProgress();

//        Auto size;
        rubberWindow();
    }

    public void takeRole(String role){
        this.role = role;
    }

    private void checkerRole(){
            if (AuthorisationWindowController.role.equals("Администратор")){
                discipline.setDisable(true);
                teachers.setDisable(true);
                specialty.setDisable(true);
                learningPlan.setDisable(true);
            } else if (AuthorisationWindowController.role.equals("Разработчик")){
                discipline.setDisable(false);
                teachers.setDisable(false);
                specialty.setDisable(false);
                learningPlan.setDisable(false);
                administrator.setDisable(false);
            } else {
                administrator.setDisable(true);
            }
    }

    private void callWindowGroup() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/groupWindow.fxml"));
        try {
            group = loader.load();
            GroupWindowController groupWindowController = loader.getController();

            if (AuthorisationWindowController.role.equals("Директор")
            || AuthorisationWindowController.role.equals("Зам Директора")
                    || AuthorisationWindowController.role.equals("Администратор")) {
            groupWindowController.checkRole();
        } else if (AuthorisationWindowController.role.equals("Преподаватель")){
                groupWindowController.checkRole();
                administrator.setDisable(true);
        }

            anchorPaneGroup.getChildren().add(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void callWindowStudent(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/studentWindow.fxml"));
        try {
         student = loader.load();
         StudentWindowController studentWindowController = loader.getController();

         if (AuthorisationWindowController.role.equals("Директор")
                 || AuthorisationWindowController.role.equals("Зам Директора")
                 || AuthorisationWindowController.role.equals("Администратор")){
             studentWindowController.checkerRole();
         } else if (AuthorisationWindowController.role.equals("Преподаватель")){
             studentWindowController.checkerRole();
             administrator.setDisable(true);
         }

         anchorPaneStudent.getChildren().add(student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void callWindowProgress(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/progressWindow.fxml"));
        try {
            progress = loader.load();
            ProgressWindowController progressWindowController = loader.getController();

            if (AuthorisationWindowController.role.equals("Директор")
                    || AuthorisationWindowController.role.equals("Зам Директора")
                    || AuthorisationWindowController.role.equals("Администратор")){
                progressWindowController.checkRole();
            } else if (AuthorisationWindowController.role.equals("Секретарь УЧ")){
                progressWindowController.checkRole();
                administrator.setDisable(true);
            }

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
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/view/disciplineWindow.fxml"));
        AnchorPane anchorPane = loader.load();
        DisciplineWindowController disciplineWindowController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(anchorPane));
        stage.initModality(Modality.APPLICATION_MODAL);

        if (role.equals("Директор")
                || role.equals("Зам Директора")
                || role.equals("Преподаватель")){
            disciplineWindowController.close();
        } else {
            disciplineWindowController.open();
        }

        stage.show();
    }

    @FXML
    void buttonOpenLearningPlaneWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/learningPlanWindow.fxml"));
        AnchorPane anchorPane = loader.load();
        LearningPlanWindowController learningPlanWindowController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(anchorPane));
        stage.initModality(Modality.APPLICATION_MODAL);

        if (role.equals("Директор")
                || role.equals("Зам Директора")
                || role.equals("Преподаватель")){
            learningPlanWindowController.close();
        } else {
            learningPlanWindowController.open();
        }

        stage.show();
    }

    @FXML
    void buttonOpenSpecialtyWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/specialtyWindow.fxml"));
        AnchorPane anchorPane = loader.load();
        SpecialtyWindowController specialtyWindowController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(anchorPane));
        stage.initModality(Modality.APPLICATION_MODAL);

        if (role.equals("Директор")
                || role.equals("Зам Директора")
                || role.equals("Преподаватель")){
            specialtyWindowController.close();
        } else {
            specialtyWindowController.open();
        }

        stage.show();
    }

    @FXML
    void buttonOpenTeachersWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teachersWindow.fxml"));
        AnchorPane anchorPane = loader.load();
        TeachersWindowController teachersWindowController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(anchorPane));
        stage.initModality(Modality.APPLICATION_MODAL);

        if (role.equals("Директор")
                || role.equals("Зам Директора")
                || role.equals("Преподаватель")){
            teachersWindowController.close();
        } else {
            teachersWindowController.open();
        }

        stage.show();
    }

    @FXML
    void buttonAdministrator(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/administratorWindow.fxml"));
        stage.setTitle("Студент");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.setScene(new Scene(parent));
        stage. initModality(Modality.APPLICATION_MODAL);
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
