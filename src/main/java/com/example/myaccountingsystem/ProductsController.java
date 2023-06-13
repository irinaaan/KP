package com.example.myaccountingsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    private TableColumn<Client, String> columnUnitOfMeasurement;

    @FXML
    private Button buttonDelete;

    @FXML
    private TableColumn<Product, Double> columnPrice;

    @FXML
    private TableColumn<Product, String> columnDescription;

    @FXML
    private TableColumn<Product, Integer> columnIdProducts;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button buttonSortByName;

    @FXML
    private Button buttonSortByPrice;

    @FXML
    private TableColumn<Product, String> columnProductName;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private Button buttonAdd;

    private DatabaseHandler dbHandler;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public TableView<Product> getTableView() {
        return tableView;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonAdd.setOnMouseEntered(event -> {
            buttonAdd.setStyle("-fx-background-color: #D87373;");
        });
        buttonAdd.setOnMouseExited(event -> {
            buttonAdd.setStyle("-fx-background-color: #F08080;");
        });

        buttonDelete.setOnMouseEntered(event -> {
            buttonDelete.setStyle("-fx-background-color: #D87373;");
        });
        buttonDelete.setOnMouseExited(event -> {
            buttonDelete.setStyle("-fx-background-color: #F08080;");
        });

        buttonAdd.setOnAction(actionEvent -> {
            openNewScene("addingProduct.fxml", "Adding Product");
        });

        buttonDelete.setOnAction(actionEvent -> {
            openNewScene("deletingProduct.fxml", "Deleting Product");
        });

        buttonSortByName.setOnMouseEntered(event -> {
            buttonSortByName.setStyle("-fx-background-color: #D87373;");
        });
        buttonSortByName.setOnMouseExited(event -> {
            buttonSortByName.setStyle("-fx-background-color: #F08080;");
        });

        buttonSortByPrice.setOnMouseEntered(event -> {
            buttonSortByPrice.setStyle("-fx-background-color: #D87373;");
        });
        buttonSortByPrice.setOnMouseExited(event -> {
            buttonSortByPrice.setStyle("-fx-background-color: #F08080;");
        });

        buttonSortByName.setOnAction(event -> {
            productList.sort((p1, p2) -> p1.getProductName().compareToIgnoreCase(p2.getProductName()));
            tableView.setItems(productList);
        });

        buttonSortByPrice.setOnAction(event -> {
            productList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
            tableView.setItems(productList);
        });

        dbHandler = new DatabaseHandler();
        productList = FXCollections.observableArrayList();

        // Load data from database
        try {
            ResultSet rs = dbHandler.getAllProducts();
            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("idproducts"),
                        rs.getString("productname"),
                        rs.getString("description"),
                        rs.getString("unit of measurement"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        columnIdProducts.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnUnitOfMeasurement.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasurement"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.setItems(productList); // устанавливаем список продуктов в TableView
    }

    public void openNewScene(String window, String title) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);
        stage.setScene(new Scene(root));
        stage.setTitle(title); // установить заголовок окна

        // получаем ссылку на контроллер AddingProductController или DeletingProductController
        Object controller = loader.getController();

        if (controller instanceof AddingProductController) {
            AddingProductController addingProductController = (AddingProductController) controller;

            // устанавливаем ссылку на контроллер ProductsController
            addingProductController.setProductsController(this);
            addingProductController.setProductList(productList);
        } else if (controller instanceof DeletingProductController) {
            DeletingProductController deletingProductController = (DeletingProductController) controller;

            // устанавливаем ссылку на контроллер ClientsController
            deletingProductController.setProductsController(this);
            deletingProductController.setProductList(productList);
        }
        stage.show();
    }
}