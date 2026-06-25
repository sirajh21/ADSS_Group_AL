package Backend.ServiceLayer.Backend.ServiceLayer;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Backend.DataAccessLayer.*;


public class FactoryService {
     private CategoryDAO categoryDAO;
    private DiscountCategoryDAO discountCATDAO;
    private DiscountProductDAO discountPRODAO;

    private ItemDAO itemDAO;
    private ReportDAO reportDAO;

    private Product1DAO productDAO;

    CategoryService categoryService = new CategoryService();
    DiscountService discountService = new DiscountService();
    ProductService productService = new ProductService();
    ReportService reportService = new ReportService(productService);
    
 
    

    public FactoryService() {
        categoryDAO = CategoryDAO.getInstance();
        reportDAO=ReportDAO.getInstance();
        discountCATDAO = DiscountCategoryDAO.getInstance();
        discountPRODAO = DiscountProductDAO.getInstance();
        itemDAO = ItemDAO.getInstance();
        productDAO = Product1DAO.getInstance();
        productService.productsController.sett(categoryService.categoryController);
    }
    public CategoryService getCategoryService() {
        return categoryService;
    }

    public DiscountService getDiscountService() {
        return discountService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public String addCategory( String name, int parentCategoryID) {
        return categoryService.addCategory( name, parentCategoryID);
    }

    public String deleteCategory( int catID) {
        return categoryService.deleteCategory( catID);
    }

    public String addSubCategory( int categoryID, int subcategoryID) {
        return categoryService.addSubCategory( categoryID, subcategoryID);
    }

    public String addFatherCategory( int categoryID, int fatherCategoryID) {
        return categoryService.addFatherCategory( categoryID, fatherCategoryID);
    }

    public String changeCategoryName( int categoryID, String newName) {
        return categoryService.changeCategoryName( categoryID, newName);
    }
    public String removeStoreItem( int productID,LocalDate eexDate,int amount){
       return productService.removeStoreItem(productID, eexDate, amount);
    }
    public String removeStorageItem( int productID,LocalDate eexDate,int amount) {
return productService.removeStorageItem(productID, eexDate, amount);
    }



    public String buildProductDiscount( int product, double percent, LocalDate start,
                                        LocalDate end) {
        return discountService.buildProductDiscount(productService.productsController.returnProduct(product), percent, start, end);
    }

    public String buildCategoryDiscount( int category, double percent, LocalDate start,
                                         LocalDate end) {
        return discountService.buildCategoryDiscount( categoryService.getCategory(category), percent, start, end);
    }
    public String returnCategories( ) {
        return categoryService.returnCategories1();

    }
    public String getDefectiveItemsByStore(List<Integer> cats ) {
        return reportService.getDefectiveItemsByStore(cats);
    }

    public String getProductsAndAmount( List<Integer> categories) {
        return reportService.getProductsAndAmount(categories);
    }

    public String getDamagedItemReportsByStore(List<Integer> cats) {
        return reportService.getDamagedItemReportsByStore(cats);
    }

    public String getExpiredItemReportsByStore(List<Integer> cats) {
        return reportService.getExpiredItemReportsByStore(cats);
    }

    public String getMinReport(List<Integer> cats) {
        return reportService.getMinReport(cats);
    }
    public String getactiveCategoriesDiscounts() {
        return discountService.getactiveCategoriesDiscounts();
    } public String getactiveProductsDiscounts() {
        return discountService.getactiveProductsDiscounts();
    }

    public String addProductToStore(int barcode,String name, int minAmount, double sellingPrice, double manufacturePrice,
                                    int shelfNumber, String manufacturer, int category) {
        return productService.addProductToStore(barcode,name, minAmount, sellingPrice, manufacturePrice, shelfNumber,
                manufacturer,categoryService.getCategory(category));
    }

    public String changeProductPrice( int productID, double newPrice) {
        return productService.changeProductPrice( productID, newPrice);
    }

    public String changeProductName( int productID, String newName) {
        return productService.changeProductName( productID, newName);
    }

    public String changeProductMinAmount(int productID, int minAmount) {
        return productService.changeProductMinAmount( productID, minAmount);
    }

    public String addStorageItem( int productID, LocalDate expirationDate,int quantity) {
        return productService.addStorageItem( productID, expirationDate,quantity);
    }

    public String addStoreItem( LocalDate expirationDate, int productID,int quantity) {
        return productService.addStoreItem(expirationDate, productID,quantity);
    }
    public String returnInformationProducts(){
        return productService.returnInformationProducts();
    }
    public String returnInformationProduct(int productid){
        return productService.returnInformationProduct(productid);
    }


    public String moveProductToCategory( int productID, int newCategoryID) {
        return productService.moveProductToCategory( productID,categoryService.getCategory(newCategoryID));
    }
    

    public String addDamfromstore(LocalDate exp,int productId,int amount) {
        return productService.addDamfromstore( productId,exp,amount);
    }
    public String addDamfromstorage(LocalDate exp,int productId,int amount) {
        return productService.addDamfromstorage( productId,exp,amount);
    }
    public  void removeData() {
    try {
        ItemDAO.getInstance().deleteData();
   
    ProductDAO.getInstance().deleteData();
    CategoryDAO.getInstance().deleteData();
    DiscountCategoryDAO.getInstance().deleteData();
    DiscountProductDAO.getInstance().deleteData();
    ReportDAO.getInstance().deleteData();

} catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}

}
public  void LoadData() {
    try {
   categoryService.loadata();
    ProductDAO.getInstance().LoadData();
   // DiscountCategoryDAO.getInstance().LoadData();
    DiscountProductDAO.getInstance().LoadData();
    ItemDAO.getInstance().LoadData();
    ReportDAO.getInstance().LoadData();

} catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}

}

    public void displayCategoryHierarchy(String catName) {
        categoryService.displayCategoryHierarchy(catName);
    }

}