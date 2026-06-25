package Backend.ServiceLayer.Backend.ServiceLayer;


import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;
public class Service {
     SupplierService supplierService;
     OrderService orderService;
     ContractService contractService;

    public Service() {
        this.supplierService = new SupplierService();
        this.orderService = new OrderService(supplierService);
        this.contractService = new ContractService(supplierService);
        loadPersistentData();
    }

    public void clearInMemoryData() {
        SupplierController supplierController = getSuppliersController();
        supplierController.suppliers.clear();
        supplierController.suppliersById.clear();
        supplierController.productsById.clear();

        ContractController contractController = getContractController();
        contractController.contracts.clear();

        OrderController orderController = getOrderController();
        orderController.orders.clear();
        orderController.periodicOrders.clear();
        orderController.orders_id.clear();
        orderController.orders_periodic_id.clear();
    }

    public void loadPersistentData() {
        try {
            clearInMemoryData();
            loadSuppliersFromDB();
            loadProductsFromDB();
            loadContractsFromDB();
            loadOrdersFromDB();
        } catch (Exception e) {
            System.out.println("⚠ Persistent data was not fully loaded: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSuppliersFromDB() throws SQLException {
        SupplierController supplierController = getSuppliersController();
        for (Supplier supplier : DataBaseController.getInstance().selectAllSuppliers()) {
            if (!supplierController.suppliersById.containsKey(supplier.getSupplier_id())) {
                supplierController.suppliers.add(supplier);
                supplierController.suppliersById.put(supplier.getSupplier_id(), supplier);
            }
        }
    }

    private void loadProductsFromDB() throws SQLException {
        SupplierController supplierController = getSuppliersController();
        for (Product1 product : DataBaseController.getInstance().selectAllProducts()) {
            supplierController.productsById.put(product.getProduct_id(), product);
        }
    }

    private void loadContractsFromDB() throws SQLException, IllegalAccessException {
        Connection connection = DataBaseController.connect();
        if (connection == null) {
            return;
        }

        String sql = "SELECT SupplierID, ContractName, Scheduled, ProductsNumbers, ProductQuantityDiscount, ProductQuantityDiscount1 FROM Contract";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int supplierId = resultSet.getInt("SupplierID");
                String contractName = resultSet.getString("ContractName");
                boolean scheduled = resultSet.getBoolean("Scheduled");
                String productsNumbers = resultSet.getString("ProductsNumbers");
                String discounts = resultSet.getString("ProductQuantityDiscount");
                String quantities = resultSet.getString("ProductQuantityDiscount1");

                HashMap<Product1, HashMap<Integer, Double>> productQuantityDiscount = parseContractProducts(productsNumbers, quantities, discounts);
                if (productQuantityDiscount.isEmpty()) {
                    continue;
                }

                Supplier supplier = getSuppliersController().suppliersById.get(supplierId);
                if (supplier == null) {
                    continue;
                }

                boolean exists = false;
                for (Contract existing : supplier.getContracts()) {
                    if (existing.getContract_name().equals(contractName)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    continue;
                }

                Contract contract = new Contract(contractName, supplierId, scheduled, new ArrayList<>(productQuantityDiscount.keySet()), productQuantityDiscount);
                supplier.AddContract(contract);
                getContractController().contracts.add(contract);
            }
        } finally {
            connection.close();
        }
    }

    private HashMap<Product1, HashMap<Integer, Double>> parseContractProducts(String productsNumbers, String quantitiesText, String discountsText) {
        HashMap<Product1, HashMap<Integer, Double>> result = new HashMap<>();
        ArrayList<Integer> productIds = extractProductIds(productsNumbers);
        ArrayList<Integer> quantities = extractIntegerList(quantitiesText);
        ArrayList<Double> discounts = extractDoubleList(discountsText);

        for (int i = 0; i < productIds.size(); i++) {
            Product1 product = getSuppliersController().productsById.get(productIds.get(i));
            if (product == null) {
                continue;
            }
            int quantity = i < quantities.size() ? quantities.get(i) : 0;
            double discount = i < discounts.size() ? discounts.get(i) : 0.0;
            HashMap<Integer, Double> discountMap = new HashMap<>();
            discountMap.put(quantity, discount);
            result.put(product, discountMap);
        }
        return result;
    }

    private void loadOrdersFromDB() throws SQLException {
        Connection connection = DataBaseController.connect();
        if (connection == null) {
            return;
        }

        String sql = "SELECT orderID, supplierID, ProductsAndAmount, date FROM Order1";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderID");
                int supplierId = resultSet.getInt("supplierID");
                String productsAndAmountText = resultSet.getString("ProductsAndAmount");
                String dateText = resultSet.getString("date");

                Supplier supplier = getSuppliersController().suppliersById.get(supplierId);
                if (supplier == null) {
                    continue;
                }

                HashMap<Integer, Integer> productsAndAmount = parseProductsAndAmount(productsAndAmountText);
                ArrayList<Integer> productIds = new ArrayList<>(productsAndAmount.keySet());
                LocalDate orderDate = LocalDate.parse(dateText);

                Order order = new Order(orderId, productIds, supplier, productsAndAmount, orderDate);
                order.calculatPricePerProduct();

                OrderController orderController = getOrderController();
                orderController.orders_id.put(order.getOrder_id(), order);
                orderController.orders.putIfAbsent(supplier, new ArrayList<>());
                orderController.orders.get(supplier).add(order);
            }
        } finally {
            connection.close();
        }
    }

    private HashMap<Integer, Integer> parseProductsAndAmount(String text) {
        HashMap<Integer, Integer> result = new HashMap<>();
        if (text == null) {
            return result;
        }

        Matcher matcher = Pattern.compile("(\\d+)\\s*=\\s*(\\d+)").matcher(text);
        while (matcher.find()) {
            result.put(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        }
        return result;
    }

    private ArrayList<Integer> extractProductIds(String text) {
        ArrayList<Integer> result = new ArrayList<>();
        if (text == null) {
            return result;
        }

        Matcher matcher = Pattern.compile("ID=(\\d+)").matcher(text);
        while (matcher.find()) {
            result.add(Integer.parseInt(matcher.group(1)));
        }
        return result;
    }

    private ArrayList<Integer> extractIntegerList(String text) {
        ArrayList<Integer> result = new ArrayList<>();
        if (text == null) {
            return result;
        }

        Matcher matcher = Pattern.compile("-?\\d+").matcher(text);
        while (matcher.find()) {
            result.add(Integer.parseInt(matcher.group()));
        }
        return result;
    }

    private ArrayList<Double> extractDoubleList(String text) {
        ArrayList<Double> result = new ArrayList<>();
        if (text == null) {
            return result;
        }

        Matcher matcher = Pattern.compile("-?\\d+(?:\\.\\d+)?").matcher(text);
        while (matcher.find()) {
            result.add(Double.parseDouble(matcher.group()));
        }
        return result;
    }

    public SupplierController getSuppliersController(){
        return supplierService.getSuppliersController();
    }

    public OrderController getOrderController(){ return orderService.getOrderController();}

    public ContractController getContractController(){ return contractService.contractController;}

    public Supplier AddSupplier( int id,String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType, ArrayList<String> companies) {
        return supplierService.AddSupplier(id,name,phoneNumber,intSupplierType,BankAccount,paymentType,companies);
    }

    public String deleteAllsuplier(){
        return supplierService.deleteAllsuplier();
    }

    public String deleteAllContacts(){
        return supplierService.deleteAllcontacts();
    }

    public String deleteAllProducts(){
        return supplierService.deleteAllProducts();
    }

    public String deleteAllContracts(){
        return contractService.deleteAllContracts();
    }

    public String deleteAllOrders(){
        return orderService.deleteAllOrders();
    }

    public String DeleteSupplier(int id) {
        return supplierService.DeleteSupplier(id);
    }

    public String AddContact(int id,int contact_id, String name, String phone_number, String email) {
        return supplierService.AddContact( id,contact_id,name,phone_number,email);
    }

    public boolean AddProduct(int price, String product_name,int pid) {
        return supplierService.AddProduct(price,product_name,pid);
    }

    public String AddCompany(int id, String companyName){
        return supplierService.AddCompany( id,companyName);
    }

    public String DeleteCompany(int id, String companyName){
        return supplierService.DeleteCompany( id,companyName);
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
    public Supplier getSupplier(int id){
        return supplierService.getSupplier(id);
    }

    public HashMap<Integer, Product1> getAllProducts(){
        return supplierService.getAllProducts();
    }

    public double checkPrice(int supplier_id,HashMap<Integer, Integer> productAndQuantity){
        return contractService.checkPrice(supplier_id, productAndQuantity);
    }

    public String makeContract(int id, String contractName, boolean scheduled, HashMap<Product1, HashMap<Integer, Double>> productQuantityDiscount) {
        return contractService.makeContract( id,contractName,scheduled,productQuantityDiscount);
    }

    public String deleteContract(String name) {
        return contractService.deleteContract(name);
    }

    public ArrayList<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    public int AddPeriodicOrder(int id, List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date, int arrival_day ) throws IllegalAccessException, SQLException {
        return orderService.AddPeriodicOrder(id,products_id,productsAndAmount,date, arrival_day);
    }
    public boolean updatePeriodicOrder(int orderId,HashMap<Integer, Integer> productsAndAmount ){
        return orderService.updatePeriodicOrder(orderId, productsAndAmount);
    }
        public int AddOrder(int id, List<Integer> products_id, HashMap<Integer, Integer> productsAndAmount, LocalDate date ) throws IllegalAccessException, SQLException {
        return orderService.AddOrder(id,products_id,productsAndAmount,date );
    }

    public String DeleteOrder(int id, Order order) {
        return orderService.DeleteOrder( id,order);
    }

    public String toStringOrder(Order order){
        return orderService.toString(order);
    }
        public Order showOrder( int id,Order order) {
        return orderService.showOrder( id,order);
    }



    public ArrayList<Contact> getContacts(int id){
        return supplierService.getContacts(id);
    }

    public ArrayList<String> getCompanies(int id){
        return supplierService.getCompanies(id);
    }

    public ArrayList<Contract> getContract(int id){
        return supplierService.getContract(id);
    }

    public String toStringSupplier(int id){
        return supplierService.toString(id);
    }

    public Map<Supplier, ArrayList<Order>> getOrders() {
        return orderService.getOrders();
    }
    public Map<Supplier, ArrayList<PeriodicOrder>> getPeriodicOrders() {
        return orderService.getPeriodicOrders();
    }

    public Map<Integer, Order> getIdOrders() {
        return orderService.getIdOrders();
    }
    public Map<Integer, PeriodicOrder> getIdPeriodicOrders() {
        return orderService.getIdProdicOrders();
    }

    public int getTotalOrder(Order order) {
        return orderService.getTotalOrder(order);
    }

    public List<Integer> getProducts_id(Order order){
        return orderService.getProducts_id(order);
    }

    public Supplier getSupplier(Order order) {
        return orderService.getSupplier(order);
    }

    public HashMap<Integer, Integer> getProductsAndAmount(Order order) {
        return orderService.getProductsAndAmount(order);
    }

    public HashMap<Product1, Double> getPricePerProducts(Order order) {
        return orderService.getPricePerProducts(order);
    }

    public String Advance_day(){
        return orderService.Advance_day();
    }
    }
