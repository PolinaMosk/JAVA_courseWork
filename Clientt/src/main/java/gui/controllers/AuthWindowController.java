package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;


public class AuthWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField auth_pwd;

    @FXML
    private Button auth_ready;

    @FXML
    private TextField auth_userName;
    private App app;
    private MainWindowController MWController;
    private ErrorWindowController EWController;


    @FXML
    void initialize() {

    }
    void showErrorWindow(String msg){
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/errorWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Error!");
            stage.show();
            EWController = loader.getController();
            EWController.showErrorWindow(app, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadWindow(App app) {
        this.app = app;
    }
    public void executeAuth() {
        if (auth_pwd.getText().equals("") || auth_userName.getText().equals("")) {
            showErrorWindow("Unsuccessful authorizarion");
            return;
        }
        User user = new User(auth_userName.getText(), auth_pwd.getText());
        app.getAuthApi().signIn(user).subscribe(resp -> {
            if (resp.isSuccessful()) {
                app.setToken("Bearer " + resp.body());
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_window.fxml"));
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Wholesale Company Database");
                stage.show();
                app.primaryStage.close();
                MWController = loader.getController();
                MWController.loadWindow(app);
            } else {
                showErrorWindow("Unsuccessful authorizarion");
            }
        });
    }
}
