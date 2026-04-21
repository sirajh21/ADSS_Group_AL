import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static int orderCounter = 0;

    private final int orderId;
    private final int quantity;
    private final Date orderDate;
    private final HashMap<items, Integer> items;

    public Order(HashMap<items, Integer> items, Date orderDate) {
        this.orderId = ++orderCounter;
        this.quantity = items.size();
        this.items = items;
        this.orderDate = orderDate;
        write();
    }

    public int getOrderId() {
        return orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public HashMap<items, Integer> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double totalPrice = 0;

        for (Map.Entry<items, Integer> entry : items.entrySet()) {
            items item = entry.getKey();
            int amount = entry.getValue();
            totalPrice += item.getPrice_of_store() * amount;
        }

        return totalPrice;
    }

    private String formatDate() {
        return new SimpleDateFormat(DATE_PATTERN).format(orderDate);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Order NO':,").append(orderId).append("\n");
        result.append("Quantity:,").append(quantity).append("\n");
        result.append("Order date:,").append(formatDate()).append("\n");
        result.append("Item name,Item price,Quantity,Total Items Price\n");

        for (Map.Entry<items, Integer> entry : items.entrySet()) {
            items item = entry.getKey();
            int amount = entry.getValue();

            result.append(item.getIname()).append(",");
            result.append(item.getPrice_of_store()).append(",");
            result.append(amount).append(",");
            result.append(amount * item.getPrice_of_store()).append("\n");
        }

        result.append("\n");
        result.append("TotalPrice:,,,").append(getTotalPrice()).append("\n");

        return result.toString();
    }

    private void write
            () {
        String folderPath = "docs/Orders";
        String filePath = folderPath + "/Order_" + orderId + ".csv";

        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.append(toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // compatibility
    public double totalPrice() {
        return getTotalPrice();
    }

    private void writeToCSV() {
        write();
    }
}