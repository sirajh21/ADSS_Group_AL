package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import Backend.ServiceLayer.Backend.ServiceLayer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;


class FactoryServiceTest {
private FactoryService factoryService;

   Service sc;
   Product1 p1;
   Product1 p2;
   Product1 p3;
   ArrayList<String> companies;
   Supplier supplier;
@BeforeEach
void setUp() {
    factoryService = new FactoryService();
           sc = new Service();
      // sc.deleteAllsuplier();
       sc.AddProduct(8,"milk",22);
       sc.AddProduct(12,"cola",11);
       sc.AddProduct(15,"shoko",00);
       p1 = sc.getAllProducts().get(1);
       p2 = sc.getAllProducts().get(2);
       p3 = sc.getAllProducts().get(3);
       companies = new ArrayList<>();
       companies.add("Tnova");
       companies.add("coca cola");
       sc.AddSupplier( 1,"aseel", "0512345678"
               , 1, "111", "Cash", companies);
       supplier = sc.getSupplier(1);
}
@AfterEach
void tearDown() {
  factoryService.getCategoryService().categoryController.categories.clear();
  factoryService.getProductService().productsController.products.clear();
  factoryService.getDiscountService().discountController.categoryDiscounts.clear();
  factoryService.getDiscountService().discountController.productDiscounts.clear();
  factoryService.getCategoryService().categoryController.setCounter(1);
  ReportController.getInstance().reports.clear();

  ReportController.getInstance().reportCounter=1;
  sc.deleteAllsuplier();
  sc.getSuppliersController().suppliers.clear();
  sc.getSuppliersController().suppliersById.clear();
  sc.getSuppliersController().productsById.clear();
  sc.getOrderController().orders.clear();
  sc.getOrderController().periodicOrders.clear();
  sc.getOrderController().orders_id.clear();
  sc.getOrderController().orders_periodic_id.clear();

}
@org.junit.jupiter.api.Test
void addCategory() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");

    Category category= factoryService.getCategoryService().returnCategories().get(0);
    assertEquals("Milk", category.getName(), "Adding category failed" );
    assertNull(category.getFatherCategory(), "Adding category failed");

}



