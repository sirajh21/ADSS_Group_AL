package Backend.BusinessLayer.Stock.objects;

import java.util.LinkedList;
import java.util.List;

public class Category {
    private String name;
    private int id;
    private List<Product> products;
    private List<Category> subCategories;
    private Category fatherCategory;

    public Category(String name, int id, Category fatherCategory) {
        this.products = new LinkedList<Product>();
        this.name = name;
        this.id = id;
        this.subCategories = new LinkedList<Category>();
        this.fatherCategory = fatherCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getFatherCategory() {
        return fatherCategory;
    }

    public void setFatherCategory(Category fatherCategory) {
        this.fatherCategory = fatherCategory;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addSubCategory(Category category) {
        this.subCategories.add(category);
    }
}
