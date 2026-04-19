package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contract {
    private int supplierId;
    private String contractName;
    private ArrayList<WorkDays> SuppWorkDays;
    private EnumSuppType SupplierMethod;
    private Map<Integer, Product> ProductsCatalogNumber;
    public Map<Product, Map<Integer, Integer>> QuantityDiscountPerProduct;

    public Contract(int supplierId, String contractNum, EnumSuppType suppmethod, ArrayList<WorkDays> SuppWorkDays) {
        this.supplierId = supplierId;
        this.contractName = contractNum;
        this.SupplierMethod = suppmethod;
        this.ProductsCatalogNumber = new HashMap<>();
        this.QuantityDiscountPerProduct = new HashMap<>();
        this.SuppWorkDays = SuppWorkDays;
    }

    public void AddProduct(Product p) {
        if (p == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        ProductsCatalogNumber.put(p.getProductId(), p);
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void DefineDiscount(Product p, int quantity, Integer discount) {
        if (p == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        Map<Integer, Integer> discountMap = new HashMap<>();
        discountMap.put(quantity, discount);
        QuantityDiscountPerProduct.put(p, discountMap);
    }

    public String getContractName() {
        return contractName;
    }

    public Map<Integer, Product> getProductsCatalogNumber() {
        return ProductsCatalogNumber;
    }

    public boolean IsProductInContract(int productId) {
        if (this.ProductsCatalogNumber.get(productId) != null) {
            return true;
        }
        return false;
    }

    public void SetContractProducts(Product[] products) {
        for (Product p : products) {
            this.ProductsCatalogNumber.put(p.getProductId(), p);
        }
    }

    public void display() {

        /* 1. Header */
        System.out.printf("📄 Contract: %s | Supplier ID: %d | Method: %s%n", contractName, supplierId, SupplierMethod);

        /* 2. Work days */
        if (SuppWorkDays != null && !SuppWorkDays.isEmpty()) {
            System.out.print("   Work Days: ");
            for (int i = 0; i < SuppWorkDays.size(); i++) {
                System.out.print(SuppWorkDays.get(i));
                if (i < SuppWorkDays.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }

        /* 3. Products catalog */
        if (ProductsCatalogNumber.isEmpty()) {
            System.out.println("   No products defined.");
        } else {
            System.out.println("   Products:");
            ProductsCatalogNumber.values().forEach(p -> System.out.printf("     • %s (ID %d) – %.2f%n", p.getProductName(), p.getProductId(), p.getProductPrice()));
        }

        /* 4. Quantity discounts */
        if (!QuantityDiscountPerProduct.isEmpty()) {
            System.out.println("   Quantity‑based Discounts:");
            QuantityDiscountPerProduct.forEach((prod, map) -> map.forEach((qty, disc) -> System.out.printf("     • %s → %d+ units ⇒ %d%% off%n", prod.getProductName(), qty, disc)));
        }
    }


}
