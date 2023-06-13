package com.example.myaccountingsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class AddingClientController {

    @FXML
    private Text textAddress;

    @FXML
    private TextField fieldTelephoneNumber;

    @FXML
    private Text textStatus;

    @FXML
    private TextField fieldAddress;

    @FXML
    private TextField fieldFullname;

    @FXML
    private AnchorPane ap;

    @FXML
    private Text textFullName;

    @FXML
    private ComboBox<String> comboBoxCountry;

    @FXML
    private ComboBox<String> comboBoxCity;

    @FXML
    private TextField fieldEmail;

    @FXML
    private Button buttonCancel;

    @FXML
    private Text textEmail;

    @FXML
    private Label labelAddingAClient;

    @FXML
    private Text textTelephoneNumber;

    @FXML
    private Button buttonAdd;

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

        // Получаем список всех стран мира
        Locale[] locales = Locale.getAvailableLocales();
        ObservableList<String> countries = FXCollections.observableArrayList();

        // Сортируем локали по английскому названию страны
        Arrays.sort(locales, Comparator.comparing(Locale::getDisplayCountry));

        for (Locale locale : locales) {
            String country = locale.getDisplayCountry(Locale.ENGLISH);
            if (!country.isEmpty() && !countries.contains(country)) {
                countries.add(country);
            }
        }

        // Заполняем выпадающий список стран
        comboBoxCountry.setItems(countries);

        // Добавляем обработчик события для выбора страны
        comboBoxCountry.setOnAction(event -> {
            String selectedCountry = comboBoxCountry.getValue();
            ObservableList<String> cities = CityListProvider.getCityListForCountry(selectedCountry);
            comboBoxCity.setItems(cities);
        });
    }

    @FXML
    void onAddButtonClicked() {
        if (fieldFullname.getText().isEmpty() || fieldTelephoneNumber.getText().isEmpty() || fieldEmail.getText().isEmpty() || comboBoxCountry.getValue() == null || comboBoxCity.getValue() == null) {
            labelError.setText("All fields are required");
            labelError.setStyle("-fx-text-fill: red;");
            return;
        }

        newClient();
        clientsController.getTableView().refresh();

        Stage stage = (Stage) buttonAdd.getScene().getWindow(); //получаем текущее окно
        stage.close();
    }

    private void newClient() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String clientname = fieldFullname.getText();
        String telephoneNumber = fieldTelephoneNumber.getText();
        String email = fieldEmail.getText();
        String country = comboBoxCountry.getValue();
        String city = comboBoxCity.getValue();

        Client client = new Client(clientname, telephoneNumber, email, country, city);
        int clientId = dbHandler.addClient(client); // Получение id нового клиента из базы данных
        client.setId(clientId); // Установка полученного id для объекта Client

        clientList.add(client); // Добавление клиента в список clientList

        // закрываем окно
        ((Stage) ap.getScene().getWindow()).close();
    }
}