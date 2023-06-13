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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    @FXML
    private TableColumn<Client, Integer> columnIdClients;

    @FXML
    private BorderPane bd;

    @FXML
    private TableColumn<Client, String> columnTelephoneNumber;

    @FXML
    private TableColumn<Client, String> columnClientname;

    @FXML
    private TableColumn<Client, String> columnStatus;

    @FXML
    private TableView<Client> tableView;

    @FXML
    private TableColumn<Client, String> columnEmail;

    @FXML
    private TableColumn<Client, String> columnCountry;

    @FXML
    private TableColumn<Client, String> columnCity;

    @FXML
    private Button buttonSortByName;

    @FXML
    private Button buttonSortByAddress;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonAdd;

    @FXML
    private AnchorPane ap;

    private DatabaseHandler dbHandler;

    private ObservableList<Client> clientList = FXCollections.observableArrayList();

    public TableView<Client> getTableView() {
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
            openNewScene("addingClient.fxml", "Adding Client");
        });

        buttonDelete.setOnAction(actionEvent -> {
            openNewScene("deletingClient.fxml", "Deleting Client");
        });

        buttonSortByName.setOnMouseEntered(event -> {
            buttonSortByName.setStyle("-fx-background-color: #D87373;");
        });
        buttonSortByName.setOnMouseExited(event -> {
            buttonSortByName.setStyle("-fx-background-color: #F08080;");
        });

        buttonSortByAddress.setOnMouseEntered(event -> {
            buttonSortByAddress.setStyle("-fx-background-color: #D87373;");
        });
        buttonSortByAddress.setOnMouseExited(event -> {
            buttonSortByAddress.setStyle("-fx-background-color: #F08080;");
        });

        dbHandler = new DatabaseHandler();
        clientList = FXCollections.observableArrayList();

        // Load data from database
        try {
            ResultSet rs = dbHandler.getAllClients();
            while (rs.next()) {
                clientList.add(new Client(
                        rs.getInt("idclients"),
                        rs.getString("clientname"),
                        rs.getString("telephone number"),
                        rs.getString("email"),
                        rs.getString("country"),
                        rs.getString("city")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        columnIdClients.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnClientname.setCellValueFactory(new PropertyValueFactory<>("clientname"));
        columnTelephoneNumber.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));

        tableView.setItems(clientList); // устанавливаем список клиентов в TableView
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

        if (controller instanceof AddingClientController) {
            AddingClientController addingClientController = (AddingClientController) controller;

            // устанавливаем ссылку на контроллер ClientsController
            addingClientController.setClientsController(this);
            addingClientController.setClientList(clientList);
        } else if (controller instanceof DeletingClientController) {
            DeletingClientController deletingClientController = (DeletingClientController) controller;

            // устанавливаем ссылку на контроллер ClientsController
            deletingClientController.setClientsController(this);
            deletingClientController.setClientList(clientList);
        }
        stage.show();
    }
}