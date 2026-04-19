package BusinessLayer;

import java.util.HashMap;
import java.util.Map;

public class OrderController {

    private Map<Integer, Order> orders;
    private int orderCounter;

    public OrderController() {
        this.orders = new HashMap<>();
        this.orderCounter = 1;
    }

    public Order createOrder(Supplier supplier, EnumPayment paymentMethod) {
        Order newOrder = new Order(orderCounter, supplier, paymentMethod);
        orders.put(orderCounter, newOrder);
        orderCounter++;
        return newOrder;
    }

    public void addProductToOrder(int orderId, Product product, int quantity) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }
        order.addProduct(product, quantity);
    }

    public void markOrderAsDone(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }
        order.ChangeStatus();
    }

    public void displayOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("⚠️ Order not found.");
            return;
        }
        order.displayOrder();
        System.out.println("Status: " + order.getOrderStatus());
        System.out.println("--------------------------------------------------");
    }

    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("📭 No orders found.");
            return;
        }

        System.out.println("📋 All Orders:");
        for (Order order : orders.values()) {
            displayOrder(order.getOrderId());
        }
    }

    public Order getOrderById(int orderId) {
        return orders.get(orderId);
    }

    public Map<Integer, Order> getAllOrders() {
        return orders;
    }

    public void deleteOrder(int orderId) {
        if (!orders.containsKey(orderId)) {
            System.out.println("⚠️ Cannot delete. Order ID " + orderId + " does not exist.");
            return;
        }
        orders.remove(orderId);
        System.out.println("✅ Order ID " + orderId + " has been successfully deleted.");
    }

}
