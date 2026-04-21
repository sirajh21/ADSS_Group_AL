import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class items {

    private final HashMap<Date, product> itembydate;
    private final String iname;
    private final String cname;
    private final String manufacture;
    private final double price_of_company;
    private final double price_of_store;

    private int minAmount;
    private int amountShelf;
    private int amountstore;
    private int total_a;

    private double discount;
    private double finalprice;
    private double defectAmount;

    private final List<DisCount> futureDis;

    public items(String iname, String cname, int minAmount, int totalAmount,
                 String manufacture, double companyPrice, double storePrice, double discount) {
        this.itembydate = new HashMap<>();
        this.futureDis = new ArrayList<>();

        this.iname = iname;
        this.cname = cname;
        this.minAmount = minAmount;
        this.manufacture = manufacture;
        this.price_of_company = companyPrice;
        this.price_of_store = storePrice;

        this.amountShelf = 0;
        this.amountstore = totalAmount;
        this.total_a = totalAmount;

        this.discount = discount;
        this.defectAmount = 0;

        if (discount > 0) {
            this.finalprice = storePrice * discount;
        } else {
            this.finalprice = storePrice;
        }
    }

    public HashMap<Date, product> getItembydate() {
        return itembydate;
    }

    public int getAmount() {
        return total_a;
    }



    public int getAmountShelf() {
        return amountShelf;
    }

    public int getAmountstore() {
        return amountstore;
    }

    public double get_final_price() {
        return finalprice;
    }

    public List<DisCount> getFutureDis() {
        return futureDis;
    }

    public double getPrice_of_store() {
        return price_of_store;
    }

    public double getDiscount() {
        return discount;
    }

    public String getIname() {
        return iname;
    }

    public String getName() {
        return iname;
    }

    public String getCname() {
        return cname;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setAmount(int amount) {
        this.total_a = amount;
    }

    public void setMinAmount(int amount) {
        this.minAmount = amount;
    }

    public void updateTotalamount(int amount) {
        this.total_a += amount;
    }

    public void addItem(product item) {
        this.total_a = item.getAmount();
        itembydate.put(item.finaldate(), item);
    }

    public void removeDefectedItem(product item) {
        for (product currentItem : itembydate.values()) {
            if (currentItem.equals(item)) {
                itembydate.remove(currentItem.finaldate());
                return;
            }
        }
    }

    public void transfer_item(int amount) {
        if (this.amountstore < amount) {
            System.out.println("Error amount");
            return;
        }

        amountShelf += amount;
        amountstore -= amount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.finalprice = price_of_store;
        this.finalprice -= (price_of_store * (discount / 100));
    }

    public void removeDiscount() {
        this.discount = 0;
        this.finalprice = price_of_store;
    }

    public void addFutureDiscount(Date dateStart, Date dateEnd, double discount) {
        if (dateEnd.before(new Date())) {
            System.out.println("wrong date");
            return;
        }

        DisCount newDiscount = new DisCount(this.getCname(), dateStart, dateEnd, discount);
        futureDis.add(newDiscount);
        applyDiscounts();
    }

    public void remove_future_Discount(DisCount discount) {
        Iterator<DisCount> iterator = futureDis.iterator();

        while (iterator.hasNext()) {
            DisCount currentDiscount = iterator.next();
            if (currentDiscount.getName().equals(discount.getName())) {
                iterator.remove();
                break;
            }
        }

        if (this.discount != 0) {
            this.discount = 0;
            this.finalprice = price_of_store;
        }
    }

    public void remove_future_Discount() {
        Iterator<DisCount> iterator = this.getFutureDis().iterator();

        while (iterator.hasNext()) {
            DisCount currentDiscount = iterator.next();
            if (currentDiscount.getName().equals(this.cname)) {
                iterator.remove();
                this.removeDiscount();
                break;
            }
        }
    }

    public void applyDiscounts() {
        Date currentDate = new Date();

        Iterator<DisCount> iterator = futureDis.iterator();
        while (iterator.hasNext()) {
            DisCount discountPeriod = iterator.next();
            Date startDate = discountPeriod.getDate_begin();
            Date endDate = discountPeriod.getEnddate_end();

            if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                this.discount = discountPeriod.getDiscount_P();
                this.finalprice = (1 - discount / 100) * this.finalprice;
                return;
            }

            if (currentDate.after(endDate)) {
                iterator.remove();
            }
        }

        this.discount = 0;
        this.finalprice = price_of_store;
    }

    public void add_duf_item(int amount1, statusDef defectReason) {
        if (amount1 > this.total_a) {
            System.out.println("Amount is greater than total amount");
            return;
        }

        total_a -= amount1;

        if (amount1 > this.amountShelf) {
            this.amountShelf = 0;
            amount1 -= this.amountShelf;
            this.amountstore -= amount1;
        } else {
            this.amountShelf -= amount1;
        }

        this.defectAmount += amount1;
    }

    public void makeDefectedItem( int amount) {
        List<Date> sortedKeys = new ArrayList<>(itembydate.keySet());
        Collections.sort(sortedKeys);

        for (Date date : sortedKeys) {
            if (amount <= 0) {
                break;
            }
            product currentItem = itembydate.get(date);
            int currentAmount = currentItem.getAmount();

            if (currentAmount > amount) {
                currentItem.setamount(amount);
                amount = 0;
            } else {
                currentItem.setamount(currentAmount);
                amount -= currentAmount;
            }
        }

        this.defectAmount += amount;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("item's Name is: ").append(this.iname).append("\n");

        if (discount > 0 && discount < 100) {
            result.append("item's Discount percentage is: ").append(this.discount).append("\n");
        }

        result.append("item's company price is: ").append(this.price_of_company).append("\n");
        result.append("item's original price is: ").append(this.price_of_store).append("\n");
        result.append("item's final price is: ").append(this.finalprice).append("\n");
        result.append("item's Total amount is: ").append(this.total_a).append("\n");
        result.append("item's Total defect amount is: ").append(this.defectAmount).append("\n");
        result.append("item's Company Name is: ").append(this.manufacture).append("\n");
        result.append("----------------------\n");

        for (product item : itembydate.values()) {
            result.append(item.toString()).append("\n");
            result.append("----------------------\n");
        }

        return result.toString();
    }
}