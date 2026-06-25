package Backend.BusinessLayer.Stock.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Backend.DataAccessLayer.*;
import Backend.BusinessLayer.Stock.objects.*;

public class productscontroller  {

    private static productscontroller instance = null;
    private static Categorycontroller instance1 ;

    private static ReportController reports = ReportController.getInstance();

    public HashMap<Integer, Product> products;
    




    public HashMap<Integer, Product> getProducts() {
        return products;
    }


    public productscontroller() {
        products = new HashMap<Integer, Product>();
        

    }
    public static productscontroller getInstance() {
        if (instance == null) {
            instance = new productscontroller();
            }
            return instance;
    }
    public void ADDdtoproduct(ProductDTO productDTO) throws SQLException {
        Category cat = Categorycontroller.getInstance().returnCategory(Categorycontroller.getInstance().getCategoryIDByName((productDTO.getCategory())));
      Product product= new Product(productDTO.getId(), productDTO.getName(), productDTO.getMinAmount(), productDTO.getSellingPrice(), productDTO.getManufacturePrice(), productDTO.getShelfNumber(), productDTO.getManufacturer(), cat);
      cat.addProduct(product);
      if(cat.getFatherCategory()!=null){
        cat.getFatherCategory().addProduct(product);
      }
      products.put(product.getId(), product);
      
    }
    public void ADDdtoitem(ItemDTO item) {
        LocalDate date = LocalDate.parse(item.getExpDate());
        products.get(item.getId()).addStorageItem(date, item.getStorageamount());
        products.get(item.getId()).addStoreItem(date, item.getStoreamount());
        products.get(item.getId()).getItems().get(date).setDamaged(item.getDamaged());
        products.get(item.getId()).checkExpiryDates();
    }
    public  void sett(Categorycontroller c) {
        instance1=c;
    }
    //
    public String changeProductPrice( int productId, double newPrice) throws SQLException {

        if ( !products.containsKey(productId)) {
            throw new IllegalArgumentException("Product not found in store");
        }
        products.get(productId).setSellingPrice(newPrice);
        ProductDAO.getInstance().changeProductPrice(productId, newPrice);
        return "Product price changed successfully";
    }
    //
    public String changeProductName( int productId, String newName) throws SQLException {

        if ( !products.containsKey(productId)) {
            throw new IllegalArgumentException("Product not found in store");
        }
        Product product = returnProduct( productId);
        product.setName(newName);
        ProductDAO.getInstance().changeProductName(productId, newName);
        return "Product name changed successfully";
    }
    //
    public String buildProduct(int productID,String name, int minAmount, double sellingPrice, double manufacturePrice,
                               int shelfNumber, String manufacture, Category category) throws SQLException {
        if(category==null){
            return "can not add product";
        }
        else if(products.containsKey(productID)){return "product already in the store";}
        else{   Product product = new Product(productID, name, minAmount, sellingPrice, manufacturePrice,
                shelfNumber, manufacture, category);

            products.put(productID, product);
category.addProduct(product);
Category cat1=category;
while(cat1.getFatherCategory()!=null){
    cat1.getFatherCategory().addProduct(product);
cat1=category.getFatherCategory();
}

    ProductDTO p= new ProductDTO(productID, name, minAmount, false, sellingPrice, sellingPrice, manufacturePrice, shelfNumber, manufacture, category.getName());
    ProductDAO.getInstance().insert(p);
            return "Product: " +productID +" was added successfully";}
    }
    //
    public Product returnProduct( int productID) {


        if (products.containsKey(productID)) {
            return products.get(productID);
        } else {
            throw new IllegalArgumentException("Product not found");
        }

    }
    //
    public List<Product> returnProducts() {


        List<Product> productsList = new LinkedList<Product>();

        if (!products.isEmpty()) {
            for (Product product : products.values()) {
                productsList.add(product);
            }
        }

        return productsList;
    }
    //
    public String addStorageItem(int productID, LocalDate expiryDate, int quantity) throws SQLException {

        if (products.containsKey(productID)) {
            if (products.get(productID).getItems().get(expiryDate) == null) {
                try {
                    ItemDTO i = new ItemDTO(expiryDate.toString(), 0, 0, productID, 0, quantity);
                    ItemDAO.getInstance().insert(i);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            } else {
                try {
                    ItemDAO.getInstance().addStorageItem(expiryDate.toString(), productID, quantity);

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            products.get(productID).addStorageItem(expiryDate, quantity);
            try {
                checkExpiryDates();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "Item added to storage successfully";

        } else {
            throw new IllegalArgumentException("Product not found in store");
        }
    }

    //
    public String addStoreItem(int productID, LocalDate expiryDate, int quantity) throws SQLException {

        if (products.containsKey(productID)) {
            if (products.get(productID).getItems().get(expiryDate) == null) {
                try {
                    ItemDTO i = new ItemDTO(expiryDate.toString(), 0, 0, productID, quantity, 0);
                    ItemDAO.getInstance().insert(i);
                } catch (Exception e) {
                }

            } else {
                try {
                    ItemDAO.getInstance().addStoreItem(expiryDate.toString(), productID, quantity);

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            products.get(productID).addStoreItem(expiryDate, quantity);
            try {
                checkExpiryDates();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "Item added to store successfully";

        } else {
            throw new IllegalArgumentException("Product not found in store");
        }
    }
    public void checkExpiryDates() throws SQLException, IllegalAccessException {
        for (Product product : products.values()) {
            product.checkExpiryDates();
        }
        Alert();
    }
    //
    public String changeProductMinAmount( int productID, int minAmount) throws SQLException {



        if (products.containsKey(productID)) {
            products.get(productID).setMinAmount(minAmount);
            ProductDAO.getInstance().changeProductMinAmount(productID, minAmount);

            return "Minimum amount for product changed successfully";
        } else {
            throw new IllegalArgumentException("Product not found in store");
        }
    }

    // //
    public String removeStoreItem(int productID, LocalDate exDate, int amount) throws SQLException, IllegalAccessException {

        if (products.containsKey(productID)) {

            products.get(productID).removeStoreItem(exDate, amount);
            ItemDAO.getInstance().removeStoreItem(exDate.toString(), productID, amount);
            Alert();

            return "Removed item successfully";
        } else {
            throw new IllegalArgumentException("Product not found in store");
        }

    }
    public String removeStorageItem(int productID, LocalDate exDate, int amount) throws SQLException, IllegalAccessException {

        if (products.containsKey(productID)) {

            products.get(productID).removestorageItem(exDate, amount);
            ItemDAO.getInstance().removestorageitem(exDate.toString(), productID, amount);
            Alert();

            return "Removed item successfully";
        } else {
            throw new IllegalArgumentException("Product not found in store");
        }

    }
    // }
   
    public String moveProductToCategory( int productID, Category newCategoryID) {
        if(newCategoryID!=null){
            if (products.containsKey(productID)) {
                products.get(productID).setCategory(newCategoryID);
                return "Product moved to category successfully";
            } else {
                throw new IllegalArgumentException("Product not found in store");
            }}
        else  throw new IllegalArgumentException("category not found in store");

    }

    public void Alert() throws SQLException, IllegalAccessException{
        List<Product> product=returnProducts();
        HashMap<Integer,Integer> p = new HashMap<>();
        LinkedList<Integer> p1=new LinkedList<>();
        for (Product product1 : product) {
            if(product1.getFullAmount()<=product1.getMinAmount() && product1.ordered==false){
                product1.ordered=true;
                p.put(product1.getId(),product1.getMinAmount()*2);
                p1.add(product1.getId());
                if(product1.getFullAmount()==0 ){
                    System.out.println("Product ID: "+product1.getId()+" is out of stock");
                }
            
            else System.out.println("Product ID: "+product1.getId()+" is almost  out of stock current amount: "+product1.getFullAmount()
            );}}
           
            if((p.keySet().size()>=1)){
         Report r=   reports.buildWeeklyReport(p, null, null);
         OrderController.getInstance().AddOrder(r.getReportID(), p1, p,LocalDate.now(), SupplierController.getInstance());

      if(r.getProductsAndAmount().size()!=0){
         for (Integer i : r.getProductsAndAmount().keySet()) {
            ReportDTO rr=new ReportDTO(r.getReportID(), i, p.get(i));
            ReportDAO.getInstance().insert(rr);
            
         }}
        }

    }
    public void receiveorder(int orderid) throws SQLException{
        Report r=reports.getReport(orderid);
        if((r.getProductsAndAmount().isEmpty()))
        {
            System.out.println("not an order");
            
        }
        else{
            ReportDAO.getInstance().delete(orderid);
            ReportController.getInstance().reports.remove(r.getReportID());
            for (Integer product : r.getProductsAndAmount().keySet())
            {
                if(products.containsKey(product)&& product!=0){
                    addStorageItem(product, LocalDate.now().plusYears(1), r.getProductsAndAmount().get(product));}
        }}
        
    }
    public void sendorder(HashMap<Integer,Integer> o) throws SQLException
    {
       Report r= reports.buildWeeklyReport(o, null, null);
        for (Integer i : o.keySet()) {
            ReportDTO rr=new ReportDTO(r.getReportID(), i, o.get(i));
            ReportDAO.getInstance().insert(rr);
            
         }
    }
   
    public String returnInformationProducts(){
        String output="";
        for(Product product : products.values()){

            
            output += ("productID= "+product.getId() +" productName= "+ product.getName() + " productFullAmount= "+product.getFullAmount()
                    +" productStoreAmount= "+product.getStoreAmount()+" productStorageAmount= "+product.getStorageAmount()+" product Location= "+product.getShelfNumber()+" Product current price: "+product.getSellingPrice()+" Is InSale: " +product.isInSale());
            output+="\n\n";

        }
        return output;
    }
    public String returnInformationProduct(int poductid){
        if (!products.containsKey(poductid)) {
            throw new IllegalArgumentException("Product not found in store");
        }
        return("productID= "+products.get(poductid).getId() +" productName= "+ products.get(poductid).getName() + " productFullAmount= "+products.get(poductid).getFullAmount()
                +" productStoreAmount= "+products.get(poductid).getStoreAmount()+" productStorageAmount= "+products.get(poductid).getStorageAmount()+" productLocation= "+products.get(poductid).getShelfNumber()+" Current price: "+products.get(poductid).getSellingPrice()+" Is InSale: " +products.get(poductid).isInSale());


    }
    public String getDefectiveItemsByStore(List<Integer> cats) {
        List<Item> defectiveItems = new LinkedList<>();
        String out="";
        out+=getDamagedItemReportsByStore(cats);
        out +=getExpiredItemReportsByStore(cats);
        defectiveItems.addAll(reports.getReport( reports.getReportCounter()-1).getItems());
        defectiveItems.addAll(reports.getReport( reports.getReportCounter()-2).getItems());

        reports.buildItemReport( defectiveItems, LocalDate.now(), LocalDate.now());
        return out;
    }
    //
    public String getProductsAndAmount(List<Integer> cats) {
        HashMap<Integer, Integer> productsAndAmount = new HashMap<>();
        String out="Products and amount Report\n";
        try {
            for (Category cat : instance1.returnCategories()) {
                for (Product product : cat.getProducts()) {
            if(!productsAndAmount.containsKey(product.getId()))    productsAndAmount.put(product.getId(), product.getFullAmount());}}
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        reports.buildWeeklyReport( productsAndAmount, LocalDate.now(), LocalDate.now());
        for (Map.Entry<Integer, Integer> entry : productsAndAmount.entrySet()) {
            out+=("proudct ID: = " + entry.getKey() + ", Product amount  = " + entry.getValue())+"\n";
        }
        return out;
    }
    //
    public String getDamagedItemReportsByStore(List<Integer> cats) {
        List<Item> damagedItemList = new LinkedList<>();
        List<Product> productList1 =new LinkedList<>();

        for (Category cat : instance1.returnCategories()) {
            if (cats.contains(cat.getId())) {
            for (Product product : cat.getProducts()) {

            if(product.getDamaged().size()!=0) if(!productList1.contains(product)) productList1.add(product);

            damagedItemList.addAll(product.getDamaged());
        }}}
        reports.builddmgItemReport( damagedItemList, LocalDate.now(), LocalDate.now());

        String output="Damaged report \nThe products that have damaged items are  \n";
        for (Product product : productList1) {
            output+="product ID: "+product.getId()+" and the amount is: " + product.getDamaged().size()+"\n" ;

        }
        return output;
    }

    public String getExpiredItemReportsByStore(List<Integer> cats) {
        List<Item> expiredItemList = new LinkedList<>();
        List<Product> productList1 =new LinkedList<>();

        for (Category cat : instance1.returnCategories()) {
            if (cats.contains(cat.getId())) {
            for (Product product : cat.getProducts()) {
            expiredItemList.addAll(product.getExpireditems());
            if(product.getExpireditems().size()!=0)if(!productList1.contains(product)) productList1.add(product);

        }}}
        reports.buildexpiredItemReport( expiredItemList, LocalDate.now(), LocalDate.now());

        String output="Expired report \nThe products that have expired items are  \n";
        for (Product product : productList1)  {
            output+="product ID: "+product.getId()+" and the amount is: " + product.getExpireditems().size()+"\n" ;

        }
        return output;

    }
    public String getMinReport(List<Integer> cats) {
        List<Integer> mList = new LinkedList<>();
        for (Category cat : instance1.returnCategories()) {
            if (cats.contains(cat.getId())) {
            for (Product product : cat.getProducts()) { 
            if(product.getFullAmount()<=product.getMinAmount()){
             if(!mList.contains(product.getId()))   mList.add(product.getId());
            }}
        }}
        reports.buildProductReport( mList, LocalDate.now(), LocalDate.now());


        String output="Min report \n The products that need to be restocked are \n";
        for (Integer product : mList) {
            output+="product ID: "+product+"\n" ;

        }
        return output;
    }
    public String addDamfromstore(LocalDate exp, int productId, int amount) throws SQLException {
        try {
            ItemDAO.getInstance().addDmgstore(exp.toString(), productId, amount);

        } catch (Exception e) {
        }

        return products.get(productId).addDamfromstore(exp, amount);

    }
    
    public String addDamfromstorage(LocalDate exp, int productId, int amount) throws SQLException {
        try {
            ItemDAO.getInstance().addDmgstorage(exp.toString(), productId, amount);

        } catch (Exception e) {
        }

        return products.get(productId).addDamfromstorage(exp, amount);

    

    }

}

