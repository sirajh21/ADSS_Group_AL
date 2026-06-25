package Backend.ServiceLayer.Backend.ServiceLayer;


import java.time.LocalDate;
import java.util.List;


import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;

public class ProductService {
    public productscontroller productsController = productscontroller.getInstance();


    public productscontroller getProductsController() {
        return productsController;
        
    }

    public ProductService() {
        
    }

    public Product getProduct( int productId) {
        try {
            Product output = productsController.returnProduct( productId);
            return output;
        } catch (Exception e) {
            return null;
        }
    }
    //
    public List<Product> getProducts() {
        try {
            return productsController.returnProducts();
        } catch (Exception e) {
            return null;
        }
    }
    //
    public String addProductToStore(int barcode,String name, int minAmount, double sellingPrice, double manufacturingPrice,
                                    int shelfNumber, String manufacturer, Category category ) {
        String output = "{}";
        try {
            output = productsController.buildProduct(barcode, name, minAmount, sellingPrice, manufacturingPrice,
                    shelfNumber, manufacturer, category);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String changeProductPrice( int productId, double newPrice) {
        String output = "{}";
        try {
            output = productsController.changeProductPrice( productId, newPrice);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String changeProductName( int productId, String newName) {
        String output = "{}";
        try {
            output = productsController.changeProductName( productId, newName);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String returnInformationProducts(){
        String output = "{}";
        try {
            output= productsController.returnInformationProducts();
            System.out.println(output);
            return output;
        } catch (Exception e) {
            return e.getMessage();

        }
    }
    public String returnInformationProduct(int productid){
        String output = "{}";
        try {
            output= productsController.returnInformationProduct(productid);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println( e.getMessage());
            return e.getMessage();

        }
    }
    //
    public String changeProductMinAmount( int productId, int min) {
        String output = "{}";
        try {
            output = productsController.changeProductMinAmount( productId, min);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    //
    public String addStorageItem( int productId, LocalDate expirationDate,int quantity) {
        String output = "{}";
        try {
            output = productsController.addStorageItem( productId, expirationDate,quantity);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();        }
    }
    //
    public String addStoreItem( LocalDate expirationDate, int productId,int quantity) {
        String output = "{}";
        try {
            output = productsController.addStoreItem(  productId,expirationDate,quantity);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();        }
    }

    // //
    public void checkDates() {
        try {
            productsController.checkExpiryDates();
        } catch (Exception e) {
            //nkml,;
        }
    }

    public String moveProductToCategory( int productId, Category newCategoryId) {
        String output = "{}";
        try {
            output = productsController.moveProductToCategory( productId, newCategoryId);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    public String removeStoreItem(int productID, LocalDate eexDate, int amount) {
        String output = "{}";
        try {
            output = productsController.removeStoreItem(productID, eexDate, amount);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    public String removeStorageItem(int productID, LocalDate eexDate, int amount) {
        String output = "{}";
        try {
            output = productsController.removeStorageItem(productID, eexDate, amount);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();            }
    }
   
    public String addDamfromstore(int id, LocalDate eexDate, int amount) {
        String output = "{}";
        try {
             output = productsController.addDamfromstore(eexDate, id, amount);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();            }
    }
    public String addDamfromstorage(int id, LocalDate eexDate, int amount) {
        String output = "{}";
        try {
            output = productsController.addDamfromstorage(eexDate, id, amount);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();            }
    }



}
        
    
        
