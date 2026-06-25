package Backend.BusinessLayer.Stock.controllers;


import Backend.BusinessLayer.Stock.objects.Contract;
import Backend.BusinessLayer.Stock.objects.Product1;
import Backend.BusinessLayer.Stock.objects.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ContractController {
    private static ContractController instance = new ContractController();
    ;
    public ArrayList<Contract> contracts;

    public ContractController() {
        this.contracts = new ArrayList<>();
    }

    public static ContractController getInstance() {
        return instance;
    }

    DataBaseController dataBaseController = DataBaseController.getInstance();

    public double checkPrice(int supplier_id, HashMap<Integer, Integer> productAndQuantity) {
        double price = 0;
        for (Contract contract : contracts) {
            if (contract.getSupplier_id() == supplier_id) {
                price = contract.checkPrice(productAndQuantity);
            }
        }
        return price;
    }

    public String makeContract(int id, String contractName, boolean scheduled,
                               HashMap<Product1, HashMap<Integer, Double>> productQuantityDiscount,
                               SupplierController supplierController) {

        try {
            if (contractName == null || productQuantityDiscount == null) {
                throw new IllegalArgumentException("Data empty");
            }

            Supplier supplier = supplierController.getSupplier(id);
            if (supplier == null) {
                throw new IllegalArgumentException("Supplier not found");
            }

            for (Product1 p : productQuantityDiscount.keySet()) {
                if (!supplierController.getAllProducts().containsKey(p.getProduct_id())) {
                    throw new IllegalArgumentException("Product not found");
                }
            }

            ArrayList<Product1> productsNumber1 = new ArrayList<>(productQuantityDiscount.keySet());
            Contract contract = new Contract(contractName, supplier.getSupplier_id(), scheduled, productsNumber1, productQuantityDiscount);
            supplier.AddContract(contract);
            contracts.add(contract);
            dataBaseController.insertContract(contract.objectDTOContract());
            return "Contract made successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to make contract: " + e.getMessage();
        }
    }



    public String deleteContract(String name, SupplierController supplierController) throws IllegalAccessException, SQLException {

        for (Contract c : contracts) {
            if (c.getContract_name().equals(name)) {
                for (Supplier s : supplierController.getAllSuppliers()) {
                    if (s.getContracts().contains(c)) {
                        s.deleteContract(c);
                        contracts.remove(c);
                        break;
                    }
                }
            }
        }

        dataBaseController.deleteContract(name);
        return "Contract deleted successfully";
    }

    public ArrayList<Contract> getAllContracts() {
        return contracts;
    }

    public String deleteAllcontracts() throws SQLException, IllegalAccessException {
        dataBaseController.selectAllContracts();

        return "All contracts deleted";
    }
}
