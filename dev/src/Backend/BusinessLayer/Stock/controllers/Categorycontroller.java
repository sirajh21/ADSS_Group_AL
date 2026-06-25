package Backend.BusinessLayer.Stock.controllers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Backend.DataAccessLayer.*;
import Backend.BusinessLayer.Stock.objects.*;



public class Categorycontroller {
    private int counter = 1;
    private static Categorycontroller instance = null;
    public HashMap<Integer, Category> categories;

    private Categorycontroller() {
        categories = new HashMap<>();
    }

    public static synchronized Categorycontroller getInstance() {
        if (instance == null) {
            instance = new Categorycontroller();
        }
        return instance;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public HashMap<Integer, Category> getCategories() {
        return categories;
    }

    public String buildCategory(String name, List<Product> products, List<Category> subCategories,
                                Category fatherCategory) throws SQLException {
        Category category = new Category(name, counter, fatherCategory);
        categories.put(counter, category);

        if (fatherCategory != null) {
            addSubCategory(fatherCategory.getId(), category.getId());
        }

        int tmp = counter - 1;
        CategoryDTO c = new CategoryDTO(name, (fatherCategory != null) ? fatherCategory.getName() : "0", counter);
        CategoryDAO.getInstance().insert(c);
        counter++;

        return "Category: " + tmp + " was added successfully";
    }

    public void addDto(CategoryDTO categoryDTO) throws SQLException {
        Category c=null;
        for (Category ce : categories.values()) 
        {
            if(ce.getName().equals(categoryDTO.getFaString())){
          c=ce;
            }      
        }
        Category category = new Category(categoryDTO.getName(), categoryDTO.getID(),
                c);
        if (category.getFatherCategory() != null) {
            category.getFatherCategory().addSubCategory(category);
        }
        categories.put(category.getId(), category);
        counter ++;
    }

    public String removeCategory(int categoryID) throws SQLException {
        Category categoryToRemove = returnCategory(categoryID);
        if (categoryToRemove == null) {
            throw new IllegalArgumentException("There is no category with ID " + categoryID);
        }

        if (!categoryToRemove.getSubCategories().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete a category that has subcategories");
        }

        if (!categoryToRemove.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete a category that has products still assigned to it");
        }

        categoryToRemove.setFatherCategory(null);
        categories.remove(categoryID);

        CategoryDAO.getInstance().deleteCategory(Integer.toString(categoryID));

        return "Category removed successfully";
    }

    public Category returnCategory(Integer id) {
        if (id == 0) {
            return null;
        } else if (categories.containsKey(id)) {
            return categories.get(id);
        } else {
            throw new IllegalArgumentException("No such category");
        }
    }

    public boolean returnCategory1(Integer id) {
        return categories.containsKey(id);
    }

    public List<Category> returnCategories() {
        return new LinkedList<>(categories.values());
    }

    public String returnCategories1() {
        StringBuilder out = new StringBuilder();
        for (Category category : categories.values()) {
            List<String> subs = new LinkedList<>();
            List<String> products = new LinkedList<>();

            if (category.getFatherCategory() != null) {
                for (Category cat : category.getSubCategories()) {
                    subs.add(cat.getName());
                }
                for (Product pro : category.getProducts()) {
                    products.add(pro.getName());
                }
                out.append("Category name: ").append(category.getName())
                   .append(" categoryID: ").append(category.getId())
                   .append(" subCategories: ").append(subs)
                   .append(" Father category: ").append(category.getFatherCategory().getName())
                   .append(" Products: ").append(products).append("\n\n");
            } else {
                for (Category cat : category.getSubCategories()) {
                    subs.add(cat.getName());
                }
                for (Product pro : category.getProducts()) {
                    products.add(pro.getName());
                }
                out.append("Category name: ").append(category.getName())
                   .append(" categoryID: ").append(category.getId())
                   .append(" subCategories: ").append(subs)
                   .append(" Father category: no father category ")
                   .append(" Products: ").append(products).append(" \n\n");
            }
        }
        return out.toString();
    }

    public String addSubCategory(int categoryID, int subCategoryID) throws SQLException {
        if (categories.containsKey(subCategoryID)) {
            Category subCategory = returnCategory(subCategoryID);
            if (categories.containsKey(categoryID)) {
                categories.get(categoryID).addSubCategory(subCategory);
                categories.get(subCategoryID).setFatherCategory(categories.get(categoryID));
                CategoryDAO.getInstance().changeCategoryFather(Integer.toString(categoryID), Integer.toString(subCategoryID));
                return "Subcategory added successfully";
            } else {
                throw new IllegalArgumentException("No such category");
            }
        } else {
            throw new IllegalArgumentException("No such category");
        }
    }

    public String setFatherCategory(int categoryID, int fatherCategoryID) throws SQLException {
        Category category = returnCategory(categoryID);
        Category fatherCategory = returnCategory(fatherCategoryID);
        if (category == null || fatherCategory == null) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        if (category.equals(fatherCategory)) {
            throw new IllegalArgumentException("A category cannot be its own father category");
        }
        category.setFatherCategory(fatherCategory);
        fatherCategory.addSubCategory(category);
        CategoryDAO.getInstance().changeCategoryFather(Integer.toString(fatherCategoryID), Integer.toString(categoryID));

        return "Father category set successfully";
    }

    public String changeCategoryName(int categoryID, String newName) throws SQLException {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("No categories found: ");
        }
        Category category = returnCategory(categoryID);
        category.setName(newName);
        CategoryDAO.getInstance().changeCategoryName(newName, Integer.toString(categoryID));
        return "Category name changed successfully.";
    }


    public Integer getCategoryIDByName(String name) {
        for (Category category : categories.values()) {
            if (category.getName().equals(name)) {
                return category.getId();
            }
        }
        return null; // or throw an exception if the category is not found
    }

    public Category getCategoryByName(String name) {
        for (Category cat : returnCategories()) {
            if (cat.getName().equalsIgnoreCase(name)) {
                return cat;
            }
        }
        return null;
    }

    public void displayCategoryHierarchyaa(String categoryName) {
        Category category = getCategoryByName(categoryName);
        if (category == null) {
            System.out.println("Category not found.");
            return;
        }

        System.out.println("Ctegory: " + category.getName());
        System.out.println("ID: " + category.getId());
        System.out.println("Products: " + category.getProducts());

        Category father = category.getFatherCategory();
        if (father != null) {
            System.out.println("Father category: " + father.getName());
        } else {
            System.out.println("No father category");
        }

        System.out.println("Subcategories:");
        for (Category sub : category.getSubCategories()) {
            System.out.println(" - " + sub.getName());
        }
    }
}
