package Backend.DataAccessLayer;


public class OrderDTO {
    private static int idCounter = 1;
    private  int order_id ;
    private int supplier;
    private String productsAndAmount; // key: product id , value: amount
    private String date;

    public OrderDTO( int id,int supplierID, String productsAndAmount, String date) {
        this.order_id = id;
        this.supplier = supplierID;
        this.productsAndAmount = productsAndAmount;
        this.date = date;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        OrderDTO.idCounter = idCounter;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getSupplier() {
        return supplier;
    }

    public void setSupplier(int supplier) {
        this.supplier = supplier;
    }

    public String getProductsAndAmount() {
        return productsAndAmount;
    }

    public void setProductsAndAmount(String productsAndAmount) {
        this.productsAndAmount = productsAndAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPeriodicOrder() {return false;};
}
