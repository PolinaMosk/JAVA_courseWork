package gui.controllers;

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

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

public class WDeleteWindowController {
    private ErrorWindowController EWController;
    private MainWindowController MWController;
    App app;
    Integer ware;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField w_deleteWindow_goodId;

    @FXML
    private TextField w_deleteWindow_BatchId;

    @FXML
    private Button w_deleteWindow_ready;


    public void loadWindow(App app, Integer ware){
        this.app = app;
        this.ware = ware;
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

    public void executeRemoving(){
        if (w_deleteWindow_BatchId.getText().equals("") && w_deleteWindow_goodId.getText().equals("") ||
        !w_deleteWindow_goodId.getText().equals("") && !w_deleteWindow_BatchId.getText().equals("")) {
            showErrorWindow("One of two id's should be set");
            return;
        }
        try {

            if (w_deleteWindow_BatchId.getText().equals("")) {
                if (ware == 1) {
                    app.getW1Api().removeGoodById(app.getToken(), Long.parseLong(w_deleteWindow_goodId.getText())).subscribe(resp -> {
                        if (!resp.isSuccessful()) {
                            if (resp.code() == 403) {
                                showErrorWindow("Access denied");
                            }
                            else showErrorWindow("No good with such id");
                        }
                        else {
                            Stage stage = (Stage) w_deleteWindow_BatchId.getScene().getWindow();
                            stage.close();
                        }
                    });
                }
                if (ware == 2) {
                    app.getW2Api().removeGoodById(app.getToken(), Long.parseLong(w_deleteWindow_goodId.getText())).subscribe(resp -> {
                        if (!resp.isSuccessful()) {
                            if (resp.code() == 403) {
                                showErrorWindow("Access denied");
                            }
                            else showErrorWindow("No good with such id");
                        }
                        else {
                            Stage stage = (Stage) w_deleteWindow_BatchId.getScene().getWindow();
                            stage.close();
                        }
                    });
                }
            } else {
                if (ware == 1) {
                    app.getW1Api().removeGoodByWare(app.getToken(), Long.parseLong(w_deleteWindow_BatchId.getText())).subscribe(resp -> {
                        if (!resp.isSuccessful()) {
                            if (resp.code() == 403) {
                                showErrorWindow("Access denied");
                            }
                            else showErrorWindow("No batch with such id");
                        }
                        else {
                            Stage stage = (Stage) w_deleteWindow_BatchId.getScene().getWindow();
                            stage.close();
                        }
                    });
                }
                if (ware == 2) {
                    app.getW2Api().removeGoodByWare(app.getToken(), Long.parseLong(w_deleteWindow_BatchId.getText())).subscribe(resp -> {
                        if (!resp.isSuccessful()) {
                            if (resp.code() == 403) {
                                showErrorWindow("Access denied");
                            }
                            else showErrorWindow("No batch with such id");
                        }
                        else {
                            Stage stage = (Stage) w_deleteWindow_BatchId.getScene().getWindow();
                            stage.close();
                        }
                    });
                }
            }
        } catch (NumberFormatException ex) {
            showErrorWindow("Invalid id format");
        } catch (Exception ex) {
            showErrorWindow(ex.toString());
        }
    }
}
