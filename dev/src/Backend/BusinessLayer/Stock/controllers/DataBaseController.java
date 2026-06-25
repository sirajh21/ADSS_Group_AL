package Backend.BusinessLayer.Stock.controllers;

import Backend.DataAccessLayer.*;
import Backend.BusinessLayer.Stock.objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.io.File;

//import java.sql.SQLException;

public class DataBaseController {

    private SuppliersDAO supplierDAO;
    private ContactDAO contactDAO;
    private Product1DAO productDAO;
    private ContractDAO contractDAO;
    private OrderDAO orderDAO;


    public static Connection connect() {
        String dbFileName = "superli.db";
        String dbPath;

        try {
            // Get working directory
            String projectDir = System.getProperty("user.dir");

            // Always go up one level if running from /release
            if (projectDir.endsWith("release")) {
                projectDir = new File(projectDir).getParent();
            }

            // Build DB path
            dbPath = projectDir + File.separator + "dev" + File.separator + dbFileName;

            String connectionString = "jdbc:sqlite:" + dbPath;
            System.out.println("Connecting to database at: " + connectionString);

            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(connectionString);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }





    /*
    private static final String DB_URL = "jdbc:sqlite:dev/src/database.db";
    public static Connection connect() {
        Connection connection1=null;
        try {
            connection1 =DriverManager.getConnection(DB_URL);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection1;
    }
*/
    public static DataBaseController instance =null;
    public static Connection connection;

    public static Connection get_connection(){ return connection;}
    public static DataBaseController getInstance() {
        if (instance == null){
            instance = new DataBaseController();
        }
        return instance;
    }
  //  public static void setInstance(DataBaseController instance){ DataBaseController.instance = instance;}

    public DataBaseController()  {
        this.connection = connect();
        this.supplierDAO = SuppliersDAO.getInstance();
        this.contactDAO = ContactDAO.getInstance();
        this.productDAO = Product1DAO.getInstance();
        this.contractDAO = ContractDAO.getInstance();
        this.orderDAO = OrderDAO.getInstance();

    }

    ///////////////supplier and contact////////////////////
    public List<Supplier> selectAllSuppliers() throws SQLException{
       return supplierDAO.selectAll();
    }

    public int selectByName(String name) throws SQLException{
        return supplierDAO.selectByName(name);
    }

    public String addSupplier(Supplier supplier) throws SQLException{
        return supplierDAO.insert(supplier.objectDTO());

    }
    public String delete(int id) throws SQLException {
        return supplierDAO.delete_Supplier(id);
    }

    public String update(SuppliersDTO suppliersDTO) throws SQLException {
        return supplierDAO.update(suppliersDTO);
    }

    public String addContact(ContactDTO c) throws SQLException {
        return this.contactDAO.insert(c);
    }

    public String deleteContact(int contactID) throws SQLException {
        return this.contactDAO.delete(contactID);
    }

    public List<Contact> selectAllContacts() throws SQLException, IllegalAccessException{
        return this.contactDAO.selectAll();
    }

    public void deleteData() throws SQLException{
        this.contactDAO.deleteData();
    }

    public String insert_supplier_contact(int supplier_id, int contact_id) throws SQLException{
        return this.supplierDAO.insert_supplier_contact(supplier_id,contact_id);
    }

    public String delete_supplier_contact(int supp_id, int contact_id) throws SQLException{
        return this.supplierDAO.delete_supplier_contact(supp_id,contact_id);
    }

    //////////////////products/////////////////

    public void insert_product(Product1DTO productDTO) throws SQLException {
        this.productDAO.insert(productDTO);
    }

    public Product1DTO selectById(int id) throws SQLException {
        return this.productDAO.selectById(id);
    }

    public void delete_Product(int id) throws SQLException {
        this.productDAO.delete(id);
    }

    public List<Product1> selectAllProducts() throws SQLException {
        return this.productDAO.selectAll();
    }

    /////contracts///////////////

    public void insertContract(ContractDTO contractDTO) throws SQLException {
        this.contractDAO.insert(contractDTO);
    }

    public ContractDTO selectById(String name) throws SQLException {
        return this.contractDAO.selectByName(name);
    }

    public void deleteContract(String name) throws SQLException {
        this.contractDAO.delete(name);
    }

    public void selectAllContracts() throws SQLException, IllegalAccessException {
         this.contractDAO.selectAll();
    }

    ////////////orders
    public void insert_order(OrderDTO order) throws SQLException {
        this.orderDAO.insert(order);
    }

    public int selectByIDOrder(int id) throws SQLException {
        return this.orderDAO.selectByID(id);
    }

    public String updateOrder(OrderDTO orderDTO) throws SQLException {
        return this.orderDAO.update(orderDTO);
    }

    public String delete_Order(int ID) throws SQLException {
        return this.orderDAO.delete_Order(ID);
    }

    public List<Order> selectAllOrders() throws SQLException {
        return this.orderDAO.selectAll();
    }








    }
