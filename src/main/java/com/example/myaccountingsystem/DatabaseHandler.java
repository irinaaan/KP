package com.example.myaccountingsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost/mas?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + ", " + Const.USERS_LASTNAME + "," + Const.USERS_LOGIN + ", " + Const.USERS_PASSWORD + ")" + "VALUES(?,?,?,?)"; //sql запрос, помещаем данные в запрос

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstname());
            prSt.setString(2, user.getLastname());
            prSt.setString(3, user.getLogin());
            prSt.setString(4, user.getPassword());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(User user) { //вывод данных пользователя
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_LOGIN + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }

    public boolean userExists(String login) {
        String select = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_LOGIN + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            ResultSet resSet = prSt.executeQuery();
            return resSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int addClient(Client client) {
        String insert = "INSERT INTO clients (clientname, `telephone number`, email, country, city) VALUES (?,?,?,?,?)";
        int clientId = -1;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, client.getClientname());
            prSt.setString(2, client.getTelephoneNumber());
            prSt.setString(3, client.getEmail());
            prSt.setString(4, client.getCountry());
            prSt.setString(5, client.getCity());
            prSt.executeUpdate();

            ResultSet generatedKeys = prSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                clientId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return clientId;
    }

    public ResultSet getAllClients() {
        ResultSet resSet = null;

        String select = "SELECT * FROM clients";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void deleteClient(int id, String name) {
        String delete = "DELETE FROM clients WHERE idclients=? AND clientname=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id);
            prSt.setString(2, name);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int addProduct(Product product) {
        String insert = "INSERT INTO products (productname, description, `unit of measurement`, price) VALUES (?,?,?,?)";
        int productId = -1;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, product.getProductName());
            prSt.setString(2, product.getDescription());
            prSt.setString(3, product.getUnitOfMeasurement());
            prSt.setDouble(4, product.getPrice());
            prSt.executeUpdate();

            ResultSet generatedKeys = prSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return productId;
    }

    public ResultSet getAllProducts() {
        ResultSet resSet = null;

        String select = "SELECT * FROM products";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public void deleteProduct(int id, String name) {
        String delete = "DELETE FROM products WHERE idproducts=? AND productname=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id);
            prSt.setString(2, name);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isProductExist(String productName) {
        String select = "SELECT COUNT(*) FROM " + Const.PRODUCTS_TABLE + " WHERE " + Const.PRODUCTS_NAME + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, productName);
            ResultSet rs = prSt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean productExists(int id) {
        String select = "SELECT * FROM products WHERE idproducts=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resSet = prSt.executeQuery();
            return resSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean clientIDExists(int id) {
        String select = "SELECT * FROM " + Const.CLIENTS_TABLE + " WHERE " + Const.CLIENTS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resSet = prSt.executeQuery();
            return resSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean productIDExists(int id) {
        String select = "SELECT * FROM " + Const.PRODUCTS_TABLE + " WHERE " + Const.PRODUCTS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resSet = prSt.executeQuery();
            return resSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getClientNameById(int id) {
        String select = "SELECT " + Const.CLIENTS_NAME + " FROM " + Const.CLIENTS_TABLE + " WHERE " + Const.CLIENTS_ID + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(Const.CLIENTS_NAME);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String getProductNameById(int id) {
        String query = "SELECT " + Const.PRODUCTS_NAME + " FROM " + Const.PRODUCTS_TABLE + " WHERE " + Const.PRODUCTS_ID + "=?";
        String name = null;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setInt(1, id);
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(Const.PRODUCTS_NAME);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    public ResultSet getAllOrders() {
        ResultSet resSet = null;

        String select = "SELECT * FROM orders";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public int addOrder(Order order) {
        String insert = "INSERT INTO orders (clientID, productID, date, status, quantity, sum) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            prSt.setInt(1, order.getClientID());
            prSt.setInt(2, order.getProductID());
            prSt.setString(3, order.getDate());
            prSt.setString(4, order.getStatus());
            prSt.setInt(5, order.getQuantity());
            prSt.setDouble(6, order.getSum());
            prSt.executeUpdate();

            ResultSet rs = prSt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Возвращает id нового заказа
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return -1; // В случае ошибки возвращаем -1
    }

    public void deleteOrder(int id) {
        String delete = "DELETE FROM orders WHERE idorders=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public double getProductPriceById(int id) {
        String select = "SELECT price FROM products WHERE idproducts=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0; // Если товар с заданным идентификатором не найден, вернуть 0 или другое значение по умолчанию
    }

    public boolean orderIDExists(int id) {
        String select = "SELECT * FROM " + Const.ORDERS_TABLE + " WHERE " + Const.ORDERS_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resSet = prSt.executeQuery();
            return resSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getAvailableYears() {
        List<Integer> years = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT YEAR(date) AS year FROM orders";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int year = resultSet.getInt("year");
                years.add(year);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return years;
    }

    public List<MonthlyOrder> getOrderCountByMonth() {
        List<MonthlyOrder> monthlyOrders = new ArrayList<>();

        String query = "SELECT MONTH(date) AS month, COUNT(*) AS orderCount " +
                "FROM orders WHERE status = 'Completed' GROUP BY MONTH(date)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int orderCount = resultSet.getInt("orderCount");
                monthlyOrders.add(new MonthlyOrder(month, orderCount));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return monthlyOrders;
    }

    public List<TopSellingProducts> getTopSellingProductsByYear(int selectedYear) {
        List<TopSellingProducts> topSellingProducts = new ArrayList<>();

        String query = "SELECT p.productname, COUNT(*) AS orderCount " +
                "FROM orders o " +
                "JOIN products p ON o.productID = p.idproducts " +
                "WHERE YEAR(o.date) = ? AND o.status = 'Completed' " +
                "GROUP BY p.productname " +
                "ORDER BY orderCount DESC";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setInt(1, selectedYear);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                String productName = resultSet.getString("productname");
                int orderCount = resultSet.getInt("orderCount");
                topSellingProducts.add(new TopSellingProducts(productName, orderCount));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return topSellingProducts;
    }

    public List<MonthlyProfit> getProfitByMonth() {
        List<MonthlyProfit> monthlyProfits = new ArrayList<>();

        String query = "SELECT MONTH(date) AS month, SUM(sum) AS profit " +
                "FROM orders WHERE status = 'Completed' " +
                "GROUP BY MONTH(date)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                double profit = resultSet.getDouble("profit");
                monthlyProfits.add(new MonthlyProfit(month, profit));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return monthlyProfits;
    }

    public int addEmployee(Employee employee) {
        String insert = "INSERT INTO employees (name, job_title, date_of_birth, email) VALUES (?,?,?,?)";
        int employeeId = -1;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, employee.getName());
            prSt.setString(2, employee.getJobTitle());
            prSt.setString(3, employee.getDate());
            prSt.setString(4, employee.getEmail());
            prSt.executeUpdate();

            ResultSet generatedKeys = prSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                employeeId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return employeeId;
    }

    public ResultSet getAllEmployees() {
        ResultSet resSet = null;

        String select = "SELECT * FROM employees";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }
}