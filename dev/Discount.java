package src.domain.Objects;

import java.util.Date;
import java.util.Objects;

public class Discount {
    private final String name;
    private final Date startDate;
    private final Date endDate;
    private final double discountPercentage;

    public Discount(String name, Date startDate, Date endDate, double discountPercentage) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercentage = discountPercentage;
    }
    public String getName() {
        return name;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, discountPercentage);
    }

}

