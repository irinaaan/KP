package com.example.myaccountingsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button buttonClients;

    @FXML
    private Button buttonOrders;

    @FXML
    private Button buttonStatistics;

    @FXML
    private Button buttonProducts;

    @FXML
    private VBox sideMenu;

    @FXML
    private Button buttonEmployee;

    @FXML
    private Button buttonExit;

    @FXML
    void sales(MouseEvent event) {
        loadPage("orders.fxml");
    }

    @FXML
    void products(MouseEvent event) {
        loadPage("products.fxml");
    }

    @FXML
    void clients(MouseEvent event) {
        loadPage("clients.fxml");
    }

    @FXML
    void reports(MouseEvent event) {
        loadPage("statistics.fxml");
    }

    @FXML
    void other(MouseEvent event) {
        loadPage("employee.fxml");
    }

    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page));
            borderPane.setCenter(root);
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Image icon = new Image(getClass().getResourceAsStream("icon.png"));
            stage.getIcons().add(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        buttonOrders.setOnMouseEntered(event -> {
            buttonOrders.setStyle("-fx-background-color: #D87373;");
        });
        buttonOrders.setOnMouseExited(event -> {
            buttonOrders.setStyle("-fx-background-color: #F08080;");
        });

        buttonProducts.setOnMouseEntered(event -> {
            buttonProducts.setStyle("-fx-background-color: #D87373;");
        });
        buttonProducts.setOnMouseExited(event -> {
            buttonProducts.setStyle("-fx-background-color: #F08080;");
        });

        buttonClients.setOnMouseEntered(event -> {
            buttonClients.setStyle("-fx-background-color: #D87373;");
        });
        buttonClients.setOnMouseExited(event -> {
            buttonClients.setStyle("-fx-background-color: #F08080;");
        });

        buttonStatistics.setOnMouseEntered(event -> {
            buttonStatistics.setStyle("-fx-background-color: #D87373;");
        });
        buttonStatistics.setOnMouseExited(event -> {
            buttonStatistics.setStyle("-fx-background-color: #F08080;");
        });

        buttonEmployee.setOnMouseEntered(event -> {
            buttonEmployee.setStyle("-fx-background-color: #D87373;");
        });
        buttonEmployee.setOnMouseExited(event -> {
            buttonEmployee.setStyle("-fx-background-color: #F08080;");
        });

        buttonExit.setOnMouseEntered(event -> {
            buttonExit.setStyle("-fx-background-color: #D87373;");
        });
        buttonExit.setOnMouseExited(event -> {
            buttonExit.setStyle("-fx-background-color: #F08080;");
        });

        buttonExit.setOnAction(event -> {
            Platform.exit();
        });
    }
}