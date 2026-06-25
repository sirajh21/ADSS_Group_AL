package Backend.BusinessLayer.Stock.controllers;

import Backend.BusinessLayer.Stock.objects.Contract;
import Backend.BusinessLayer.Stock.objects.Order;
import Backend.BusinessLayer.Stock.objects.Product1;
import Backend.BusinessLayer.Stock.objects.Supplier;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderController {
    private static OrderController instance = new OrderController();

    public Map<Supplier, ArrayList<Order>> orders = new HashMap<>(); // shortage orders
    public Map<Supplier, ArrayList<PeriodicOrder>> periodicOrders = new HashMap<>(); // periodic orders

    public Map<Integer, Order> orders_id = new HashMap<>(); // shortage orders
    public Map<Integer, PeriodicOrder> orders_periodic_id = new HashMap<>(); // periodic orders

    DataBaseController dataBaseController = DataBaseController.getInstance();

    LocalDate date = LocalDate.now();
    int currentDay = date.getDayOfWeek().getValue();

    public static OrderController getInstance() {
        return instance;
    }

    public String toString(Order order) {
        return order.toString();
    }

    public ArrayList<Contract> getContractProducts(Supplier s, Set<Integer> products_id) {
        if (s.getContracts() == null) {
            return null;
        }

        ArrayList<Contract> supplierContractsWithProducts = new ArrayList<>();

        for (Contract contract : s.getContracts()) {
            boolean hasAllProducts = true;

            for (Integer productId : products_id) {
                Product1 product = SupplierController.getInstance().getProductById(productId);

                if (product == null || !contract.getProductsNumber().contains(product)) {
                    hasAllProducts = false;
                    break;
                }
            }

            if (hasAllProducts) {
                supplierContractsWithProducts.add(contract);
            }
        }

        return supplierContractsWithProducts;
    }

    public Supplier findCheapestSupplier(HashMap<Integer, Integer> productAndQuantity) {
        double best_price = Integer.MAX_VALUE;
        ArrayList<Supplier> suppliers = SupplierController.getInstance().getAllSuppliers();
        Set<Integer> products_id = productAndQuantity.keySet();

        Supplier supplier = null;

        for (Supplier s : suppliers) {
            ArrayList<Contract> supplierContracts = getContractProducts(s, products_id);

            if (supplierContracts == null) {
                continue;
            }

            for (Contract contract : supplierContracts) {
                double price = contract.checkPrice(productAndQuantity);

                if (price <= best_price) {
                    best_price = price;
                    supplier = s;
                }
            }
        }

        return supplier;
    }

    public boolean hasOpenOrderForProduct(int productId) {
        for (Order order : orders_id.values()) {
            if (order.getProductsAndAmount() != null &&
                    order.getProductsAndAmount().containsKey(productId)) {
                return true;
            }

            if (order.getProducts_id() != null &&
                    order.getProducts_id().contains(productId)) {
                return true;
            }
        }

        for (PeriodicOrder order : orders_periodic_id.values()) {
            if (order.getProductsAndAmount() != null &&
                    order.getProductsAndAmount().containsKey(productId)) {
                return true;
            }

            if (order.getProducts_id() != null &&
                    order.getProducts_id().contains(productId)) {
                return true;
            }
        }

        return false;
    }

    public int getExistingOrderIdForProduct(int productId) {
        for (Order order : orders_id.values()) {
            if (order.getProductsAndAmount() != null &&
                    order.getProductsAndAmount().containsKey(productId)) {
                return order.getOrder_id();
            }

            if (order.getProducts_id() != null &&
                    order.getProducts_id().contains(productId)) {
                return order.getOrder_id();
            }
        }

        for (PeriodicOrder order : orders_periodic_id.values()) {
            if (order.getProductsAndAmount() != null &&
                    order.getProductsAndAmount().containsKey(productId)) {
                return order.getOrder_id();
            }

            if (order.getProducts_id() != null &&
                    order.getProducts_id().contains(productId)) {
                return order.getOrder_id();
            }
        }

        return -1;
    }

    public int AddOrder(int id,
                        List<Integer> products_id,
                        HashMap<Integer, Integer> productsAndAmount,
                        LocalDate date,
                        SupplierController supplierController) throws IllegalAccessException, SQLException {

        if (products_id == null || productsAndAmount == null || date == null) {
            throw new IllegalAccessException("Data order cannot be empty");
        }

        for (Integer productId : productsAndAmount.keySet()) {
            int existingOrderId = getExistingOrderIdForProduct(productId);

            if (existingOrderId != -1) {
                System.out.println("Order already exists for product ID: " + productId +
                        " in order ID: " + existingOrderId);
                return existingOrderId;
            }
        }

        Supplier supplier = findCheapestSupplier(productsAndAmount);

        if (supplier == null) {
            throw new IllegalAccessException("No supplier for this order");
        }

        Order order = new Order(id, products_id, supplier, productsAndAmount, date);
        order.calculatPricePerProduct();

        orders_id.put(order.getOrder_id(), order);

        orders.putIfAbsent(supplier, new ArrayList<>());
        orders.get(supplier).add(order);

        dataBaseController.insert_order(order.object2Dto());

        return order.getOrder_id();
    }

    public int AddPeriodicOrder(int id,
                                List<Integer> products_id,
                                HashMap<Integer, Integer> productsAndAmount,
                                LocalDate date,
                                int arrival_day,
                                SupplierController supplierController) throws IllegalAccessException, SQLException {

        if (products_id == null || productsAndAmount == null || date == null) {
            throw new IllegalAccessException("Data order cannot be empty");
        }

        Supplier supplier = findCheapestSupplier(productsAndAmount);

        if (supplier == null) {
            throw new IllegalAccessException("No supplier for this order");
        }

        PeriodicOrder periodicOrder = new PeriodicOrder(id, products_id, supplier, productsAndAmount, date, arrival_day);
        periodicOrder.calculatPricePerProduct();

        orders_periodic_id.put(periodicOrder.getOrder_id(), periodicOrder);

        periodicOrders.putIfAbsent(supplier, new ArrayList<>());
        periodicOrders.get(supplier).add(periodicOrder);

        dataBaseController.insert_order(periodicOrder.object2Dto());

        return periodicOrder.getOrder_id();
    }

    public boolean updatePeriodicOrder(int orderId, HashMap<Integer, Integer> productsAndAmount) throws SQLException {
        if (productsAndAmount == null || productsAndAmount.isEmpty()) {
            return false;
        }

        // Case 1: the order exists in memory as a real PeriodicOrder
        PeriodicOrder oldPeriodicOrder = orders_periodic_id.get(orderId);

        if (oldPeriodicOrder != null) {
            Supplier newSupplier = findCheapestSupplier(productsAndAmount);

            if (newSupplier == null) {
                return false;
            }

            List<Integer> updatedProductsIds = new ArrayList<>(productsAndAmount.keySet());

            PeriodicOrder newPeriodicOrder = new PeriodicOrder(
                    orderId,
                    updatedProductsIds,
                    newSupplier,
                    productsAndAmount,
                    oldPeriodicOrder.getDate(),
                    oldPeriodicOrder.getArrival_day()
            );

            newPeriodicOrder.calculatPricePerProduct();

            orders_periodic_id.put(orderId, newPeriodicOrder);

            Supplier oldSupplier = oldPeriodicOrder.getSupplier();

            if (periodicOrders.containsKey(oldSupplier)) {
                periodicOrders.get(oldSupplier).remove(oldPeriodicOrder);

                if (periodicOrders.get(oldSupplier).isEmpty()) {
                    periodicOrders.remove(oldSupplier);
                }
            }

            periodicOrders.putIfAbsent(newSupplier, new ArrayList<>());
            periodicOrders.get(newSupplier).add(newPeriodicOrder);

            dataBaseController.updateOrder(newPeriodicOrder.object2Dto());

            return true;
        }

        // Case 2: after restart, the DB may load the periodic order as a normal Order
        Order oldOrder = orders_id.get(orderId);

        if (oldOrder == null) {
            return false;
        }

        Supplier newSupplier = findCheapestSupplier(productsAndAmount);

        if (newSupplier == null) {
            return false;
        }

        List<Integer> updatedProductsIds = new ArrayList<>(productsAndAmount.keySet());

        Order newOrder = new Order(
                orderId,
                updatedProductsIds,
                newSupplier,
                productsAndAmount,
                oldOrder.getDate()
        );

        newOrder.calculatPricePerProduct();

        orders_id.put(orderId, newOrder);

        Supplier oldSupplier = oldOrder.getSupplier();

        if (orders.containsKey(oldSupplier)) {
            orders.get(oldSupplier).remove(oldOrder);

            if (orders.get(oldSupplier).isEmpty()) {
                orders.remove(oldSupplier);
            }
        }

        orders.putIfAbsent(newSupplier, new ArrayList<>());
        orders.get(newSupplier).add(newOrder);

        dataBaseController.updateOrder(newOrder.object2Dto());

        return true;
    }
    public String DeleteOrder(int id, Order order, SupplierController supplierController) throws IllegalAccessException, SQLException {
        if (id == 0 || order == null) {
            throw new IllegalAccessException("Data order cannot be empty");
        }

        Supplier supplier = supplierController.getSupplier(id);

        if (!orders.containsKey(supplier) && !periodicOrders.containsKey(supplier)) {
            throw new IllegalAccessException("Data order not found");
        }

        if (order.isPeriodicOrder()) {
            orders_periodic_id.remove(order.getOrder_id());

            if (periodicOrders.containsKey(supplier)) {
                periodicOrders.get(supplier).remove(order);
            }
        } else {
            orders_id.remove(order.getOrder_id());

            if (orders.containsKey(supplier)) {
                orders.get(supplier).remove(order);
            }
        }

        dataBaseController.delete_Order(order.getOrder_id());

        return "Order deleted successfully";
    }

    public Order showOrder(int id, Order order, SupplierController supplierController) throws IllegalAccessException {
        Supplier supplier = supplierController.getSupplier(id);

        if (!supplierController.getAllSuppliers().contains(supplier)) {
            throw new IllegalAccessException("Supplier not found");
        }

        if (order == null || !orders_id.containsKey(order.getOrder_id())) {
            throw new IllegalAccessException("Data order cannot be empty");
        }

        if (!orders.containsKey(supplier)) {
            throw new IllegalAccessException("Order not found for this supplier");
        }

        for (Order o : orders.get(supplier)) {
            if (o.getOrder_id() == order.getOrder_id()) {
                return o;
            }
        }

        throw new IllegalAccessException("Order not found for this supplier");
    }

    public Map<Supplier, ArrayList<Order>> getOrders() {
        return orders;
    }

    public Map<Supplier, ArrayList<PeriodicOrder>> getPeriodicOrders() {
        return periodicOrders;
    }

    public Map<Integer, Order> getIdOrders() {
        return orders_id;
    }

    public Map<Integer, PeriodicOrder> getIdProdicOrders() {
        return orders_periodic_id;
    }

    public int getTotalOrder(Order order) {
        return order.getTotalPrice();
    }

    public List<Integer> getProducts_id(Order order) {
        return order.getProducts_id();
    }

    public Supplier getSupplier(Order order) {
        return order.getSupplier();
    }

    public HashMap<Integer, Integer> getProductsAndAmount(Order order) {
        return order.getProductsAndAmount();
    }

    public HashMap<Product1, Double> getPricePerProducts(Order order) {
        return order.getPricePerProducts();
    }

    public LocalDate getDate(Order order) {
        return order.getDate();
    }

    public String increment_day() throws SQLDataException, IllegalAccessException {
        if (currentDay == 6) {
            currentDay = 0;
        } else {
            currentDay++;
        }

        runThroughDay();

        return "Increment Day success";
    }

    public void runThroughDay() throws SQLDataException, IllegalAccessException {
        for (PeriodicOrder po : orders_periodic_id.values()) {
            if (po.getArrival_day() == this.currentDay) {
                makeOrdertoPeriodic(
                        po.getOrder_id(),
                        po.getProducts_id(),
                        po.getSupplier(),
                        po.getProductsAndAmount(),
                        po.getDate(),
                        SupplierController.getInstance()
                );
            }
        }
    }

    public int makeOrdertoPeriodic(int id,
                                   List<Integer> products_id,
                                   Supplier supplier,
                                   HashMap<Integer, Integer> productsAndAmount,
                                   LocalDate date,
                                   SupplierController supplierController) throws IllegalAccessException {

        if (products_id == null || productsAndAmount == null || date == null) {
            throw new IllegalAccessException("Data order cannot be empty");
        }

        Order order = new Order(id, products_id, supplier, productsAndAmount, date);
        order.calculatPricePerProduct();

        orders_id.put(order.getOrder_id(), order);

        orders.putIfAbsent(supplier, new ArrayList<>());
        orders.get(supplier).add(order);

        return order.getOrder_id();
    }

    public String deleteAllOrders() throws SQLException, IllegalAccessException {
        List<Order> order1 = dataBaseController.selectAllOrders();

        for (Order o : order1) {
            dataBaseController.delete_Order(o.getOrder_id());
        }

        orders.clear();
        periodicOrders.clear();
        orders_id.clear();
        orders_periodic_id.clear();

        return "All orders deleted";
    }
}