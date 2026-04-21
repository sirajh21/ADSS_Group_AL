package src.domain.Objects;

import java.util.*;



public class ItemSpecs {

    private final HashMap<Date, Item> itemByDate;
    private final String iname;
    private final String cname;
    private int minAmount;
    private int total_amount_in_shelf;
    private int total_amount_in_store;
    private int total_amount;
    private final String manufacture;
    private final double companyPrice;
    private final double storePrice;
    private double discount;
    private double finalprice;
    private double defectAmount;
    private final List<Discount> futureDiscounts = new ArrayList<>();

    public ItemSpecs(String iname, String cname, int minAmount, int totalAmount, String manufacture, double companyPrice, double storePrice, double discount) {
        itemByDate = new HashMap<Date, Item>();
        this.iname = iname;
        this.cname = cname;
        this.total_amount_in_shelf = 0;
        this.total_amount_in_store = totalAmount;
        this.total_amount = totalAmount;
        this.minAmount = minAmount;
        this.manufacture = manufacture;
        this.companyPrice = companyPrice;
        this.storePrice = storePrice;
        this.discount = discount;
        this.defectAmount = 0;
        if (discount > 0)
            this.finalprice = storePrice * discount;
        else
            this.finalprice = storePrice;
    }

    public HashMap<Date, Item> getItemByDate() {
        return itemByDate;
    }
    public int getAmount() {
        return total_amount;
    }


    public void removeDefectedItem(Item item) {
        for (Item i : itemByDate.values()) {
            if (i.equals(item)) {
                itemByDate.remove(i.getExpdate());
                return;
            }
        }
    }
    public int getTotal_amount_in_shelf(){
        return total_amount_in_shelf;
    }
    public int getTotal_amount_in_store(){
        return total_amount_in_store;
    }

    public void addItem(Item item) {
        this.total_amount = item.getAmount();
        itemByDate.put(item.getExpdate(),item);
    }
    public double get_final_price(){
        return this.finalprice;
    }

    public void setAmount(int amount) {
        this.total_amount =  amount;
    }
    public List<Discount> getFutureDiscounts(){
        return futureDiscounts;
    }


    public void transfer_item(int amount){
        if(this.total_amount_in_store<amount){
            System.out.println("Error amount");
        }
        else {
            total_amount_in_shelf+=amount;
            total_amount_in_store-=amount;
        }


    }

    public double getStorePrice() {
        return storePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public String getIname() {
        return iname;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.finalprice = storePrice;
        this.finalprice -= (storePrice * (discount / 100));
    }

    public void addFutureDiscount(Date dateStart, Date dateEnd, double discount) {
        if (dateEnd.before(new Date())){
            System.out.println("wrong date");
        }
        else {
            Discount discount1 = new Discount(this.getCname(), dateStart, dateEnd, discount);
            futureDiscounts.add(discount1);
            this.applyDiscounts();
        }

    }

    public void remove_future_Discount(Discount discount){
        for (Discount discount1 : futureDiscounts) {
            if (discount1.getName().equals(discount.getName())) {
                futureDiscounts.remove(discount1);
                break;
            }
        }
                if (this.discount != 0){
                    this.discount = 0;
                    this.finalprice = storePrice;
                }
    }
    public void remove_future_Discount() {
        for(Discount discount2 : this.getFutureDiscounts()){
            if(discount2.getName().equals(this.cname)){
                this.futureDiscounts.remove(discount2);
                this.removeDiscount();
                break;
            }
        }

    }


    public void applyDiscounts() {
        Date currentDate = new Date();
        for (Discount discountPeriod : futureDiscounts) {
            Date startDate = discountPeriod.getStartDate();
            Date endDate = discountPeriod.getEndDate();

            // Check if the current date is within the discount period
            if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                this.discount = discountPeriod.getDiscountPercentage();
                this.finalprice = (1 - discount / 100) * this.finalprice;
                return;
            }


            if (currentDate.after(endDate)) {
                futureDiscounts.remove(discountPeriod);
            }
        }

        this.discount = 0;
        this.finalprice = storePrice;
    }



    public int Getamount(){
        return this.total_amount;
    }
    public int getMinAmount() {
        return minAmount;
    }
    public void removeDiscount(){
        finalprice = storePrice;
        this.discount = 0;
    }


    public String getName() {
        return iname;
    }


    public void setMinAmount(int amount) {
      this.minAmount = amount;
    }



    public String getCname() {
        return cname;
    }

    public void updateTotalamount(int amount) {
        this.total_amount = this.total_amount + amount;
    }



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("item's Name is: ").append(this.iname).append("\n");
        if (discount > 0 && discount < 100) {
            result.append("item's Discount percentage is: ").append(this.discount).append("\n");
        }
        result.append("item's company price is: ").append(this.companyPrice).append("\n");
        result.append("item's original price is: ").append(this.storePrice).append("\n");
        result.append("item's final price is: ").append(this.finalprice).append("\n");
        result.append("item's Total amount is: ").append(this.total_amount).append("\n");
        result.append("item's Total defect amount is: ").append(this.defectAmount).append("\n");
        result.append("item's Company Name is: ").append(this.manufacture).append("\n");
        result.append("----------------------\n");
        for (Item item : itemByDate.values()) {
            result.append(item.toString()).append("\n");
            result.append("----------------------\n");
        }
        return result.toString();
    }


    public void addingDefectItem(int amount1, DefectType defectReason) {
        if(amount1 > this.total_amount){
            System.out.println("Amount is greater than total amount");
            return;
        }
        total_amount -= amount1;
        if (amount1 > this.total_amount_in_shelf){
            this.total_amount_in_shelf = 0;
            amount1 -= this.total_amount_in_shelf;

            this.total_amount_in_store -= amount1;
        }
       else {
           this.total_amount_in_shelf -= amount1;
        }
       this.defectAmount += amount1;
    }

    public void makeDefectedItem(ItemSpecs itemSpecs, int amount) {
        List<Date> sortedKeys = new ArrayList<>(itemByDate.keySet());
        Collections.sort(sortedKeys);
        for (Date date : sortedKeys) {
            if (amount <= 0) break;
            Item currentItem = itemByDate.get(date);
            int currentAmount = currentItem.getAmount();
            if (currentAmount > amount) {
                currentItem.SetDefectAmount(amount);
                amount = 0;
            } else {
                currentItem.SetDefectAmount(currentAmount);
                amount -= currentAmount;
            }
        }
        this.defectAmount += amount;
    }
}




