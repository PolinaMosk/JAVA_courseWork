package gui;

import api.*;
import di.ApiComponent;
import di.DaggerApiComponent;
import gui.controllers.AuthWindowController;
import gui.controllers.MainWindowController;
import gui.controllers.WAddWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.security.InvalidKeyException;

public class App extends Application {

    private ApiComponent component = DaggerApiComponent.create();
    private AuthApi authApi;
    private GoodsApi goodsApi;
    private SalesApi salesApi;
    private Warehouse1Api W1Api;
    private Warehouse2Api W2Api;
    private String token;
    private User user;
    public Stage primaryStage;
    private MainWindowController mwController;
    private WAddWindowController w1AddController;
    private AuthWindowController authController;

    public GoodsApi getGoodsApi() {
        return goodsApi;
    }

    public void setGoodsApi(GoodsApi goodsApi) {
        this.goodsApi = goodsApi;
    }

    public SalesApi getSalesApi() {
        return salesApi;
    }

    public void setSalesApi(SalesApi salesApi) {
        this.salesApi = salesApi;
    }

    public Warehouse1Api getW1Api() {
        return W1Api;
    }

    public void setW1Api(Warehouse1Api w1Api) {
        W1Api = w1Api;
    }

    public Warehouse2Api getW2Api() {
        return W2Api;
    }

    public void setW2Api(Warehouse2Api w2Api) {
        W2Api = w2Api;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            authApi = component.provideAuthApi();
            goodsApi = component.provideGoodsApi();
            salesApi = component.provideSalesApi();
            W1Api = component.provideW1Api();
            W2Api = component.provideW2Api();

            showAuthWindow();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IllegalArgumentException ex) {
            primaryStage.close();
            showAuthWindow();
            primaryStage.setResizable(false);
            primaryStage.show();
        }

    }

    public AuthApi getAuthApi() {
        return authApi;
    }

    public void setAuthApi(AuthApi authApi) {
        this.authApi = authApi;
    }

    public void showAuthWindow() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/authorizationWindow.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("Authorization");

            authController = loader.getController();
            authController.loadWindow(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
