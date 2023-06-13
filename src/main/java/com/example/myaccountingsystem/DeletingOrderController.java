package com.example.myaccountingsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeletingOrderController {

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonDelete;

    @FXML
    private TextField fieldOrderID;

    @FXML
    private Text textOrderID;

    @FXML
    private AnchorPane ap;

    @FXML
    private Label labelDeletingASale;

    @FXML
    private Label labelError;

    private OrdersController ordersController;

    private ObservableList<Order> orderList;

    public void setOrdersController(OrdersController ordersController) {
        this.ordersController = ordersController;
    }

    public void setOrderList(ObservableList<Order> orderList) {
        this.orderList = orderList;
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
            if (fieldOrderID.getText().isEmpty()) {
                // display an error message in red
                labelError.setText("All fields are required");
                labelError.setLayoutX(90);
                labelError.setLayoutY(147);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // check if order id is a number
            if (!isNumeric(fieldOrderID.getText())) {
                // display an error message in red
                labelError.setText("Order ID must be a number");
                labelError.setLayoutX(78);
                labelError.setLayoutY(147);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // get the order id from the text fields
            int orderId = Integer.parseInt(fieldOrderID.getText());
            DatabaseHandler dbHandler = new DatabaseHandler();

            // check if the product exists in the database
            if (!dbHandler.orderIDExists(orderId)) {
                // display an error message in red
                labelError.setText("Order with ID " + orderId + " does not exist");
                labelError.setLayoutX(70);
                labelError.setLayoutY(147);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // delete the order from the database
            dbHandler.deleteOrder(orderId);

            // remove the order from the table view
           orderList.removeIf(c -> c.getOrderID() == orderId);

            // close the window
            Stage stage = (Stage) ap.getScene().getWindow();
            stage.close();
        });

        buttonCancel.setOnAction(event -> {
            ((Stage) ap.getScene().getWindow()).close();
        });
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // match a number with optional '-' and decimal
    }
}