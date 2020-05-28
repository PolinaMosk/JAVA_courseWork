package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Goods;
import model.Warehouse1;
import model.Warehouse2;

public class WAddWindowController {
    private App app;
    private Integer ware;
    private MainWindowController MWController;
    private ErrorWindowController EWController;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField w_addWindow_name;

    @FXML
    private TextField w_addWindow_number;

    @FXML
    private TextField w_addWindow_priority;

    @FXML
    private Button w_addWindow_ready_btn;


    @FXML
    void initialize() {
    }

    public void loadWindow(App app, Integer ware) {
        this.app = app;
        this.ware = ware;
    }

    void showErrorWindow(String msg) {
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

    public void executeAdding() {
        if (w_addWindow_name.getText().equals("") || w_addWindow_priority.getText().equals("") ||
                w_addWindow_number.getText().equals("")) {
            showErrorWindow("You did't set all parameters");
            return;
        }
        try {
            Goods good = new Goods(w_addWindow_name.getText(), Float.parseFloat(w_addWindow_priority.getText()));
            if (ware == 1) {
                Warehouse1 warehouse1 = new Warehouse1(good, Integer.parseInt(w_addWindow_number.getText()));
                app.getW1Api().addGoodToWare(app.getToken(), warehouse1).subscribe(newWare -> {
                    if (!newWare.isSuccessful()) {
                        if (newWare.code() == 403) {
                            showErrorWindow("Access denied");
                        }
                        else showErrorWindow("No good with such name");
                    }
                    else {
                        Stage stage = (Stage) w_addWindow_ready_btn.getScene().getWindow();
                        stage.close();
                    }
                });
            } else {
                Warehouse2 warehouse2 = new Warehouse2(good, Integer.parseInt(w_addWindow_number.getText()));
                app.getW2Api().addGoodToWare(app.getToken(), warehouse2).subscribe(newWare -> {
                    if (!newWare.isSuccessful()) {
                        if (newWare.code() == 403) {
                            showErrorWindow("Access denied");
                        } else showErrorWindow("No good with such name");
                    }
                    else {
                        Stage stage = (Stage) w_addWindow_ready_btn.getScene().getWindow();
                        stage.close();
                    }
                });
            }

        } catch (NumberFormatException ex) {
            showErrorWindow("Invalid priority or quantity");
        } catch (Exception ex) {
            showErrorWindow(ex.toString());
        }
    }

}
