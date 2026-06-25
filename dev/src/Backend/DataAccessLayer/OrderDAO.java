package Backend.DataAccessLayer;

import Backend.BusinessLayer.Stock.controllers.DataBaseController;
import Backend.BusinessLayer.Stock.objects.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection c;
    private static OrderDAO instance = null;

    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    private OrderDAO() {
        this.c = DataBaseController.get_connection();
    }

    private void ensureConnection() throws SQLException {
        if (c == null || c.isClosed()) {
            c = DataBaseController.connect();
        }
    }

    public void insert(OrderDTO orderDTO) {
        try {
            ensureConnection();

            String sql = "INSERT INTO Order1(orderID, supplierID, ProductsAndAmount, date) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = c.prepareStatement(sql)) {
                statement.setInt(1, orderDTO.getOrder_id());
                statement.setInt(2, orderDTO.getSupplier());
                statement.setString(3, orderDTO.getProductsAndAmount());
                statement.setString(4, orderDTO.getDate());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkOrderAvailability(int id) throws SQLException {
        return selectByID(id) == 0;
    }

    public int selectByID(int id) throws SQLException {
        ensureConnection();

        String sql = "SELECT orderID FROM Order1 WHERE orderID = ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("orderID");
                } else {
                    return 0;
                }
            }
        }
    }

    public String update(OrderDTO orderDTO) throws SQLException {
        ensureConnection();

        String sql = "UPDATE Order1 SET supplierID = ?, ProductsAndAmount = ?, date = ? WHERE orderID = ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, orderDTO.getSupplier());
            statement.setString(2, orderDTO.getProductsAndAmount());
            statement.setString(3, orderDTO.getDate());
            statement.setInt(4, orderDTO.getOrder_id());

            int rows = statement.executeUpdate();

            if (rows == 0) {
                return "order was not found\n";
            }

            return "order updated successfully\n";
        }
    }

    public String delete_Order(int ID) throws SQLException {
        ensureConnection();

        String deleteOrderQuery = "DELETE FROM Order1 WHERE orderID = ?";

        try (PreparedStatement statement = c.prepareStatement(deleteOrderQuery)) {
            statement.setInt(1, ID);
            statement.executeUpdate();
        }

        return "Order deleted successfully\n";
    }

    public List<Order> selectAll() throws SQLException {
        ensureConnection();

        String sql = "SELECT * FROM Order1";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("orderID");
                int supplierID = resultSet.getInt("supplierID");
                String productsAndAmount = resultSet.getString("ProductsAndAmount");
                String date = resultSet.getString("date");

                Order order = new Order(id, supplierID, productsAndAmount, date);
                orders.add(order);
            }
        }

        return orders;
    }
}