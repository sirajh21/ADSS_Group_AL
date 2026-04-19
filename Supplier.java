package BusinessLayer;

import java.sql.Date;
import java.util.*;

public class ContractController {
    private Map<String, Contract> SystemContracts;

    public ContractController() {
        this.SystemContracts = new HashMap<>();
    }

    public void createContract(int supplierId, String contractName, EnumSuppType method, ArrayList<WorkDays> workDays) {
        if (SystemContracts.containsKey(contractName)) {
            throw new IllegalArgumentException("⚠️ Contract with this name already exists.");
        }
        Contract newContract = new Contract(supplierId, contractName, method, workDays);
        SystemContracts.put(contractName, newContract);
    }
    public boolean IsProductInContractt(String ContractName,int productId) {
        Contract contract = SystemContracts.get(ContractName);
        if (contract == null) {
            throw new IllegalArgumentException("⚠️ Contract not found.");
        }
        return contract.IsProductInContract(productId);
    }


    public void addProductToContract(String contractName, Product product) {
        Contract contract = SystemContracts.get(contractName);
        if (contract == null) {
            throw new IllegalArgumentException("⚠️ Contract not found.");
        }
        contract.AddProduct(product);
    }

    public void defineDiscount(String contractName, Product product, int quantity, int discountPercent) {
        Contract contract = SystemContracts.get(contractName);
        if (contract == null) {
            throw new IllegalArgumentException("⚠️ Contract not found.");
        }
        Map<Integer, Integer> quantityMap = new HashMap<>();
        quantityMap.put(quantity, discountPercent);

        if (contract.QuantityDiscountPerProduct.containsKey(product)) {
            contract.QuantityDiscountPerProduct.get(product).put(quantity, discountPercent);
        } else {
            contract.QuantityDiscountPerProduct.put(product, quantityMap);
        }
    }

    public void setProductsToContract(String contractName, Product[] products) {
        Contract contract = SystemContracts.get(contractName);
        if (contract == null) {
            throw new IllegalArgumentException("⚠️ Contract not found.");
        }
        contract.SetContractProducts(products);
    }

    public void displayContract(String contractName) {
        Contract contract = SystemContracts.get(contractName);
        if (contract == null) {
            System.out.println("⚠️ Contract not found.");
            return;
        }
        contract.display();
    }

    public void displayAllContracts() {
        if (SystemContracts.isEmpty()) {
            System.out.println("📭 No contracts available.");
            return;
        }

        System.out.println("📜 All Contracts:");
        for (Contract contract : SystemContracts.values()) {
            System.out.println("--------------------------------------------");
            contract.display();
        }
        System.out.println("--------------------------------------------");
    }
    public String deleteContract(String contractName) {
        if (!SystemContracts.containsKey(contractName)) {
            return "❌ Contract does not exist.";
        }
        SystemContracts.remove(contractName);
        return "✅ Contract deleted successfully.";
    }

    public Contract getContract(String contractName) {
        return SystemContracts.get(contractName);
    }

    public ArrayList<String> getAllContractNames() {
        return new ArrayList<>(SystemContracts.keySet());
    }
}
