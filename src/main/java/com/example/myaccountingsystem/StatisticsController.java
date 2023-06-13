package com.example.myaccountingsystem;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import java.util.Comparator;
import java.util.List;

public class StatisticsController {

    @FXML
    private CategoryAxis categoryAxisOrder;

    @FXML
    private NumberAxis numberAxisOrder;

    @FXML
    private BarChart<String, Number> barChartProfit;

    @FXML
    private BarChart<String, Number> barChartTopSelling;

    @FXML
    private ScrollPane sp;

    @FXML
    private CategoryAxis categoryAxisTopSelling;

    @FXML
    private NumberAxis numberAxisTopSelling;

    @FXML
    private BarChart<String, Number> barChartOrder;

    @FXML
    private ChoiceBox<Integer> choiceBox;

    @FXML
    private AnchorPane ap;

    @FXML
    private CategoryAxis categoryAxisProfit;

    @FXML
    private NumberAxis numberAxisProfit;

    private int selectedYear;

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    @FXML
    public void initialize() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Получаем доступные годы из базы данных
        List<Integer> availableYears = databaseHandler.getAvailableYears();

        // Установить выбранный год, например, первый доступный год
        setSelectedYear(availableYears.get(0));

        // Добавляем доступные годы в ChoiceBox
        choiceBox.getItems().addAll(availableYears);

        // Добавляем слушатель событий для ChoiceBox, который будет вызывать метод updateCharts при изменении выбранного года
        choiceBox.setOnAction(event -> updateCharts());
    }

    @FXML
    private void updateCharts() {
        // Получаем выбранный год из ChoiceBox
        selectedYear = choiceBox.getValue();

        // Очищаем данные в графиках
        barChartOrder.getData().clear();
        barChartProfit.getData().clear();
        barChartTopSelling.getData().clear();

        // Обновляем графики с выбранным годом
        initializeProfitByMonthChart();
        initializeOrderByMonthChart();
        initializeTopSellingProductsByYear(selectedYear);
    }

    private void initializeOrderByMonthChart() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Получаем данные о количестве заказов по месяцам
        List<MonthlyOrder> monthlyOrders = databaseHandler.getOrderCountByMonth();

        // Создаем серию данных для графика
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        monthlyOrders.sort(Comparator.comparing(MonthlyOrder::getMonth));

        for (MonthlyOrder monthlyOrder : monthlyOrders) {
            series.getData().add(new XYChart.Data<>(monthlyOrder.getMonth(), monthlyOrder.getOrderCount()));
        }

        // Настраиваем оси и добавляем серию данных в график
        categoryAxisOrder.setLabel("Month");
        numberAxisOrder.setLabel("Order Count");
        barChartOrder.getData().add(series);
    }

    private void initializeTopSellingProductsByYear(int selectedYear) {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Получаем данные о самых продаваемых продуктах за указанный год
        List<TopSellingProducts> topSellingProducts = databaseHandler.getTopSellingProductsByYear(selectedYear);

        // Создаем серию данных для графика
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        topSellingProducts.sort(Comparator.comparingInt(TopSellingProducts::getOrderCount).reversed());

        for (TopSellingProducts product : topSellingProducts) {
            series.getData().add(new XYChart.Data<>(product.getProductName(), product.getOrderCount()));
        }

        // Настраиваем оси и добавляем серию данных в график
        categoryAxisTopSelling.setLabel("Product");
        numberAxisTopSelling.setLabel("Order Count");
        barChartTopSelling.getData().add(series);
    }

    private void initializeProfitByMonthChart() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Получаем данные о прибыли по месяцам
        List<MonthlyProfit> monthlyProfits = databaseHandler.getProfitByMonth();

        // Создаем серию данных для графика
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        monthlyProfits.sort(Comparator.comparing(MonthlyProfit::getMonth));

        for (MonthlyProfit monthlyProfit : monthlyProfits) {
            series.getData().add(new XYChart.Data<>(monthlyProfit.getMonth(), monthlyProfit.getProfit()));
        }

        // Настраиваем оси и добавляем серию данных в график
        categoryAxisProfit.setLabel("Month");
        numberAxisProfit.setLabel("Profit");
        barChartProfit.getData().add(series);
    }
}