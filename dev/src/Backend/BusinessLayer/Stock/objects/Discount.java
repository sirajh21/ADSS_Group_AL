package Backend.BusinessLayer.Stock.objects;

import java.time.LocalDate;

public class Discount {
    private int discountID;
    private Product product;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;
    private double percent;

    public Discount(int discountID, Category category, LocalDate startDate, LocalDate endDate, double percent) {
        this.discountID = discountID;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.product = null;
    }

    public Discount(Product product, LocalDate startDate, LocalDate endDate, double percent, int discountID) {
        this.discountID = discountID;
        this.category = null;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.product = product;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
