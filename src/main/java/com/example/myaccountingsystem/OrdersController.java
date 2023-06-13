package com.example.myaccountingsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private TableColumn<Order, Double> columnSum;

    @FXML
    private BorderPane bd;

    @FXML
    private TableColumn<Order, Integer> columnIdOrders;

    @FXML
    private Button buttonDelete;

    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, Integer> columnQuantity;

    @FXML
    private TableColumn<Order, Integer> columnIdProducts;

    @FXML
    private TableColumn<Order, String> columnDate;

    @FXML
    private TableColumn<Order, Integer> columnIdCLient;

    @FXML
    private TableColumn<Order, String> columnStatus;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button buttonSortBySum;

    @FXML
    private Button buttonSortByDate;

    @FXML
    private Button buttonAdd;

    private DatabaseHandler dbHandler;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    public TableView<Order> getTableView() {
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
            openNewScene("addingOrder.fxml", "Adding Order");
        });

        buttonDelete.setOnAction(actionEvent -> {
            openNewScene("deletingOrder.fxml", "Deleting Order");
        });

        buttonSortBySum.setOnMouseEntered(event -> {
            buttonSortBySum.setStyle("-fx-background-color: #D87373;");
        });
        buttonSortBySum.setOnMouseExited(event -> {
            buttonSortBySum.setStyle("-fx-background-color: #F08080;");
        });

        buttonSortByDate.setOnMouseEntered(event -> {
            buttonSortByDate.setStyle("-fx-background-color: #D87373;");
        });
        buttonSortByDate.setOnMouseExited(event -> {
            buttonSortByDate.setStyle("-fx-background-color: #F08080;");
        });

        buttonSortBySum.setOnAction(actionEvent -> {
            orderList.sort((p1, p2) -> Double.compare(p1.getSum(), p2.getSum()));
            tableView.setItems(orderList);
        });

        buttonSortByDate.setOnAction(actionEvent -> {
            orderList.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
            tableView.setItems(orderList);
        });

        columnDate.setCellFactory(column -> new TableCell<Order, String>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    try {
                        // Преобразование строки даты в объект java.util.Date
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(item);
                        // Преобразование объекта java.util.Date в строку с нужным форматом
                        String formattedDate = dateFormat.format(date);
                        setText(formattedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        dbHandler = new DatabaseHandler();
        orderList = FXCollections.observableArrayList();

        // Load data from database
        try {
            ResultSet rs = dbHandler.getAllOrders();
            while (rs.next()) {
                orderList.add(new Order(
                        rs.getInt("idorders"),
                        rs.getInt("clientID"),
                        rs.getInt("productID"),
                        rs.getString("date"),
                        rs.getString("status"),
                        rs.getInt("quantity"),
                        rs.getDouble("sum")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        columnIdOrders.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        columnIdCLient.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        columnIdProducts.setCellValueFactory(new PropertyValueFactory<>("productID"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnSum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        tableView.setItems(orderList); // устанавливаем список клиентов в TableView
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

        // получаем ссылку на контроллер AddingClientController или DeletingClientController
        Object controller = loader.getController();

        if (controller instanceof AddingOrderController) {
            AddingOrderController addingOrderController = (AddingOrderController) controller;

            // устанавливаем ссылку на контроллер OrdersController
            addingOrderController.setOrdersController(this);
            addingOrderController.setOrderList(orderList);
        } else if (controller instanceof DeletingOrderController) {
            DeletingOrderController deletingOrderController = (DeletingOrderController) controller;

            // устанавливаем ссылку на контроллер OrdersController
            deletingOrderController.setOrdersController(this);
            deletingOrderController.setOrderList(orderList);
        }
        stage.show();
    }
}