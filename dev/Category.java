package src.domain.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Category {
    private static int idCounter = 0;
    private final String CategoryName;
    private final int CatId;
    private double discount;
    private final List<ItemSpecs> CategoryItems;
    private final LinkedList<Category> subCat;
    private final List<Discount> futureDiscounts = new ArrayList<>();

    public Category(String name) {
        this.CategoryName = name;
        this.CatId = idCounter++;
        this.discount=0;
        this.CategoryItems = new ArrayList<>();
        this.subCat = new LinkedList<>();
    }

    public void addSubCat(Category subCat) {
        this.subCat.add(subCat);
    }


    public String GetCategoryName() {
        return CategoryName;
    }
    public int getCatId() {
        return CatId;
    }
    public List<ItemSpecs> getCategoryItems() {
        return CategoryItems;
    }

    public void addCategoryItem(ItemSpecs item) {
        this.CategoryItems.add(item);
    }
    public void removeCategoryItem(String name) {
        this.CategoryItems.removeIf(item -> item.getIname().equals(name));
    }

    public void addCatDiscount(double discount) {
        for (ItemSpecs item : this.CategoryItems) {
            item.setDiscount(discount);
        }
        for (Category category : this.subCat) {
            for (ItemSpecs item : category.getCategoryItems()) {
                item.setDiscount(discount);
            }
        }
        this.discount=discount;
    }



    public void apply_future_discounts() {
        Date today = new Date();

        for (Discount discount : futureDiscounts) {
            if ((discount.getStartDate().equals(today) || discount.getStartDate().before(today)) && discount.getEndDate().after(today)) {
                this.addCatDiscount(discount.getDiscountPercentage());
                this.discount = discount.getDiscountPercentage();
                return;
            }
            if (discount.getEndDate().before(today)) {
                this.remove_future_CatDiscount(discount.getStartDate(), discount.getEndDate(), discount.getName());
                futureDiscounts.remove(discount);
            }
        }
            this.discount = 0;
    }

    public void addCatDiscount_byDate(Date dateStart, Date dateEnd, double discount) {
        if (dateEnd.before(new Date())){
            System.out.println("wrong date");
        }
        Discount discount1 = new Discount(this.getCategoryName(), dateStart, dateEnd, discount);
        futureDiscounts.add(discount1);
        for (ItemSpecs item : this.CategoryItems) {
            item.addFutureDiscount(dateStart, dateEnd, discount);
        }
        this.apply_future_discounts();
    }
    public List<Discount> getFutureDiscount() {
        return futureDiscounts;
    }
    public void removeCatDiscount() {
        for (ItemSpecs item : this.CategoryItems) {
                item.removeDiscount();
        }
        this.discount=0;
    }
    public void remove_future_CatDiscount(Date dateStart, Date dateEnd,String CategoryName) {
        for (Discount discount : futureDiscounts) {
            if(discount.getName().equals(CategoryName)){
                futureDiscounts.remove(discount);
                this.discount = 0;
                break;
            }
        }
        for (ItemSpecs item : this.CategoryItems) {
            if(item.getCname().equals(CategoryName)) {
                Discount discount1 = new Discount(item.getCname(), dateStart, dateEnd, discount);
                item.remove_future_Discount(discount1);


            }
        }
    }

    public double getDiscount() {
        return discount;
    }
    public String getCategoryName(){
        return CategoryName;
    }
    public void removeSUBCategory(String  categoryName){
        this.subCat.removeIf(subCat -> subCat.getCategoryName().equals(categoryName));
    }

    public LinkedList<Category> getSubCategory(){
        return this.subCat;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category Name: ").append(CategoryName).append("\n");
        sb.append("Category ID: ").append(CatId).append("\n");
        if (this.getClass().equals(SubCategory.class)) {
            SubCategory sub = (SubCategory) this;
            sb.append("Upper Category: ").append(sub.getUpperCat()).append("\n");
        }
        sb.append("Discount: ").append(discount).append("%\n");
        if (!getCategoryItems().isEmpty()) {
            sb.append("Items in Category: \n");

            for (ItemSpecs item : CategoryItems) {
                sb.append("  - ").append(item.toString()).append("\n");
            }
        }

        if (!subCat.isEmpty()) {
            sb.append("Subcategories: \n");
            for (Category subCategory : subCat) {
                sb.append("  - ").append(subCategory.GetCategoryName()).append("\n");
            }
        }

        return sb.toString();
    }

}
