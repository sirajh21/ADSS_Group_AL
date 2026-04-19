package BusinessLayer;

public class Product {
    private int ProductId;
    private String ProductName;
    private double ProductPrice;

    public Product(int id, String name, double ProductPrice) {
        this.ProductId = id;
        this.ProductName = name;
        this.ProductPrice = ProductPrice;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }
    public void display() {
        System.out.printf(
                "Product: %s (ID: %d) – Price: %.2f%n",
                ProductName, ProductId, ProductPrice
        );
    }
}
