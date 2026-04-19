package ServiceLayer;

import BusinessLayer.*;

import java.util.ArrayList;

public class ContractService {
    private ContractController contractController = new ContractController();

    public ContractController getContractController() {
        return contractController;
    }

    public String createContract(int supplierId, String contractName, EnumSuppType method, ArrayList<WorkDays> workDays) {
        try {
            contractController.createContract(supplierId, contractName, method, workDays);
            return "✅ Contract created.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }
    public String deleteContract(String contractName) {
        try {
            return contractController.deleteContract(contractName);
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String addProductToContract(String contractName, Product product) {
        try {
            contractController.addProductToContract(contractName, product);
            return "✅ Product added to contract.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public boolean IsProductInCon(String ContractName, int productId) {
        try {
            return contractController.IsProductInContractt(ContractName, productId);
        } catch (Exception e) {
            return false;
        }
    }

    public String defineDiscount(String contractName, Product product, int quantity, int discountPercent) {
        try {
            contractController.defineDiscount(contractName, product, quantity, discountPercent);
            return "✅ Discount defined.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String setProductsToContract(String contractName, Product[] products) {
        try {
            contractController.setProductsToContract(contractName, products);
            return "✅ Products set.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String displayContract(String contractName) {
        try {
            contractController.displayContract(contractName);
            return "✅ Contract displayed.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String displayAllContracts() {
        try {
            contractController.displayAllContracts();
            return "✅ All contracts displayed.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public Contract getContract(String contractName) {
        try {
            return contractController.getContract(contractName);
        } catch (Exception e) {
            return null;
        }
    }
}
