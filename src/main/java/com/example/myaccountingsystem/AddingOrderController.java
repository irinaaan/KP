package com.example.myaccountingsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

public class AddingOrderController {

    @FXML
    private TextField fieldQuantity;

    @FXML
    private TextField fieldClientID;

    @FXML
    private Text textSum;

    @FXML
    private TextField fieldSaleID;

    @FXML
    private Label labelAddingAOrder;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text textProductID;

    @FXML
    private TextField fieldProductID;

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField fieldSum;

    @FXML
    private Text textQuantity;

    @FXML
    private Button buttonCancel;

    @FXML
    private Text textClientID;

    @FXML
    private Text textSaleID;

    @FXML
    private Text textStatus;

    @FXML
    private TextField fieldStatus;

    @FXML
    private Text textDate;

    @FXML
    private Button buttonAdd;

    @FXML
    private Label labelError;

    @FXML
    private ComboBox<String> comboBoxStatus;

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
        // Инициализация выпадающего списка статуса заказа
        comboBoxStatus.getItems().addAll("Pending", "Processing", "Completed", "Cancelled");

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
            // закрыть окно
            ((Stage) ap.getScene().getWindow()).close();
        });

        fieldQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && isNumeric(newValue)) {
                calculateOrderSum();
            } else {
                textSum.setText("");
            }
        });
    }

    private void calculateOrderSum() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        if (!fieldProductID.getText().isEmpty()) {
            int quantity = Integer.parseInt(fieldQuantity.getText());
            double price = dbHandler.getProductPriceById(Integer.parseInt(fieldProductID.getText()));
            double sum = price * quantity;
            textSum.setText("Order Sum: " + sum);
        }
    }

    @FXML
    void onAddButtonClicked() {
        if (fieldClientID.getText().isEmpty() || fieldProductID.getText().isEmpty() || datePicker.getValue() == null || comboBoxStatus.getValue() == null || fieldQuantity.getText().isEmpty()) {
            labelError.setText("All fields are required");
            labelError.setLayoutX(130);
            labelError.setLayoutY(415);
            labelError.setStyle("-fx-text-fill: red;");
            return;
        }

        // check if client id is a number
        if (!isNumeric(fieldClientID.getText())) {
            // display an error message in red
            labelError.setText("Client ID must be a number");
            labelError.setLayoutX(110);
            labelError.setLayoutY(420);
            labelError.setStyle("-fx-text-fill: red");
            return;
        }

        DatabaseHandler dbHandler = new DatabaseHandler();

        if (!dbHandler.clientIDExists(Integer.parseInt(fieldClientID.getText()))) {
            // display an error message in red
            labelError.setText("Client ID does not exist");
            labelError.setLayoutX(120);
            labelError.setLayoutY(420);
            labelError.setStyle("-fx-text-fill: red");
            return;
        }

        if (!dbHandler.productExists(Integer.parseInt(fieldProductID.getText()))) {
            // display an error message in red
            labelError.setText("Product ID does not exist");
            labelError.setLayoutX(120);
            labelError.setLayoutY(420);
            labelError.setStyle("-fx-text-fill: red");
            return;
        }

        // check if product id is a number
        if (!isNumeric(fieldProductID.getText())) {
            // display an error message in red
            labelError.setText("Product ID must be a number");
            labelError.setLayoutX(110);
            labelError.setLayoutY(420);
            labelError.setStyle("-fx-text-fill: red");
            return;
        }

        // check if quantity is a number
        if (!isNumeric(fieldQuantity.getText())) {
            // display an error message in red
            labelError.setText("Quantity must be a number");
            labelError.setLayoutX(110);
            labelError.setLayoutY(420);
            labelError.setStyle("-fx-text-fill: red");
            return;
        }

        newOrder();
        ordersController.getTableView().refresh();
        Stage stage = (Stage) buttonAdd.getScene().getWindow();
        stage.close();
    }

    private void newOrder() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        int clientID = Integer.parseInt(fieldClientID.getText());
        int productID = Integer.parseInt(fieldProductID.getText());
        String date = datePicker.getValue().toString();
        String status = comboBoxStatus.getValue();
        int quantity = Integer.parseInt(fieldQuantity.getText());
        double price = dbHandler.getProductPriceById(productID);
        double sum = price * quantity;

        Order order = new Order(clientID, productID, date, status, quantity, sum);
        int orderId = dbHandler.addOrder(order); // Получение id нового заказа из базы данных
        order.setOrderID(orderId); // Установка полученного id для объекта Order

        orderList.add(order); // Добавление заказа в список orderList

        ((Stage) ap.getScene().getWindow()).close();
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // match a number with optional '-' and decimal
    }
}