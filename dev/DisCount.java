import java.util.Date;
import java.util.Objects;

public class DisCount {
    private final String Nackename;
    private final Date date_begin;
    private final Date enddate_end;
    private final double discount_P;

    public DisCount(String name, Date startDate, Date endDate, double discount_P) {
        this.Nackename = name;
        this.date_begin = startDate;
        this.enddate_end = endDate;
        this.discount_P = discount_P;
    }
    public Date getDate_begin() {
        return date_begin;
    }
    public String getName() {
        return Nackename;
    }

    public double getDiscount_P() {
        return discount_P;
    }
    public Date getEnddate_end() {
        return enddate_end;
    }

    @Override

    public int hashCode() {
        return Objects.hash(Nackename, date_begin, enddate_end, discount_P);
    }

}

