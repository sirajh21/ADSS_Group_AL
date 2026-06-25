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

public class SuppliersDAO {
    private Connection c;
    private static SuppliersDAO instance = null;

    public static SuppliersDAO getInstance() {
        if (instance == null) {
            instance = new SuppliersDAO();
        }
        return instance;
    }

    private SuppliersDAO() {
        this.c = DataBaseController.get_connection();
    }

    private void ensureConnection() throws SQLException {
        if (c == null || c.isClosed()) {
            c = DataBaseController.connect();
        }
    }

    public boolean checkSupplierAvailability(String name) throws SQLException {
        return selectByName(name) == 0;
    }

    public int selectByName(String name) throws SQLException {
        ensureConnection();

        String sql = "SELECT * FROM Supplier WHERE Name = ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ID");
                } else {
                    return 0;
                }
            }
        }
    }

    public String insert(SuppliersDTO suppliersDTO) throws SQLException {
        ensureConnection();

        String sql = "INSERT INTO Supplier(ID, Name, PhoneNumber, SupplierType, BankAccount, PaymentType) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, suppliersDTO.getSupplier_id());
            statement.setString(2, suppliersDTO.getName());
            statement.setString(3, suppliersDTO.getPhoneNumber());
            statement.setInt(4, suppliersDTO.getSupplierTypeInt());
            statement.setString(5, suppliersDTO.getBankAccount());
            statement.setString(6, suppliersDTO.getPaymentType());
            statement.executeUpdate();
        }

        return "Supplier added successfully\n";
    }

    public String update(SuppliersDTO suppliersDTO) throws SQLException {
        ensureConnection();

        String sql = "UPDATE Supplier " +
                "SET Name = ?, PhoneNumber = ?, SupplierType = ?, BankAccount = ?, PaymentType = ? " +
                "WHERE ID = ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, suppliersDTO.getName());
            statement.setString(2, suppliersDTO.getPhoneNumber());
            statement.setInt(3, suppliersDTO.getSupplierTypeInt());
            statement.setString(4, suppliersDTO.getBankAccount());
            statement.setString(5, suppliersDTO.getPaymentType());
            statement.setInt(6, suppliersDTO.getSupplier_id());

            statement.executeUpdate();
        }

        return "Supplier updated successfully\n";
    }

    public String delete_Supplier(int ID) throws SQLException {
        ensureConnection();

        String deleteContactQuery = "DELETE FROM Supplier_Contact WHERE SupplierID = ?";
        try (PreparedStatement statement = c.prepareStatement(deleteContactQuery)) {
            statement.setInt(1, ID);
            statement.executeUpdate();
        }

        String deleteSupplierQuery = "DELETE FROM Supplier WHERE ID = ?";
        try (PreparedStatement statement = c.prepareStatement(deleteSupplierQuery)) {
            statement.setInt(1, ID);
            statement.executeUpdate();
        }

        return "Supplier deleted successfully\n";
    }

    public List<Supplier> selectAll() throws SQLException {
        ensureConnection();

        String sql = "SELECT * FROM Supplier";
        List<Supplier> suppliers = new ArrayList<>();

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int supplierType = resultSet.getInt("SupplierType");
                String bankAccount = resultSet.getString("BankAccount");
                String paymentType = resultSet.getString("PaymentType");

                Supplier supplier = new Supplier(
                        id,
                        name,
                        phoneNumber,
                        supplierType,
                        bankAccount,
                        paymentType,
                        new ArrayList<>()
                );

                suppliers.add(supplier);
            }
        }

        return suppliers;
    }

    public List<Supplier> loadAll() throws SQLException {
        ensureConnection();

        String sql = "SELECT * FROM Supplier";
        List<Supplier> suppliers = new ArrayList<>();

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int supplierType = resultSet.getInt("SupplierType");
                String bankAccount = resultSet.getString("BankAccount");
                String paymentType = resultSet.getString("PaymentType");

                Supplier supplier = new Supplier(
                        id,
                        name,
                        phoneNumber,
                        supplierType,
                        bankAccount,
                        paymentType,
                        new ArrayList<>()
                );

                SupplierController.getInstance().AddSupplier(
                        id,
                        name,
                        phoneNumber,
                        supplierType,
                        bankAccount,
                        paymentType,
                        new ArrayList<>()
                );

                suppliers.add(supplier);
            }
        }

        return suppliers;
    }

    public String insert_supplier_contact(int supplier_id, int contact_id) throws SQLException {
        ensureConnection();

        String query =
                "INSERT INTO Supplier_Contact (SupplierID, ContactID) " +
                        "SELECT ?, ? " +
                        "WHERE EXISTS (SELECT 1 FROM Supplier WHERE ID = ?) " +
                        "AND EXISTS (SELECT 1 FROM Contacts WHERE ID = ?) " +
                        "AND NOT EXISTS (" +
                        "   SELECT 1 FROM Supplier_Contact " +
                        "   WHERE SupplierID = ? AND ContactID = ?" +
                        ")";

        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, supplier_id);
            statement.setInt(2, contact_id);
            statement.setInt(3, supplier_id);
            statement.setInt(4, contact_id);
            statement.setInt(5, supplier_id);
            statement.setInt(6, contact_id);
            statement.executeUpdate();
        }

        return "Supplier Contact added successfully\n";
    }

    public String delete_supplier_contact(int supp_id, int contact_id) throws SQLException {
        ensureConnection();

        String query = "DELETE FROM Supplier_Contact WHERE SupplierID = ? AND ContactID = ?";

        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, supp_id);
            statement.setInt(2, contact_id);
            statement.executeUpdate();
        }

        return "Supplier Contact deleted successfully\n";
    }
}