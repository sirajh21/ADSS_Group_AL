package Backend.ServiceLayer.Backend.ServiceLayer;


import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;

public class OrderService {
    public OrderController orderController = OrderController.getInstance();
    public SupplierController supplierController;

    public OrderController getOrderController() {
        return orderController;
    }


    public OrderService(SupplierService supplierController) {
        this.supplierController = supplierController.getSuppliersController();
    }

    public String toString(Order order){
        try {
            String output = orderController.toString(order);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public int AddOrder(int id, List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date ) throws IllegalAccessException, SQLException {
            int output = orderController.AddOrder( id, products_id, productsAndAmount, date, supplierController );
            return output;


    }

    public int AddPeriodicOrder( int id,List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date, int arrival_day ) throws IllegalAccessException, SQLException {
        int output = orderController.AddPeriodicOrder(  id,products_id, productsAndAmount, date,arrival_day, supplierController );
        return output;


    }
    public boolean updatePeriodicOrder(int orderId,HashMap<Integer, Integer> productsAndAmount ){
        try {
            boolean output = orderController.updatePeriodicOrder(orderId, productsAndAmount);
            return output;
        } catch (Exception e) {
            return false;
        }
    }

        public String DeleteOrder(int id, Order order) {
        try {
            String output = orderController.DeleteOrder(id, order,  supplierController);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public Order showOrder(int id, Order order) {
        try {
            Order output = orderController.showOrder(id, order, supplierController);
            return output;
        } catch (Exception e) {
            return null;
        }
    }





    public Map<Supplier, ArrayList<Order>> getOrders() {
        try {
            Map<Supplier, ArrayList<Order>> output = orderController.getOrders();
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public  Map<Supplier,ArrayList<PeriodicOrder>> getPeriodicOrders() {
        try {
            Map<Supplier, ArrayList<PeriodicOrder>> output = orderController.getPeriodicOrders();
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<Integer, Order> getIdOrders() {
        try {
            Map<Integer, Order> output = orderController.getIdOrders();
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public  Map<Integer,PeriodicOrder> getIdProdicOrders() {
        try {
            Map<Integer, PeriodicOrder> output = orderController.getIdProdicOrders();
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public int getTotalOrder(Order order) {
        try {
            int output = orderController.getTotalOrder(order);
            return output;
        } catch (Exception e) {
            return 0;
        }
    }


    public List<Integer> getProducts_id(Order order){
        try {
            List<Integer> output = orderController.getProducts_id(order);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public Supplier getSupplier(Order order) {
        try {
            Supplier output = orderController.getSupplier(order);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public HashMap<Integer, Integer> getProductsAndAmount(Order order) {
        try {
            HashMap<Integer,Integer> output = orderController.getProductsAndAmount(order);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public HashMap<Product1, Double> getPricePerProducts(Order order) {
        try {
            HashMap<Product1, Double> output = orderController.getPricePerProducts(order);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public String Advance_day(){
        try {
            String output= orderController.increment_day();
            return output;
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteAllOrders() {
        try {
            String output = orderController.deleteAllOrders();
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }






    }
