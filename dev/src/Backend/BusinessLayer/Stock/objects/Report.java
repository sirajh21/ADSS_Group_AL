package Backend.BusinessLayer.Stock.objects;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Report {

    protected int reportID;
    protected List<Item> items=new LinkedList<>();
    protected List<Integer> products =new LinkedList<>();
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected HashMap<Integer, Integer> productsAndAmount =new HashMap<>();

    // for only items
    public Report(int reportID, List<Item> items, LocalDate startDate, LocalDate endDate) {
        this.items = (items == null) ? new LinkedList<>() : items;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportID = reportID;
        
    }

    // for products
    public Report(List<Integer> products, int reportID, LocalDate startDate, LocalDate endDate) {
        this.products = (products == null) ? new LinkedList<>() : products;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportID = reportID;
        
    }

    // for weekly report or sale report
    public Report(int reportID, HashMap<Integer, Integer> productsAndAmount, LocalDate startDate, LocalDate endDate) {
        this.productsAndAmount = (productsAndAmount == null) ? new HashMap<>() : productsAndAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportID = reportID;
        
        
        
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Integer> getProducts() {
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public HashMap<Integer, Integer> getProductsAndAmount() {
        return productsAndAmount;
    }

    public void setProductsAndAmount(HashMap<Integer, Integer> productsAndAmount) {
        this.productsAndAmount = productsAndAmount;
    }
}
