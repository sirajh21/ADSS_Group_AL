
import ServiceLayer.*;
import BusinessLayer.*;
import ServiceLayer.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTests {

    private Supplier supplier;
    private Product product;
    private Contract contract;
    private Order order;
    private Service service;

    @BeforeEach
    void setUp() {
        supplier = new Supplier(1, "Test Supplier", "Test Address", "0500000000", new BankDetails("Bank", "Branch", 123), new ArrayList<>());
        product = new Product(100, "Test Product", 10.0);
        service = new Service();
    }

    // 🔵 Contract Tests

    @Test
    void testCreateContract() {
        contract = new Contract(1, "Contract1", EnumSuppType.ScheduledSupplier, new ArrayList<>());
        assertEquals("Contract1", contract.getContractName());
    }

    @Test
    void testAddProductToContract() {
        contract = new Contract(1, "Contract1", EnumSuppType.ScheduledSupplier, new ArrayList<>());
        contract.AddProduct(product);
        assertTrue(contract.getProductsCatalogNumber().containsKey(product.getProductId()));
    }

    @Test
    void testDefineDiscountForProduct() {
        contract = new Contract(1, "Contract1", EnumSuppType.ScheduledSupplier, new ArrayList<>());
        contract.AddProduct(product);
        contract.DefineDiscount(product, 5, 10);
        assertTrue(contract.QuantityDiscountPerProduct.containsKey(product));
        assertEquals(10, contract.QuantityDiscountPerProduct.get(product).get(5));
    }

    @Test
    void testIsProductInContract() {
        contract = new Contract(1, "Contract1", EnumSuppType.ScheduledSupplier, new ArrayList<>());
        contract.AddProduct(product);
        assertTrue(contract.IsProductInContract(product.getProductId()));
    }

    // 🟢 Order Tests

    @Test
    void testCreateOrder() {
        order = new Order(1, supplier, EnumPayment.cash);
        assertEquals(1, order.getOrderId());
        assertEquals("In Progress", order.getOrderStatus());
    }

    @Test
    void testAddProductToOrder() {
        order = new Order(1, supplier, EnumPayment.cash);
        order.addProduct(product, 2);
        assertTrue(order.getProducts().containsKey(product));
    }

    @Test
    void testCalculateOrderTotalWithDiscount() {
        contract = new Contract(1, "Contract1", EnumSuppType.ScheduledSupplier, new ArrayList<>());
        contract.AddProduct(product);
        contract.DefineDiscount(product, 2, 10); // 10% off for 2+
        supplier.addContract(contract);

        order = new Order(1, supplier, EnumPayment.cash);
        order.addProduct(product, 2);

        double expectedPrice = (10.0 * 2) * 0.9; // 10% discount
        assertEquals(expectedPrice, order.getTotalCost(), 0.01);
    }

    // 🟡 Service Layer Tests

    @Test
    void testAddSupplierViaService() {
        String result = service.addSupplier(2, "New Supplier", "New Address", "0511111111", new BankDetails("Bank", "Branch", 456), new ArrayList<>());
        assertTrue(result.contains("✅"));
        assertNotNull(service.getSupplier(2));
    }

    @Test
    void testCreateContractViaService() {
        service.addSupplier(3, "Contract Supplier", "Address", "0522222222", new BankDetails("Bank", "Branch", 789), new ArrayList<>());
        String result = service.createContract(3, "ContractTest", EnumSuppType.ScheduledSupplier, new ArrayList<>());
        assertTrue(result.contains("✅"));
        assertNotNull(service.getContract("ContractTest"));
    }
    @Test
    void testDeleteSupplier() {
        service.addSupplier(5, "SupplierToDelete", "Delete Address", "0533333333", new BankDetails("Bank", "Branch", 111), new ArrayList<>());
        assertNotNull(service.getSupplier(5));

        String result = service.deleteSupplier(5);
        assertTrue(result.contains("✅"));
        assertNull(service.getSupplier(5));
    }

}
