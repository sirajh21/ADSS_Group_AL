import java.text.SimpleDateFormat;
import java.util.Date;

public class product {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static int count = 0;

    private final int id;

    private final String name;
    private final Date finalDate;
    private final int amount;

    private statusDef type;
    private int anInt;

    public product(String name, Date expirationDate, int amount) {
        this.id = count++;
        this.name = name;
        this.finalDate = expirationDate;
        this.amount = amount;
        this.anInt = 0;
        this.type = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public int getAmount() {
        return amount;
    }

    public int getAnInt() {
        return anInt;
    }

    public statusDef getType() {
        return type;
    }

    public void setDefectAmount(int amount) {
        this.type = statusDef.dammage;
        this.anInt = amount;
    }

    private String formatDate() {
        return new SimpleDateFormat(DATE_PATTERN).format(finalDate);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("item's id is: ").append(id).append("\n");
        result.append("item's name is: ").append(name).append("\n");
        result.append("item's expiration date is: ").append(formatDate()).append("\n");
        result.append("amount of defect items: ").append(anInt).append("\n");

        if (type != null) {
            result.append(type).append("\n");
        }

        return result.toString();
    }

    // ===== compatibility methods =====

    public void setamount(int amount) {
        setDefectAmount(amount);
    }

    public Date finaldate() {
        return getFinalDate();
    }
}