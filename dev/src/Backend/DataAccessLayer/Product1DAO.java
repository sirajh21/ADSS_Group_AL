package Backend.DataAccessLayer;

import Backend.BusinessLayer.Stock.controllers.DataBaseController;
import Backend.BusinessLayer.Stock.controllers.SupplierController;
import Backend.BusinessLayer.Stock.objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product1DAO {
    private Connection c;
    private static Product1DAO instance = null;

    public static Product1DAO getInstance() {
        if (instance == null) {
            instance = new Product1DAO();
        }
        return instance;
    }

    private Product1DAO() {
        this.c = DataBaseController.get_connection();
    }

    private void ensureConnection() throws SQLException {
        if (c == null || c.isClosed()) {
            c = DataBaseController.connect();
        }
    }

    public boolean checkProductAvailability(int id) throws SQLException {
        return selectById(id) == null;
    }

    public void insert(Product1DTO productDTO) throws SQLException {
        ensureConnection();

        String sql = "INSERT INTO Product (ProductID, Price, ProductName) VALUES (?, ?, ?)";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, productDTO.getProduct_id());
            statement.setInt(2, productDTO.getList_price());
            statement.setString(3, productDTO.getProduct_name());
            statement.executeUpdate();
        }
    }

    public Product1DTO selectById(int id) throws SQLException {
        ensureConnection();

        String sql = "SELECT * FROM Product WHERE ProductID = ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int price = resultSet.getInt("Price");
                    String name = resultSet.getString("ProductName");

                    return new Product1DTO(id, price, name);
                } else {
                    return null;
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        ensureConnection();

        String sql = "DELETE FROM Product WHERE ProductID = ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Product1> selectAll() throws SQLException {
        ensureConnection();

        String query = "SELECT * FROM Product";
        List<Product1> products = new ArrayList<>();

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                int price = resultSet.getInt("Price");
                String name = resultSet.getString("ProductName");

                Product1 product = new Product1(id, price, name);
                products.add(product);
            }
        }

        return products;
    }

    public List<Product1> loadAll() throws SQLException {
        ensureConnection();

        String query = "SELECT * FROM Product";
        List<Product1> products = new ArrayList<>();

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                int price = resultSet.getInt("Price");
                String name = resultSet.getString("ProductName");

                Product1 product = new Product1(id, price, name);
                products.add(product);

                try {
                    SupplierController.getInstance().AddProduct(price, name, id);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return products;
    }
}