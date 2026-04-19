package ServiceLayer;

import BusinessLayer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private OrderController orderController = new OrderController();
    private SupplierControler supplierController;
    public OrderController getOrderController() {
        return orderController;
    }
    public OrderService(SupplierControler supplierController) {
        this.supplierController = supplierController;
    }

    public Order createOrder(int supplierId, String paymentMethod) {
        try {
            Supplier supplier = supplierController.getSupplier(supplierId);
            if (supplier == null) throw new Exception("Supplier not found.");
            EnumPayment method = EnumPayment.valueOf(paymentMethod.toLowerCase());
            return orderController.createOrder(supplier, method);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
            return null;
        }
    }

    public String addProductToOrder(int orderId, int productId, int quantity) {
        try {
            Order order = orderController.getOrderById(orderId);
            Supplier supplier = order.getSupplier();
            Product product = supplier.getSuppProducts().get(productId);
            if (product == null) return "Product not found.";
            orderController.addProductToOrder(orderId, product, quantity);
            return "✅ Product added to order.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String markOrderAsDone(int orderId) {
        try {
            orderController.markOrderAsDone(orderId);
            return "✅ Order marked as done.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String deleteOrder(int orderId) {
        try {
            orderController.deleteOrder(orderId);
            return "✅ Order deleted.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public ArrayList<Order> getAllOrders() {
        try {
            return new ArrayList<>(orderController.getAllOrders().values());
        } catch (Exception e) {
            return null;
        }
    }

    public Order getOrder(int orderId) {
        try {
            return orderController.getOrderById(orderId);
        } catch (Exception e) {
            return null;
        }
    }
}
