package Backend.ServiceLayer.Backend.ServiceLayer;



import java.sql.SQLException;
import java.util.List;

import Backend.DataAccessLayer.*;
import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;

public class CategoryService {
    public Categorycontroller categoryController =Categorycontroller.getInstance() ;

    public CategoryService() {
    }
    //
    public Category getCategory( int categoryID) {
        try {

            return categoryController.returnCategory( categoryID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public String returnCategories1() {
        try {
            String output= categoryController.returnCategories1();
            System.out.println(output);

            return output;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String addCategory(String name, Integer parentCategoryID) {
        try {
            String output= categoryController.buildCategory(name, null, null,
                    categoryController.returnCategory( parentCategoryID) );
           // System.out.println(output);

            return output;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String deleteCategory( int categoryID) {
        try {
            String output= categoryController.removeCategory(categoryID);
            System.out.println(output);

            return output;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public  List<Category> returnCategories( ) {
        try {
            return categoryController.returnCategories();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return null;
        }
    }
    //
    public String addSubCategory( int categoryID, int subCategoryID) {
        try {
            String output= categoryController.addSubCategory( categoryID, subCategoryID);
            System.out.println(output);

            return output;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String addFatherCategory( int categoryID, int fatherCategoryID) {
        try {
            String output= categoryController.setFatherCategory( categoryID, fatherCategoryID);
            System.out.println(output);

            return output;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String changeCategoryName( int categoryID, String newName) {
        try {
            String output= categoryController.changeCategoryName( categoryID, newName);
            System.out.println(output);
            return output;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }


    public void displayCategoryHierarchy(String categoryName) {
        try {
            categoryController.displayCategoryHierarchyaa(categoryName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void loadata(){
try {
    CategoryDAO.getInstance().LoadData();
} catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
    }
}
