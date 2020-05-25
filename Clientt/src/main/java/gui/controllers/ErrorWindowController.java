package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorWindowController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea error_msg_field;

    @FXML
    private Button error_ok_button;


    @FXML
    void initialize() {
    }

    public void showErrorWindow(App app, String msg) {
        error_msg_field.setText(msg);
    }

    public void closeWindow(){
        Stage stage = (Stage) error_ok_button.getScene().getWindow();
        stage.close();
    }
}
