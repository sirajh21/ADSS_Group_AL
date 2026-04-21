package src.domain.Objects;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Order {
    private static int orderCounter = 0;
    private final int orderId;
    private final int quantity;
    private final Date orderDate;
    private final HashMap<ItemSpecs,Integer> items;

    public Order(HashMap<ItemSpecs,Integer> order,Date orderDate) {
        this.orderId = ++orderCounter;
        this.quantity = order.size();
        this.items=order;
        this.orderDate = orderDate;
        writeToCSV();
    }

    public int getOrderId() {
        return orderId;
    }


    public HashMap<ItemSpecs,Integer> getItems(){
        return items;
    }


    public double totalPrice() {
        double totalPrice = 0;
        for (ItemSpecs item : items.keySet()) {
            totalPrice += item.getStorePrice() * items.get(item);
        }
        return totalPrice;
    }


    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder itemNames = new StringBuilder();
        itemNames.append("Order NO':").append(",").append(orderId).append("\n");
        itemNames.append("Quantity:").append(",").append(quantity).append("\n");
        itemNames.append("Order date:").append(",").append(sdf.format(orderDate)).append("\n");
        itemNames.append("Item name").append(",").append("Item price").append(",").append("Quantity").append(",").append("Total Items Price").append("\n");
        for (ItemSpecs item : items.keySet()) {
            itemNames.append(item.getIname());
            itemNames.append(",");
            itemNames.append(item.getStorePrice());
            itemNames.append(",");
            itemNames.append(items.get(item));
            itemNames.append(",");
            itemNames.append(items.get(item) *item.getStorePrice());
            itemNames.append("\n");
        }
        itemNames.append("\n");
        itemNames.append("TotalPrice:").append(",").append(",").append(",").append(totalPrice()).append("\n");
        return itemNames.toString();
    }

    private void writeToCSV() {
        String csvFileName = "docs/Orders/" + "Order_" + this.getOrderId() + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            writer.append(this.toString());
            } catch (IOException ex) {
               throw new RuntimeException(ex);
        }
    }

}

