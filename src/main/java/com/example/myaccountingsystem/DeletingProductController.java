package com.example.myaccountingsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeletingProductController {

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonDelete;

    @FXML
    private TextField fieldProductName;

    @FXML
    private Text textProductName;

    @FXML
    private Label labelDeletingAProduct;

    @FXML
    private Text textProductID;

    @FXML
    private Label labelError;

    @FXML
    private TextField fieldProductID;

    @FXML
    private AnchorPane ap;

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
        buttonDelete.setOnMouseEntered(event -> {
            buttonDelete.setStyle("-fx-background-color: #D87373;");
        });
        buttonDelete.setOnMouseExited(event -> {
            buttonDelete.setStyle("-fx-background-color: #F08080;");
        });

        buttonCancel.setOnMouseEntered(event -> {
            buttonCancel.setStyle("-fx-background-color: #D87373;");
        });
        buttonCancel.setOnMouseExited(event -> {
            buttonCancel.setStyle("-fx-background-color: #F08080;");
        });

        buttonDelete.setOnAction(event -> {
            // check if all fields are filled in
            if (fieldProductID.getText().isEmpty() || fieldProductName.getText().isEmpty()) {
                // display an error message in red
                labelError.setText("All fields are required");
                labelError.setLayoutX(100);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // check if product id is a number
            if (!isNumeric(fieldProductID.getText())) {
                // display an error message in red
                labelError.setText("Product ID must be a number");
                labelError.setLayoutX(75);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // get the product id and name from the text fields
            int productId = Integer.parseInt(fieldProductID.getText());
            String productName = fieldProductName.getText();
            DatabaseHandler dbHandler = new DatabaseHandler();

            // check if the product exists in the database
            if (!dbHandler.productIDExists(productId)) {
                // display an error message in red
                labelError.setText("Product with ID " + productId + " does not exist");
                labelError.setLayoutX(70);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // get the client's name from the database
            String nameFromDB = dbHandler.getProductNameById(productId);

            // check if the name entered by the user matches the name in the database
            if (!nameFromDB.equals(productName)) {
                // display an error message in red
                labelError.setText("The name entered is incorrect");
                labelError.setLayoutX(70);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // delete the product from the database
            dbHandler.deleteProduct(productId, productName);

            // remove the product from the table view
            productList.removeIf(c -> c.getId() == productId && c.getProductName().equals(productName));

            // close the window
            Stage stage = (Stage) ap.getScene().getWindow();
            stage.close();
        });

        buttonCancel.setOnAction(event -> {
            // закрыть окно
            ((Stage) ap.getScene().getWindow()).close();
        });
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // match a number with optional '-' and decimal
    }
}