@org.junit.jupiter.api.Test
void deleteCategory() {
   assertEquals(0, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");

    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.deleteCategory(1);
    assertEquals(0, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");


}




@org.junit.jupiter.api.Test
void changeCategoryName() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");

    Category category= factoryService.getCategoryService().returnCategories().get(0);
    assertEquals("Milk", category.getName(), "Adding category failed" );
    factoryService.changeCategoryName(1, "3% Milk");
    assertEquals("3% Milk", category.getName(), "Adding category failed" );

}
 @org.junit.jupiter.api.Test
void addProductToStore() {

    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");

    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    assertEquals(1, factoryService.getProductService().getProducts().size(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    assertEquals("milk", factoryService.getProductService().getProduct(1).getName(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getMinAmount(), "Adding Product failed");
    assertEquals(7, factoryService.getProductService().getProduct(1).getManufacturePrice(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getShelfNumber(), "Adding Product failed");
    assertEquals("Strauss", factoryService.getProductService().getProduct(1).getManufacturer(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getCategory().getId(), "Adding Product failed");




}

@org.junit.jupiter.api.Test
void buildProductDiscount() {

    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");

    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    assertEquals(1, factoryService.getProductService().getProducts().size(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    factoryService.buildProductDiscount(1, 20, LocalDate.now(), LocalDate.now());
    assertEquals(8, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    assertEquals(1, factoryService.getDiscountService().geDiscounts().size(), "Adding Product failed");



}

@org.junit.jupiter.api.Test
void buildCategoryDiscount() {

    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");

    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    assertEquals(1, factoryService.getProductService().getProducts().size(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    factoryService.buildProductDiscount(1, 20, LocalDate.now(), LocalDate.now());
    assertEquals(8, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    assertEquals(1, factoryService.getDiscountService().geDiscounts().size(), "Adding Product failed");
}


@org.junit.jupiter.api.Test
void addStorageItem() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    factoryService.addStorageItem(1, LocalDate.now().plusDays(1), 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getStorageAmount(), "Adding Items failed");


}

@org.junit.jupiter.api.Test
void addStoreItem() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    factoryService.addStoreItem(LocalDate.now().plusDays(1),1, 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getStoreAmount(), "Adding Items failed");
}





@org.junit.jupiter.api.Test
void changeProductPrice() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");

    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    assertEquals(1, factoryService.getProductService().getProducts().size(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    assertEquals("milk", factoryService.getProductService().getProduct(1).getName(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getMinAmount(), "Adding Product failed");
    assertEquals(7, factoryService.getProductService().getProduct(1).getManufacturePrice(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getShelfNumber(), "Adding Product failed");
    assertEquals("Strauss", factoryService.getProductService().getProduct(1).getManufacturer(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getCategory().getId(), "Adding Product failed");
factoryService.changeProductPrice(1, 12);
assertEquals(12, factoryService.getProductService().getProduct(1).getSellingPrice(), "Changing the product price failed");

}

@org.junit.jupiter.api.Test
void changeProductName() {
    factoryService.addCategory("Milk", 0);

    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    assertEquals(1, factoryService.getProductService().getProducts().size(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    assertEquals("milk", factoryService.getProductService().getProduct(1).getName(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getMinAmount(), "Adding Product failed");
    assertEquals(7, factoryService.getProductService().getProduct(1).getManufacturePrice(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getShelfNumber(), "Adding Product failed");
    assertEquals("Strauss", factoryService.getProductService().getProduct(1).getManufacturer(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getCategory().getId(), "Adding Product failed");
factoryService.changeProductName(1, "Milk");
assertEquals("Milk", factoryService.getProductService().getProduct(1).getName(), "Changing the product name failed");
}

@org.junit.jupiter.api.Test
void changeProductMinAmount() {
    factoryService.addCategory("Milk", 0);

    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    assertEquals(1, factoryService.getProductService().getProducts().size(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getSellingPrice(), "Adding Product failed");
    assertEquals("milk", factoryService.getProductService().getProduct(1).getName(), "Adding Product failed");
    assertEquals(10, factoryService.getProductService().getProduct(1).getMinAmount(), "Adding Product failed");
    assertEquals(7, factoryService.getProductService().getProduct(1).getManufacturePrice(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getShelfNumber(), "Adding Product failed");
    assertEquals("Strauss", factoryService.getProductService().getProduct(1).getManufacturer(), "Adding Product failed");
    assertEquals(1, factoryService.getProductService().getProduct(1).getCategory().getId(), "Adding Product failed");
factoryService.changeProductMinAmount(1, 5);
assertEquals(5, factoryService.getProductService().getProduct(1).getMinAmount(), "Changing the product MinAmount failed");
}




@org.junit.jupiter.api.Test
void removeStoreItem() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    factoryService.addStoreItem(LocalDate.now().plusDays(1),1, 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getStoreAmount(), "Adding Items failed");
    factoryService.removeStoreItem(1,LocalDate.now().plusDays(1), 10);
    assertEquals(0, factoryService.getProductService().getProduct(1).getStoreAmount(), "Adding Items failed");


}
@org.junit.jupiter.api.Test
void removeStorageItem() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    factoryService.addStorageItem(1,LocalDate.now().plusDays(1), 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getStorageAmount(), "Adding Items failed");
    factoryService.removeStorageItem(1,LocalDate.now().plusDays(1), 10);
    assertEquals(0, factoryService.getProductService().getProduct(1).getStorageAmount(), "Adding Items failed");


}
@org.junit.jupiter.api.Test
void adddmgstoreItem() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    factoryService.addStoreItem(LocalDate.now().plusDays(1),1, 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getStoreAmount(), "Adding Items failed");
    factoryService.addDamfromstore(LocalDate.now().plusDays(1),1, 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getDamagedamount(), "Adding Items failed");


}
@org.junit.jupiter.api.Test
void adddmgstorageItem() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    factoryService.addStorageItem(1,LocalDate.now().plusDays(1), 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getStorageAmount(), "Adding Items failed");
    factoryService.addDamfromstorage(LocalDate.now().plusDays(1),1, 10);
    assertEquals(10, factoryService.getProductService().getProduct(1).getDamagedamount(), "Adding Items failed");


}
@org.junit.jupiter.api.Test
void makereport() {
    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    try {
      try {
        productscontroller.getInstance().Alert();
    } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
  }
  assertEquals(1, ReportController.getInstance().reports.size(), "Adding category failed");

}
@org.junit.jupiter.api.Test
void reciveorder() throws SQLException {

    factoryService.addCategory("Milk", 0);
    assertEquals(1, factoryService.getCategoryService().returnCategories().size(), "Adding category failed");
    factoryService.addProductToStore(1,"milk", 10, 10, 7, 1, "Strauss", 1);
    try {
      try {
        productscontroller.getInstance().Alert();
    } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
  }
    assertEquals(1, ReportController.getInstance().reports.size(), "Adding category failed");
    productscontroller.getInstance().receiveorder(1);
    assertEquals(20, factoryService.getProductService().getProduct(1).getFullAmount(), "Adding Items failed");

}
 @org.junit.jupiter.api.Test

   public void addSupplierEmptyName() {
       try {
           sc.AddSupplier( 7,"", "055555555"
                   , 1, "10256", "doar", companies);
       } catch (Exception e) {
           assertEquals(e.getMessage(), "name cannot be empty");
       }
   }

   @org.junit.jupiter.api.Test
   public void addSupplierEmptyCompanies() {
       try {
           sc.AddSupplier( 66,"sam", "055555555"
                   , 1, "10256", "doar", new ArrayList<>());
       } catch (Exception e) {
           assertEquals(e.getMessage(), "Companies cannot be empty");
       }
   }

   @org.junit.jupiter.api.Test

   public void addSupplierUnavailableType() {
       try {
           sc.AddSupplier( 88,"sam", "055555555"
                   , 4, "10256", "doar", companies);
       } catch (Exception e) {
           assertEquals(e.getMessage(), "Supplier type must be 1 or 2 or 3");
       }
   }

   @org.junit.jupiter.api.Test

   public void deleteSupplier() {
       try{
           sc.DeleteSupplier(1);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

   @org.junit.jupiter.api.Test

   public void deleteUnExistedSupplier() {
       try{
           sc.DeleteSupplier(2);
       }catch (Exception e){
           assertEquals("The supplier not found" ,e.getMessage());
       }
   }


   @org.junit.jupiter.api.Test

   public void makeContractEmptyQuantityDiscount() {
       try{
           sc.makeContract(1,"123",true,new HashMap<>());
       }catch (Exception e){
           assertEquals(e.getMessage(),"Data empty");
       }
   }
   @org.junit.jupiter.api.Test

   public void makeContractEmptyQuantityDiscount1() {
       try{
           sc.makeContract(1,"123",true,new HashMap<>());
       }catch (Exception e){
           assertEquals(e.getMessage(),"Data empty");
       }
   }

   @org.junit.jupiter.api.Test
   public void makeContract(){
       try{
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(100,10.0);
           quantity_discount.put(500,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           quantity_discount_product.put(sc.getAllProducts().get(1),quantity_discount);
           sc.makeContract(1,"213",true,quantity_discount_product);
           assertEquals(sc.getSupplier(1).getContracts().get(0).getContract_name(),"213");
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

   @org.junit.jupiter.api.Test
   public void makeContract1(){
       try{
           companies = new ArrayList<>();
           companies.add("Tnova");
           companies.add("coca cola");
           sc.AddSupplier( 00,"aseel2", "0512345678"
                   , 1, "111", "Cash", companies);
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(10,10.0);
           quantity_discount.put(50,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           quantity_discount_product.put(sc.getAllProducts().get(1),quantity_discount);
           sc.makeContract(2,"12",true,quantity_discount_product);
           assertEquals(sc.getSupplier(2).getContracts().get(0).getContract_name(),"12");
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

   @org.junit.jupiter.api.Test
   public void deleteContract1(){
       try{
           companies = new ArrayList<>();
           companies.add("Tnova");
           companies.add("coca cola");
           sc.AddSupplier( 88,"aseel2", "0512345678"
                   , 1, "111", "Cash", companies);
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(100,10.0);
           quantity_discount.put(500,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           quantity_discount_product.put(sc.getAllProducts().get(1),quantity_discount);
           sc.makeContract(2,"12",true,quantity_discount_product);
           sc.deleteContract("12");
           int contQuentity = supplier.getContracts().size();
           assertNull(supplier.getContractByName("12"));
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }
   @org.junit.jupiter.api.Test
   public void deleteContract(){
       try{
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(100,10.0);
           quantity_discount.put(500,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           quantity_discount_product.put(sc.getAllProducts().get(1),quantity_discount);
           sc.makeContract(1,"213",true,quantity_discount_product);
           sc.deleteContract("213");
           int contQuentity = supplier.getContracts().size();
           assertNull(supplier.getContractByName("213"));
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

   @org.junit.jupiter.api.Test
   public void makeContractFail(){
       try{
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(100,10.0);
           quantity_discount.put(500,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           quantity_discount_product.put(sc.getAllProducts().get(4),quantity_discount);
           sc.makeContract(1,"213",true,quantity_discount_product);
       }catch (Exception e){
           assertEquals(e.getMessage(),"Product not found");
       }
   }

   @org.junit.jupiter.api.Test
   public void addOrder() {
       try {
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(100,10.0);
           quantity_discount.put(500,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           Product1 p= sc.getAllProducts().get(1);
           quantity_discount_product.put(p,quantity_discount);
           sc.makeContract(1,"213",true,quantity_discount_product);
           HashMap<Integer,Integer> quantity = new HashMap<>();
           quantity.put(1,50);
           ArrayList<Integer> productsList=new ArrayList<>();
           productsList.add(1);
           sc.AddOrder( 1,productsList,quantity, LocalDate.of(2024,5,18)
           );
           assertEquals(sc.getOrders().get(sc.getSupplier(1)).size(),1);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }

   }



   @org.junit.jupiter.api.Test
   public void addOrderFail() {
       try {
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(100,10.0);
           quantity_discount.put(500,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           Product1 p= sc.getAllProducts().get(1);
           quantity_discount_product.put(p,quantity_discount);
           sc.makeContract(1,"213",true,quantity_discount_product);
           HashMap<Integer,Integer> quantity = new HashMap<>();
           quantity.put(1,50);
           ArrayList<Integer> productsList=new ArrayList<>();
           productsList.add(1);
           sc.AddOrder(4, productsList,quantity,LocalDate.of(2024,5,18));
       }catch (Exception e){
           assertEquals(e.getMessage(), "No supplier for this order" );
       }

   }

   @org.junit.jupiter.api.Test
   public void CancelOrder() {
       try {
           HashMap<Integer,Double> quantity_discount = new HashMap<>();
           quantity_discount.put(100,10.0);
           quantity_discount.put(500,20.0);
           HashMap<Product1,HashMap<Integer,Double>> quantity_discount_product = new HashMap<>();
           Product1 p= sc.getAllProducts().get(1);
           quantity_discount_product.put(p,quantity_discount);
           sc.makeContract(1,"213",true,quantity_discount_product);
           HashMap<Integer,Integer> quantity = new HashMap<>();
           quantity.put(1,50);
           ArrayList<Integer> productsList=new ArrayList<>();
           productsList.add(1);
           sc.AddOrder(99, productsList,quantity, LocalDate.of(2024,5,18));
           Order o=sc.getIdOrders().get(1);
           sc.DeleteOrder(1, o);
           assertEquals(sc.getOrders().get(sc.getSupplier(1)).size(),0);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }

   }


   @org.junit.jupiter.api.Test
   public void addContact() {
       try{
           sc.AddContact(1,213456,"jerry","0511111", "jerry@gmail.com");
           assertEquals(supplier.getContacts().size(),1);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

   @org.junit.jupiter.api.Test
   public void addContact1() {
       try{
           sc.AddContact(1,2222,"jaw","051", "jawy@gmail.com");
           sc.AddContact(1,3333,"jaw1","0511", "jawy1@gmail.com");

           assertEquals(supplier.getContacts().size(),2);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

   @org.junit.jupiter.api.Test
   public void addContactFail() {
       try{
           sc.AddContact(0,213456,"jerry","0511111", "jerry@gmail.com");
       }catch (Exception e){
           assertEquals(e.getMessage(), "Data contact cannot be empty");
       }
   }

   @org.junit.jupiter.api.Test
   public void addCompany() {
       try {
           int companies_size = supplier.getCompanies().size();
           sc.AddCompany(1,"Aosem");
           assertEquals(supplier.getCompanies().size(),companies_size+1);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

   @org.junit.jupiter.api.Test
   public void addCompany1() {
       try {
           int companies_size = supplier.getCompanies().size();
           sc.AddCompany(1,"h");
           assertEquals(supplier.getCompanies().size(),companies_size+1);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
   }

//
}