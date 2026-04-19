package BusinessLayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int orderId;
    private Supplier supplier;
    private Date orderDate;
    private EnumPayment paymentMethod;
    private String OrderStatus;
    private Map<Product, Integer> products; // Product and Quantity
    private double totalCost;

    public Order(int orderId, Supplier supplier, EnumPayment paymentMethod) {
        this.orderId = orderId;
        this.supplier = supplier;
        this.paymentMethod = paymentMethod;
        this.orderDate = new Date();
        this.products = new HashMap<>();
        this.totalCost = 0;
        this.OrderStatus = "In Progress";
    }

    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid product or quantity.");
        }

        products.put(product, quantity);
        totalCost += calculatePrice(product, quantity);
    }

    private double calculatePrice(Product product, int quantity) {
        double basePrice = product.getProductPrice() * quantity;
        for (Contract contract : supplier.getSuppContracts()) {
            Map<Product, Map<Integer, Integer>> discounts = contract.QuantityDiscountPerProduct;
            if (discounts.containsKey(product)) {
                for (Map.Entry<Integer, Integer> entry : discounts.get(product).entrySet()) {
                    if (quantity >= entry.getKey()) {
                        double discountRate = entry.getValue() / 100.0;
                        return basePrice * (1 - discountRate);
                    }
                }
            }
        }
        return basePrice;
    }

    public void displayOrder() {
        System.out.printf("📦 Order #%d from Supplier: %s%n", orderId, supplier.getSupplierName());
        System.out.println("Products:");
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            System.out.printf("   - %s x %d%n", entry.getKey().getProductName(), entry.getValue());
        }
        System.out.printf("Total Price: %.2f%n", totalCost);
        System.out.printf("Payment Method: %s%n", paymentMethod);
        System.out.printf("Order Date: %s%n", orderDate.toString());
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Map<Product, Integer> getOrderedProducts() {
        return products;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void ChangeStatus() {
        if (OrderStatus.equals("Done")) {
            throw new IllegalArgumentException("the order is done already");
        } else {
            this.OrderStatus = "Done";
        }
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public EnumPayment getPaymentMethod() {
        return paymentMethod;
    }
}
