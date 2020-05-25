package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Goods;
import model.Sales;
import model.Warehouse1;
import model.Warehouse2;


public class MainWindowController {
    private App app;
    private WAddWindowController wAddController;
    private WDeleteWindowController wDeleteController;
    private SalesAddController salesController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane W1Tab;

    @FXML
    private AnchorPane W2Tab;

    @FXML
    private AnchorPane goodsTab;

    @FXML
    private TableView<Goods> goods_table;

    @FXML
    private TableColumn<?, ?> goods_id_column;

    @FXML
    private TableColumn<?, ?> goods_name_column;

    @FXML
    private TableColumn<?, ?> goods_priority_column;

    @FXML
    private Button goods_refresh_btn;

    @FXML
    private TextField goods_search_field;

    @FXML
    private AnchorPane salesTab;

    @FXML
    private Button sales_add_btn;

    @FXML
    private TableView<Sales> sales_table;

    @FXML
    private TableColumn<?, ?> sales_date_column;

    @FXML
    private TableView<Goods> sales_goods_table;

    @FXML
    private TableColumn<?,?> sales_goodName;

    @FXML
    private TableColumn<?,?> sales_goodId;

    @FXML
    private TableColumn<?,?> sales_goodPr;

    @FXML
    private TableColumn<?, ?> sales_id_column;

    @FXML
    private TableColumn<?, ?> sales_number_column;

    @FXML
    private Button sales_refresh_btn;

    @FXML
    private TextField sales_search_field;

    @FXML
    private Button w1_add_btn;

    @FXML
    private Button w1_delete_btn;

    @FXML
    private TableView<Warehouse1> w1_table;

    @FXML
    private TableColumn<?, ?> w1_goodId_column;

    @FXML
    private TableView<Goods> w1_goods_table;

    @FXML
    private TableColumn<?, ?> w1_goodName_column;

    @FXML
    private TableColumn<?, ?> w1_goodPriority_column;

    @FXML
    private TableColumn<?, ?> w1_id_column;

    @FXML
    private TableColumn<?, ?> w1_number_column;

    @FXML
    private Button w1_refresh_btn;

    @FXML
    private TextField w1_search_field;

    @FXML
    private Button w2_add_btn;

    @FXML
    private Button w2_delete_btn;

    @FXML
    private TableView<Warehouse2> w2_table;

    @FXML
    private TableColumn<?, ?> w2_goodId_column;

    @FXML
    private TableColumn<?, ?> w2_goodName_column;
    @FXML
    private TableView<Goods> w2_goods_table;

    @FXML
    private TableColumn<?, ?> w2_goodPriority_column;

    @FXML
    private TableColumn<?, ?> w2_id_column;

    @FXML
    private TableColumn<?, ?> w2_number_column;

    @FXML
    private Button w2_refresh_btn;

    @FXML
    private TextField w2_search_field;


    @FXML
    void initialize() {
    }

    public void loadWindow(App app){
        this.app = app;

        loadGoodsTable();
        loadWare1Table();
        loadWare2Table();
        loadSalesTable();
        goods_search_field.textProperty().addListener(search ->{
            if (!goods_search_field.getText().equals("")) {
                app.getGoodsApi().getGoodById(app.getToken(), Integer.parseInt(goods_search_field.getText())).subscribe(resp -> {
                    goods_table.getItems().clear();
                    goods_table.getItems().addAll(resp.body());
                });
            } else {
                goods_table.getItems().clear();
                loadGoodsTable();
            }
        });
        w1_search_field.textProperty().addListener(search ->{
            if (!w1_search_field.getText().equals("")) {
                app.getW1Api().getGoodInWare(app.getToken(), Integer.parseInt(w1_search_field.getText())).subscribe(resp -> {
                    app.getW1Api().getGoodByWareId(app.getToken(), Integer.parseInt(w1_search_field.getText())).subscribe(res ->{
                        w1_table.getItems().clear();
                        w1_goods_table.getItems().clear();
                        w1_table.getItems().addAll(resp.body());
                        w1_goods_table.getItems().addAll(res.body());
                    });
                });
            } else {
                w1_table.getItems().clear();
                w1_goods_table.getItems().clear();
                loadWare1Table();
            }
        });
        w2_search_field.textProperty().addListener(search ->{
            if (!w2_search_field.getText().equals("")) {
                app.getW2Api().getGoodInWare(app.getToken(), Integer.parseInt(w2_search_field.getText())).subscribe(resp -> {
                    app.getW2Api().getGoodByWareId(app.getToken(), Integer.parseInt(w2_search_field.getText())).subscribe(res ->{
                        w2_table.getItems().clear();
                        w2_goods_table.getItems().clear();
                        w2_table.getItems().addAll(resp.body());
                        w2_goods_table.getItems().addAll(res.body());
                    });
                });
            } else {
                w2_table.getItems().clear();
                w2_goods_table.getItems().clear();
                loadWare2Table();
            }
        });
       sales_search_field.textProperty().addListener(search ->{
           if (!sales_search_field.getText().equals("")) {
               app.getSalesApi().getGroupById(app.getToken(), Integer.parseInt(sales_search_field.getText())).subscribe(resp -> {
                   app.getSalesApi().getGoodsBySaleId(app.getToken(), Integer.parseInt(sales_search_field.getText())).subscribe(res -> {
                       sales_table.getItems().clear();
                       sales_goods_table.getItems().clear();
                       sales_table.getItems().addAll(resp.body());
                       sales_goods_table.getItems().addAll(res.body());
                   });
               });
           } else {
               sales_table.getItems().clear();
               sales_goods_table.getItems().clear();
               loadSalesTable();
           }
        });
    }

