package Backend.DataAccessLayer;

import Backend.BusinessLayer.Stock.controllers.ContractController;
import Backend.BusinessLayer.Stock.controllers.DataBaseController;
import Backend.BusinessLayer.Stock.controllers.SupplierController;
import Backend.BusinessLayer.Stock.objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContractDAO {
    private Connection c;


    public static ContractDAO getInstance() {
        if(instance == null){
            instance = new ContractDAO();
        }
        return instance;
    }

    private static ContractDAO instance = new ContractDAO();

    private ContractDAO() {
        this.c = DataBaseController.get_connection();
    }


    public boolean checkContractAvailability(String name) throws SQLException {
        return selectByName(name) == null;
    }

    public void insert(ContractDTO contractDTO) throws SQLException {
       // if(!checkContractAvailability(contractDTO.getContract_name()))
         //   return;
        String sql = "INSERT INTO Contract (SupplierID, ContractName, Scheduled, ProductsNumbers, ProductQuantityDiscount, ProductQuantityDiscount1) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setInt(1, contractDTO.getSupplier_id());
        statement.setString(2, contractDTO.getContract_name());
        statement.setBoolean(3, contractDTO.isScheduled());
        statement.setString(4, contractDTO.getProductsNumber().toString());
        statement.setString(5, contractDTO.getProductQuantityDiscount().toString());
        statement.setString(6, contractDTO.getProductQuantityDiscount2().toString());

        statement.executeUpdate();
    }

    public ContractDTO selectByName(String name) throws SQLException {
        String sql = "SELECT * FROM Contract WHERE Contract Name = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setString(2, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int supplierID = resultSet.getInt("SupplierID");
            String contractName = resultSet.getString("ContractName");
            Boolean sheduled = resultSet.getBoolean("Scheduled");
            String products_numbers = resultSet.getString("ProductsNumber");
            String products_discount = resultSet.getString("ProductQuantityDiscount");
            String products_discount2 = resultSet.getString("ProductQuantityDiscount1");

            return new ContractDTO(contractName, supplierID, sheduled, products_numbers,products_discount,products_discount2);
        } else {
            return null;
        }
    }


    public void delete(String name) throws SQLException {
        String sql = "DELETE FROM Contract WHERE ContractName = ?";
        PreparedStatement statement = c.prepareStatement(sql);
        statement.setString(1, name);
        statement.executeUpdate();
    }

    public void selectAll() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = c.prepareStatement("DELETE FROM Contract");
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
    
    


    public List<Contract> loadAll() throws SQLException, IllegalAccessException {
        String query = "SELECT * FROM Contract";
        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<Contract> contracts = new ArrayList<>();
            while (resultSet.next()) {
                int supplierID = resultSet.getInt("SupplierID");
                String contractName = resultSet.getString("ContractName");
                boolean scheduled = resultSet.getBoolean("Scheduled");
                String productsNumbers = resultSet.getString("ProductsNumbers");
                String productsDiscount = resultSet.getString("ProductQuantityDiscount");
                String productsDiscount1 = resultSet.getString("ProductQuantityDiscount1");


                // Create the Contract object
                Contract contract = new Contract(contractName, supplierID, scheduled, productsNumbers, productsDiscount);
                contracts.add(contract);

                // Process product numbers and discounts
                String[] productsArray = productsNumbers.substring(1, productsNumbers.length() - 1).split("},");
                String[] discountsArray = productsDiscount.substring(1, productsDiscount.length() - 1).split(",");
                String[] discountsArray1 = productsDiscount1.substring(1, productsDiscount1.length() - 1).split(",");


                if (productsArray.length != discountsArray.length) {
                    throw new IllegalArgumentException("Products and discounts length mismatch.");
                }

                HashMap<Product1, HashMap<Integer, Double>> productQuantityDiscount = new HashMap<>();

                for (int i = 0; i < productsArray.length; i++) {
                    String productInfo = productsArray[i];
                    //System.out.println(productInfo);
                    int indexOf = productInfo.indexOf("ID=");
                    if(indexOf==-1)
                        continue;
                    String afterId = productInfo.substring(indexOf+3);
                    String numberText = "";
                    for (char c : afterId.toCharArray()) {
                        if (Character.isDigit(c)) {
                            numberText += c;
                        } else {
                            break;
                        }
                    }
                    int productId = Integer.parseInt(numberText);

                    //System.out.println(id);
                    //int productId = Integer.parseInt(productInfo.split("\\{")[1].split(",")[0].split("=")[1].trim());
                    Double discount = Double.parseDouble(discountsArray[i].trim());
                    Integer amount=Integer.parseInt(discountsArray1[i].trim());
                    

                    Product1 product = SupplierController.getInstance().getProductById(productId);
                    HashMap<Integer, Double> discountMap = new HashMap<>();
                    discountMap.put(amount, discount); // Assuming the key in the map is the index

                    productQuantityDiscount.put(product, discountMap);
                }

                // Call makeContract with the parsed data
                ContractController.getInstance().makeContract(supplierID, contractName, scheduled, productQuantityDiscount, SupplierController.getInstance());
            }
            return contracts;
        }
    }
}