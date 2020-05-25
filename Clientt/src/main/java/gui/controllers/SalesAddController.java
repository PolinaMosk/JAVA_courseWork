package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.sql.Time;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Goods;
import model.Sales;


public class SalesAddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField sales_addWindow_name;

    @FXML
    private TextField sales_addWindow_number;

    @FXML
    private Button sales_addWindow_ready;
    private App app;
    private ErrorWindowController EWController;

    @FXML
    void initialize() {
    }

    public void loadWindow(App app){
        this.app = app;
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

    public void executeAdding(){
        if (sales_addWindow_name.getText().equals("") || sales_addWindow_number.getText().equals("")) {
            showErrorWindow("Not all parameters are set");
            return;
        }
        if (Integer.parseInt(sales_addWindow_number.getText()) <= 0) {
            showErrorWindow("Number must be positive");
            return;
        }
        try {
            Sales sale = new Sales();
            Goods good = new Goods();
            good.setName(sales_addWindow_name.getText());

            sale.setCreate_date(" ");
            sale.setGood_count(Integer.parseInt(sales_addWindow_number.getText()));
            sale.setGood(good);
             app.getSalesApi().addSale(app.getToken(), sale).subscribe(resp -> {
                 if (!resp.isSuccessful()) {
                     showErrorWindow("No good with such name or not enough goods in warehouses");
                 } else {
                     Stage stage = (Stage) sales_addWindow_ready.getScene().getWindow();
                     stage.close();
                 }
             });
        } catch (Exception ex) {
            showErrorWindow(ex.getMessage());
            return;
        }
    }
}