    public void refresh() {
        goods_table.getItems().clear();
        sales_table.getItems().clear();
        w1_table.getItems().clear();
        w1_goods_table.getItems().clear();
        w2_table.getItems().clear();
        w2_goods_table.getItems().clear();

        loadGoodsTable();
        loadWare1Table();
        loadWare2Table();
        loadSalesTable();
    }

    public void loadGoodsTable() {
        goods_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        goods_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        goods_priority_column.setCellValueFactory(new PropertyValueFactory<>("priority"));
        app.getGoodsApi().getGoods(app.getToken()).subscribe(resp -> {
            if (resp.isSuccessful() && resp.body() != null) {
                goods_table.getItems().addAll(resp.body());
            } else {

            }
        });
    }

    public void loadSalesTable() {
        sales_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        sales_date_column.setCellValueFactory(new PropertyValueFactory<>("create_date"));
        sales_number_column.setCellValueFactory(new PropertyValueFactory<>("good_count"));
        sales_goodId.setCellValueFactory(new PropertyValueFactory<>("id"));
        sales_goodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        sales_goodPr.setCellValueFactory(new PropertyValueFactory<>("priority"));
        app.getSalesApi().getSales(app.getToken()).subscribe(resp -> {
            if (resp.isSuccessful() && resp.body() != null) {
                app.getSalesApi().listGoodsInSales(app.getToken()).subscribe(res -> {
                    if (res.isSuccessful() && res.body() != null) {
                        sales_table.getItems().addAll(resp.body());
                        sales_goods_table.getItems().addAll(res.body());
                    }
                });
            } else {

            }
        });
    }

    public void loadWare1Table() {
        w1_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        w1_number_column.setCellValueFactory(new PropertyValueFactory<>("good_count"));
        app.getW1Api().listWare(app.getToken()).subscribe(resp -> {
            if (resp.isSuccessful() && resp.body() != null) {
                w1_table.getItems().addAll(resp.body());
            } else {

            }
        });
        w1_goodId_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        w1_goodName_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        w1_goodPriority_column.setCellValueFactory(new PropertyValueFactory<>("priority"));
        app.getW1Api().listGoods(app.getToken()).subscribe(resp -> {
            if (resp.isSuccessful() && resp.body() != null) {
                w1_goods_table.getItems().addAll(resp.body());
            } else {

            }
        });

    }

    public void loadWare2Table() {
        w2_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        w2_number_column.setCellValueFactory(new PropertyValueFactory<>("good_count"));
        app.getW2Api().listWare(app.getToken()).subscribe(resp -> {
            if (resp.isSuccessful() && resp.body() != null) {
                w2_table.getItems().addAll(resp.body());
            } else {

            }
        });
        w2_goodId_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        w2_goodName_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        w2_goodPriority_column.setCellValueFactory(new PropertyValueFactory<>("priority"));
        app.getW2Api().listGoods(app.getToken()).subscribe(resp -> {
            if (resp.isSuccessful() && resp.body() != null) {
                w2_goods_table.getItems().addAll(resp.body());
            } else {

            }
        });
    }

    public void addWindowW1(){
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/addWareWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add");
            stage.show();
            wAddController = loader.getController();
            wAddController.loadWindow(app, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWindowW2(){
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/addWareWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add");
            stage.show();
            wAddController = loader.getController();
            wAddController.loadWindow(app, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteWindowW1(){
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/deleteWareWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Delete");
            stage.show();
            wDeleteController = loader.getController();
            wDeleteController.loadWindow(app, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteWindowW2(){
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/deleteWareWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Delete");
            stage.show();
            wDeleteController = loader.getController();
            wDeleteController.loadWindow(app, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSale(){
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/addSaleWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add sale");
            stage.show();
            salesController = loader.getController();
            salesController.loadWindow(app);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshGoods(){
        goods_table.getItems().clear();
        loadGoodsTable();
    }

    public void refreshWare1(){
        w1_table.getItems().clear();
        w1_goods_table.getItems().clear();
        loadWare1Table();
    }
    public void refreshWare2(){
        w2_table.getItems().clear();
        w2_goods_table.getItems().clear();
        loadWare2Table();
    }
    public void refreshSales(){
        sales_table.getItems().clear();
        sales_goods_table.getItems().clear();
        loadSalesTable();
    }

}
