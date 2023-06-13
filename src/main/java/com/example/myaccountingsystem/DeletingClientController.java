package com.example.myaccountingsystem;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeletingClientController {

    @FXML
    private Label labelDeletingAClient;

    @FXML
    private Text textFullName;

    @FXML
    private TextField fieldClientID;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonDelete;

    @FXML
    private TextField fieldFullname;

    @FXML
    private AnchorPane ap;

    @FXML
    private Label labelError;

    private ClientsController clientsController;

    private ObservableList<Client> clientList;

    public void setClientsController(ClientsController clientsController) {
        this.clientsController = clientsController;
    }

    public void setClientList(ObservableList<Client> clientList) {
        this.clientList = clientList;
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
            if (fieldClientID.getText().isEmpty() || fieldFullname.getText().isEmpty()) {
                // display an error message in red
                labelError.setText("All fields are required");
                labelError.setLayoutX(100);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // check if client id is a number
            if (!isNumeric(fieldClientID.getText())) {
                // display an error message in red
                labelError.setText("Client ID must be a number");
                labelError.setLayoutX(75);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // get the client id and name from the text fields
            int clientId = Integer.parseInt(fieldClientID.getText());
            String clientName = fieldFullname.getText();
            DatabaseHandler dbHandler = new DatabaseHandler();

            // check if the client exists in the database
            if (!dbHandler.clientIDExists(clientId)) {
                // display an error message in red
                labelError.setText("Client with ID " + clientId + " does not exist");
                labelError.setLayoutX(70);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // get the client's name from the database
            String nameFromDB = dbHandler.getClientNameById(clientId);

            // check if the name entered by the user matches the name in the database
            if (!nameFromDB.equals(clientName)) {
                // display an error message in red
                labelError.setText("The name entered is incorrect");
                labelError.setLayoutX(70);
                labelError.setLayoutY(227);
                labelError.setStyle("-fx-text-fill: red");
                return;
            }

            // delete the client from the database
            dbHandler.deleteClient(clientId, clientName);

            // remove the client from the table view
            clientList.removeIf(c -> c.getId() == clientId && c.getClientname().equals(clientName));

            // close the window
            Stage stage = (Stage) ap.getScene().getWindow();
            stage.close();
        });

        buttonCancel.setOnAction(event -> {
            // close the window
            ((Stage) ap.getScene().getWindow()).close();
        });
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // match a number with optional '-' and decimal
    }
}