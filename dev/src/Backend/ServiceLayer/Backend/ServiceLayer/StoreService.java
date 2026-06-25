package Backend.ServiceLayer.Backend.ServiceLayer;

import java.util.HashMap;


public class StoreService {
    private static StoreService instance = new StoreService();
    private HashMap<Integer, FactoryService> factories;


    private StoreService() {
        factories = new HashMap<Integer, FactoryService>();


        for (int i = 1; i <= 1; i++) {
            factories.put(i, new FactoryService());
        }
        for (int storeId = 1; storeId <= 1; storeId++) {
            // int counter=1;
            // Category category0=new Category("Dairy", counter, null);
            // factories.get(storeId).getCategoryService().categoryController.getCategories().put(counter, category0);
            // counter++;
            // Category category1=new Category("Milk", counter, category0);
            // category1.setFatherCategory(category0);
            // category0.addSubCategory(category1);
            // factories.get(storeId).getCategoryService().categoryController.getCategories().put(counter, category1);
            // counter++;
            // Category category2=new Category("1.5L", counter,category1);
            // factories.get(storeId).getCategoryService().categoryController.getCategories().put(counter, category2);
            // category2.setFatherCategory(category1);
            // category1.addSubCategory(category2);
            // counter++;
            // Category category3=new Category("Bathroom products", counter, null);
            // factories.get(storeId).getCategoryService().categoryController.  getCategories().put(counter, category3);
            // counter++;
            // Category category4=new Category("Shampoo", counter, category3);
            // factories.get(storeId).getCategoryService().categoryController. getCategories().put(counter, category4);
            // counter++;
            // category4.setFatherCategory(category3);
            // category3.addSubCategory(category4);
            // Category category5=new Category("250ml", counter,category4);
            // factories.get(storeId).getCategoryService().categoryController.getCategories().put(counter, category5);
            // counter++;
            // category5.setFatherCategory(category4);
            // category4.addSubCategory(category5);
            // factories.get(storeId).getCategoryService().categoryController.setCounter(counter);
            // Product product0=new Product(1, "Milk3%", 3, 7, 5, 1, "Tnuva",   factories.get(storeId).categoryService.getCategory(1));
            // product0.setCategory(category0);
            //Item item=new Item(1, LocalDate.now().minusDays(1));
            //Item item1=new Item(1, LocalDate.now().minusDays(1));
            //Item item2=new Item(1, LocalDate.now().minusDays(1));
            //product0.addStoreItem(LocalDate.now().minusDays(1), item);
           // product0.addStoreItem(LocalDate.now().minusDays(1), item1);
         //   product0.addStoreItem(LocalDate.now().minusDays(1), item2);
          //  factories.get(storeId).productService.productsController.getProducts().put(1, product0);

          //  Product product1=new Product(2, "Shampoo 250ml", 3, 15, 10, 2, "Pinuk",   factories.get(storeId).categoryService.getCategory(2));
        //    factories.get(storeId).productService.productsController.getProducts().put(2, product1);

// product1.setCategory(category3);
// category0.addProduct(product0);
// category1.addProduct(product0);
// category2.addProduct(product0);

// category3.addProduct(product1);
// category4.addProduct(product1);
// category5.addProduct(product1);
}}

//             factories.get(storeId).productService.productsController.getProducts().put(2, product1);
//             Discount discount0=new Discount(product0,LocalDate.now().minusDays(1),LocalDate.now().minusDays(1),20,1);
//             factories.get(storeId).getDiscountService().discountController.getProductDiscounts().put(1, discount0);
//             Discount discount1=new Discount(product1,LocalDate.now(),LocalDate.now().plusDays(1),20,1);
//             factories.get(storeId).getDiscountService().discountController.getProductDiscounts().put(2, discount1);
//             product1.startDiscount(discount0.getPercent());
//             product0.startDiscount(discount1.getPercent());
//             factories.get(storeId).getDiscountService().discountController.setCounter(3);

           
//         }}

    public static StoreService getInstance() {
        return instance;
    }

    public FactoryService pickStore(int storeId) {

     

        return factories.get(storeId);
    }


}