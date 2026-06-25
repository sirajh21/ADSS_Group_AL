package Backend.BusinessLayer.Stock.objects;

import Backend.DataAccessLayer.*;

import java.time.LocalDate;
import java.util.*;

public class Order {
    //  private static int idCounter = 1;
    private int order_id;
    private List<Integer> products_id = new LinkedList<>();
    private Supplier supplier;
    private HashMap<Integer, Integer> productsAndAmount = new HashMap<>(); // key: product id , value: amount
    private String productsAndAmountString;
    private HashMap<Product1, Double> pricePerProducts = new HashMap<>(); // key: product, value:price for this product after discount
    private LocalDate date;
    private String dateString;


    public Order(int id, List<Integer> products_id, Supplier supplier, HashMap<Integer, Integer> productsAndAmount, LocalDate date) {
        this.order_id = id;
        this.products_id = products_id;
        this.supplier = supplier;
        this.productsAndAmount = productsAndAmount;
        this.date = date;
    }

    public Order(Order o) {
        this.order_id = o.order_id;
        this.products_id = o.products_id;
        this.supplier = o.supplier;
        this.productsAndAmount = o.productsAndAmount;
        this.date = o.date;
    }

    public Order(int id, int supplierID, String productsAndAmount, String date) {
        this.order_id = id;
        this.dateString = date;
        this.productsAndAmountString = productsAndAmount;
    }

    public boolean isPeriodicOrder() {
        return false;
    }

    ;

    // @Override
    public String toString() {

        String prices = pricePerProductsToString();
        return "Order num<" + order_id + ">{\n" +
                "  Supplier Id: " + supplier.getSupplier_id() + ",\n" +
                "  Supplier Name: " + supplier.getName() + ",\n" +
                "  Order Date: " + date + ",\n" +
                "  Price and quantity for each product: " + prices + "\n" +
                "}";
    }

    private String pricePerProductsToString() {
        String prices = "";
        for (Map.Entry<Product1, Double> entry : pricePerProducts.entrySet()) {
            int id = entry.getKey().getProduct_id();
            int price = entry.getKey().getList_price();
            String productName = entry.getKey().getProduct_name();
            int amount_product = productsAndAmount.get(id);
            double discount = 100 - (100 * entry.getValue() / (amount_product * price));
            prices += "\n    " + productName + "(ID:" + id + "), Price:" + price + ", Quantity:" + amount_product + ", discount:%" + discount + ", TotalCost:$" + entry.getValue();
        }
        return prices;
    }

    public void calculatPricePerProduct() {
        ArrayList<Contract> contracts = supplier.getContracts();
        for (Contract contract : contracts) {
            for (Integer product_id : products_id) {

                int amount_product = productsAndAmount.get(product_id);
                Product1 product = contract.getProduct(product_id);
                if(product == null){break;}
                Map<Integer, Double> quantityDiscountPerProduct = contract.getProductQuantityDiscount(product);
                if (quantityDiscountPerProduct.isEmpty()) {
                    pricePerProducts.put(product, (double) amount_product * product.getList_price());
                } else {
                    // double discount = quantityDiscountPerProduct.get(amount_product);
                    //if(quantityDiscountPerProduct.get(amount_product) == null)
                    double discount;
                    if (!quantityDiscountPerProduct.containsKey(amount_product)) {
                        discount = findNearestDiscount(quantityDiscountPerProduct.keySet(), amount_product, quantityDiscountPerProduct);


                    } else {
                        discount = quantityDiscountPerProduct.get(amount_product);

                    }
                    double price = ((100 - discount) / 100) * amount_product * product.getList_price();
                    pricePerProducts.put(product, price);


                }
            }

        }
    }

    private Double findNearestDiscount(Set<Integer> availableQuantities, int amount, Map<Integer, Double> quantityDiscountPerProduct) {
        Double nearestDiscount = null;
        int minDifference = Integer.MAX_VALUE;

        for (Integer quantity : availableQuantities) {
            int difference = Math.abs(quantity - amount);
            if (difference < minDifference) {
                minDifference = difference;
                nearestDiscount = quantityDiscountPerProduct.get(quantity);
            }
        }

        return nearestDiscount;
    }


    public OrderDTO object2Dto() {
        return new OrderDTO(order_id, supplier.getSupplier_id(), productsAndAmount.toString(), date.toString());
    }

    public int getOrder_id() {
        return order_id;
    }

    public List<Integer> getProducts_id() {
        return products_id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getSupplierID() {
        return supplier.getSupplier_id();
    }

    public HashMap<Integer, Integer> getProductsAndAmount() {
        return productsAndAmount;
    }

    public HashMap<Product1, Double> getPricePerProducts() {
        return pricePerProducts;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (double price : pricePerProducts.values()) {
            totalPrice += price;
        }
        return totalPrice;
    }
}
