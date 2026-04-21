import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class catogeryCon {
    private static int idCounter = 0;
    private final int catogery_id;
    private double discount;
    private final List<items> catogeries;
    private final String catogery_name;

    private final LinkedList<catogeryCon> sub;
    private final List<DisCount> f_discount;

    public catogeryCon(String name) {

        this.catogery_name = name;

        this.discount = 0;
        this.catogeries = new ArrayList<>();
        this.catogery_id = idCounter++;
        this.sub = new LinkedList<>();
        this.f_discount = new ArrayList<>();
    }



    public String GetCategoryName() {
        return catogery_name;
    }

    public String getCategoryName() {
        return catogery_name;
    }
    public void addsub(catogeryCon sub) {
        this.sub.add(sub);
    }
    public int getCatId() {
        return catogery_id;
    }



    public LinkedList<catogeryCon> getSubCategory() {
        return this.sub;
    }
    public List<items> getCategoryItems() {
        return catogeries;
    }
    public List<DisCount> getFutureDiscount() {
        return f_discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void addCategoryItem(items item) {
        this.catogeries.add(item);
    }

    public void removeCategoryItem(String name) {
        this.catogeries.removeIf(item -> item.getIname().equals(name));
    }

    public void addsiscount(double discount) {
        for (int i = 0; i < this.catogeries.size(); i++) {
            items item = this.catogeries.get(i);
            item.setDiscount(discount);
        }

        for (int i = 0; i < this.sub.size(); i++) {
            catogeryCon category = this.sub.get(i);

            for (int j = 0; j < category.getCategoryItems().size(); j++) {
                items item = category.getCategoryItems().get(j);
                item.setDiscount(discount);
            }
        }

        this.discount = discount;
    }

    public void apply_future_discounts() {
        Date today = new Date();

        for (DisCount discount : f_discount) {
            boolean started = discount.getDate_begin().before(today) || discount.getDate_begin().equals(today);
            boolean notEnded = discount.getEnddate_end().after(today);

            if (started && notEnded) {
                this.addsiscount(discount.getDiscount_P());
                this.discount = discount.getDiscount_P();
                return;
            }
        }

        Iterator<DisCount> iterator = f_discount.iterator();
        while (iterator.hasNext()) {
            DisCount discount = iterator.next();
            if (discount.getEnddate_end().before(today)) {
                iterator.remove();
            }
        }

        this.discount = 0;
    }


    public void addCatDiscount_byDate(Date datestart, Date dateend, double discount) {
        if (dateend.before(new Date())) {
            System.out.println("wrong date");
        }

        DisCount newDiscount = new DisCount(this.getCategoryName(), datestart, dateend, discount);
        f_discount.add(newDiscount);

        for (items item : this.catogeries) {
            item.addFutureDiscount(datestart, dateend, discount);
        }

        this.apply_future_discounts();
    }

    public void removeCatDiscount() {
        for (int i = 0; i < this.catogeries.size(); i++) {
            this.catogeries.get(i).removeDiscount();
        }
        this.discount = 0;
    }

    public void remove_future_CatDiscount(Date dateStart, Date dateEnd, String CategoryName) {
        Iterator<DisCount> iterator = f_discount.iterator();
        while (iterator.hasNext()) {
            DisCount currentDiscount = iterator.next();
            if (currentDiscount.getName().equals(CategoryName)) {
                iterator.remove();
                this.discount = 0;
                break;
            }
        }

        for (int i = 0; i < this.catogeries.size(); i++) {
            items item = this.catogeries.get(i);

            if (item.getCname().equals(CategoryName)) {
                DisCount discountToRemove = new DisCount(item.getCname(), dateStart, dateEnd, discount);
                item.remove_future_Discount(discountToRemove);
            }
        }
        }


    public void removeSUBCategory(String categoryName) {
        this.sub.removeIf(subCategory -> subCategory.getCategoryName().equals(categoryName));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category iD: ").append(catogery_id).append("\n");

        sb.append("Category Name: ").append(catogery_name).append("\n");

        if (this.getClass().equals(foodcat.class)) {
            foodcat sub = (foodcat) this;
            sb.append("Upper Category: ").append(sub.getUpperCat()).append("\n");
        }

        sb.append("Discount: ").append(discount).append("%\n");

        if (!getCategoryItems().isEmpty()) {
            sb.append("Items in Category: \n");
            for (items item : catogeries) {
                sb.append("  - ").append(item.toString()).append("\n");
            }
        }

        if (!sub.isEmpty()) {
            sb.append("Subcategories: \n");
            for (int i = 0; i < sub.size(); i++) {
                sb.append("  - ").append(sub.get(i).GetCategoryName()).append("\n");
            }
        }

        return sb.toString();
    }
}