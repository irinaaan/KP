package com.example.myaccountingsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddingProductController {

    @FXML
    private TextField fieldPrice;

    @FXML
    private Label labelAddingAProduct;

    @FXML
    private TextField fieldUnitOfMeasurement;

    @FXML
    private TextField fieldProductName;

    @FXML
    private TextField fieldDescription;

    @FXML
    private Text textProductID;

    @FXML
    private AnchorPane ap;

    @FXML
    private Text textPrice;

    @FXML
    private Text textUnitOfMeasurement;

    @FXML
    private Button buttonCancel;

    @FXML
    private Text textProductName;

    @FXML
    private Text textDescription;

    @FXML
    private Button buttonAdd;

    @FXML
    private Label labelError;

    private ProductsController productsController;

    private ObservableList<Product> productList;

    public void setProductsController(ProductsController productsController) {
        this.productsController = productsController;
    }

    public void setProductList(ObservableList<Product> productList) {
        this.productList = productList;
    }

    @FXML
    void initialize() {
        buttonAdd.setOnMouseEntered(event -> {
            buttonAdd.setStyle("-fx-background-color: #D87373;");
        });
        buttonAdd.setOnMouseExited(event -> {
            buttonAdd.setStyle("-fx-background-color: #F08080;");
        });

        buttonCancel.setOnMouseEntered(event -> {
            buttonCancel.setStyle("-fx-background-color: #D87373;");
        });
        buttonCancel.setOnMouseExited(event -> {
            buttonCancel.setStyle("-fx-background-color: #F08080;");
        });

        buttonCancel.setOnAction(event -> {
            // close the window
            ((Stage) ap.getScene().getWindow()).close();
        });
    }

    @FXML
    void onAddButtonClicked() {
        if (fieldProductName.getText().isEmpty() || fieldDescription.getText().isEmpty() || fieldUnitOfMeasurement.getText().isEmpty() || fieldPrice.getText().isEmpty()) {
            labelError.setText("All fields are required");
            labelError.setLayoutX(130);
            labelError.setLayoutY(405);
            labelError.setStyle("-fx-text-fill: red;");
            return;
        }

        String priceString = fieldPrice.getText();
        if (!priceString.matches("[\\d.,]+")) {
            labelError.setText("Price should be a number");
            labelError.setLayoutX(120);
            labelError.setLayoutY(405);
            labelError.setStyle("-fx-text-fill: red;");
            return;
        }

        DatabaseHandler dbHandler = new DatabaseHandler();
        String productNameStr = fieldProductName.getText();
        if (dbHandler.isProductExist(productNameStr)) {
            labelError.setText("There is already a product with this name");
            labelError.setLayoutX(70);
            labelError.setLayoutY(405);
            labelError.setStyle("-fx-text-fill: red");
            return;
        }

        newProduct();
        productsController.getTableView().refresh();

        Stage stage = (Stage) buttonAdd.getScene().getWindow(); //получаем текущее окно
        stage.close();
    }

    private void newProduct() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String productName = fieldProductName.getText();
        String description = fieldDescription.getText();
        String unitOfMeasurement = fieldUnitOfMeasurement.getText();
        double price = Double.parseDouble(fieldPrice.getText());

        Product product = new Product(productName, description, unitOfMeasurement, price);
        int productId = dbHandler.addProduct(product); // Get the new product id from the database
        product.setId(productId); // Set the obtained id for the Product object

        productList.add(product); // Add the product to the productList

        ((Stage) ap.getScene().getWindow()).close();
    }
}