import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class DomainTest {

    private Bage facade;

    private Date parseDate(String text) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() {
        facade = Bage.instance();

        facade.addCategory("cat1");
        facade.addCategory("cat2");
        facade.addCategory("cat3");

        facade.addItem("item1", "cat1", parseDate("10/10/2027"), 5, 10, 0, 5, "CompanyA", 20);
        facade.addItem("item2", "cat2", parseDate("15/11/2027"), 3, 20, 0, 4, "CompanyB", 50);
        facade.addItem("item3", "cat1", parseDate("20/12/2027"), 7, 30, 10, 6, "CompanyC", 40);
    }

    @After
    public void tearDown() {
        facade.removeCategory("cat1");
        facade.removeCategory("cat2");
        facade.removeCategory("cat3");
    }

    @Test
    public void addCategoryTest() {
        facade.addCategory("cat4");
        assertTrue(facade.IsCategoryExistsByName("cat4"));
        facade.removeCategory("cat4");
    }

    @Test
    public void removeCategoryTest() {
        assertTrue(facade.IsCategoryExistsByName("cat3"));
        facade.removeCategory("cat3");
        assertFalse(facade.IsCategoryExistsByName("cat3"));
        facade.addCategory("cat3");
    }

    @Test
    public void addItemTest() {
        facade.addItem("item4", "cat1", parseDate("01/01/2028"), 4, 8, 0, 3, "CompanyD", 12);
        assertTrue(facade.IsItemExistsByName("item4"));
        assertEquals(12, facade.get_item_amount("item4"));
        facade.removeItem("item4");
    }

    @Test
    public void removeItemTest() {
        assertTrue(facade.IsItemExistsByName("item2"));
        facade.removeItem("item2");
        assertFalse(facade.IsItemExistsByName("item2"));

        facade.addItem("item2", "cat2", parseDate("15/11/2027"), 3, 20, 0, 4, "CompanyB", 50);
    }

    @Test
    public void getItemByNameTest() {
        items item = facade.getItemByName("item1");
        assertNotNull(item);
        assertEquals("item1", item.getName());
    }

    @Test
    public void getCategoryByNameTest() {
        catogeryCon category = facade.getCategoryByName("cat1");
        assertNotNull(category);
        assertEquals("cat1", category.getCategoryName());
    }



    @Test
    public void categoryDiscountTest() {
        facade.addCategoryDiscount("cat1", 50);
        assertEquals(5.0, facade.get_item_price("item1"), 0.001);

        facade.removecat("cat1");
        assertEquals(10.0, facade.get_item_price("item1"), 0.001);
    }

    @Test
    public void futureItemDiscountTest() {
        Date start = parseDate("01/01/2020");
        Date end = parseDate("01/01/2030");

        facade.add_future_ItemDiscount("item1", 25, start, end);
        assertEquals(7.5, facade.get_item_price("item1"), 0.001);

        facade.remove_future_ItemDiscount("item1");
    }

    @Test
    public void futureCategoryDiscountTest() {
        Date start = parseDate("01/01/2020");
        Date end = parseDate("01/01/2030");

        facade.add_future_CategoryDiscount("cat1", 20, start, end);
        assertEquals(8.0, facade.get_item_price("item1"), 0.001);

        facade.remove_future_Car_Discount("cat1", start, end);
    }

    @Test
    public void addSubCategoryTest() {
        facade.addSubCategory("cat1", "sub1");
        LinkedList<catogeryCon> subCategories = facade.getsubCategoryNames("cat1");

        assertNotNull(subCategories);
        assertEquals(1, subCategories.size());
    }

    @Test
    public void removeSubCategoryTest() {
        facade.addSubCategory("cat1", "sub2");
        assertEquals(1, facade.getsubCategoryNames("cat1").size());

        facade.removesubcategory("cat1", "sub2");
        assertEquals(0, facade.getsubCategoryNames("cat1").size());
    }

    @Test
    public void transferItemsTest() {
        facade.itemtransfer("item1", 10);

        assertEquals(10, facade.getItemsShelf("item1"));
        assertEquals(10, facade.getItemsStored("item1"));
    }

    @Test
    public void minimumAmountTest() {
        facade.addMinimumAttributeItem("item1", 15);
        assertEquals(15, facade.getItemByName("item1").getMinAmount());
    }

    @Test
    public void makeOrderTest() {
        HashMap<String, Integer> orderItems = new HashMap<>();
        orderItems.put("item1", 5);
        orderItems.put("item2", 10);

        facade.order(orderItems, new Date());

        assertEquals(15, facade.get_item_amount("item1"));
        assertEquals(40, facade.get_item_amount("item2"));
    }

    @Test
    public void categoryItemsCountTest() {
        assertEquals(60, facade.num_items_cat("cat1"));
        assertEquals(50, facade.num_items_cat("cat2"));
        assertEquals(0, facade.num_items_cat("cat3"));
    }

    @Test
    public void makeCategoryReportTest() {
        facade.makeCategoryReport("catReport", "cat1", "category report description", new Date());

        Report report = facade.getReportByname("catReport");
        assertNotNull(report);
        assertTrue(report.toString().contains("catReport"));
        assertTrue(report.toString().contains("category report description"));
    }

    @Test
    public void makeItemReportTest() {
        facade.makeItemReport("itemReport", "item1", "item report description", new Date());

        Report report = facade.getReportByname("itemReport");
        assertNotNull(report);
        assertTrue(report.toString().contains("itemReport"));
        assertTrue(report.toString().contains("item1"));
    }

    @Test
    public void makeLackReportTest() {
        facade.addMinimumAttributeItem("item1", 30);
        facade.makeLackReport("lackReport", "lack report description", new Date());

        Report report = facade.getReportByname("lackReport");
        assertNotNull(report);
        assertTrue(report.toString().contains("lackReport"));
    }



    @Test
    public void makeDefectReportAutoTest() {
        facade.makeDefectReport("autoDefectReport", "automatic defect report", new Date());

        Report report = facade.getReportByname("autoDefectReport");
        assertNotNull(report);
        assertTrue(report.toString().contains("autoDefectReport"));
    }

    @Test
    public void showExpiredItemsTest() {
        facade.addItem("expiredItem", "cat1", parseDate("01/01/2020"), 2, 5, 0, 1, "OldCompany", 7);

        String result = facade.showItemsUntilExpDate(new Date()).toString();

        assertTrue(result.contains("expiredItem"));
        facade.removeItem("expiredItem");
    }

    @Test
    public void showAvailableItemsTest() {
        String result = facade.showItems_after_ExpDate(new Date()).toString();

        assertTrue(result.contains("item1"));
        assertTrue(result.contains("item2"));
        assertTrue(result.contains("item3"));
    }

    @Test
    public void showStatusTest() {
        String status = facade.showStatus().toString();

        assertTrue(status.contains("cat1"));
        assertTrue(status.contains("item1"));
        assertTrue(status.contains("item2"));
    }

    @Test
    public void itemToStringTest() {
        product item = new product("testItem", parseDate("12/12/2027"), 5);
        String text = item.toString();

        assertTrue(text.contains("testItem"));
        assertTrue(text.contains("12/12/2027"));
    }

    @Test
    public void discountObjectTest() {
        DisCount discount = new DisCount("item1", parseDate("01/01/2025"), parseDate("01/01/2028"), 15);

        assertEquals("item1", discount.getName());
        assertEquals(15.0, discount.getDiscount_P(), 0.001);
    }

    @Test
    public void subCategoryTest() {
        foodcat subCategory = new foodcat("subCat", "mainCat");

        assertEquals("subCat", subCategory.getCategoryName());
        assertEquals("mainCat", subCategory.getUpperCat());
    }

    @Test
    public void reportTest() {
        Report report = new Report(new Date(), "testReport", status_report.Weekly, "hello");

        assertEquals("testReport", report.getReportName());
        assertTrue(report.toString().contains("hello"));
    }

    @Test
    public void orderTest() {
        HashMap<items, Integer> orderItems = new HashMap<>();
        orderItems.put(facade.getItemByName("item1"), 2);
        orderItems.put(facade.getItemByName("item2"), 3);

        Order order = new Order(orderItems, new Date());

        assertEquals(2, order.getItems().size());
        assertTrue(order.totalPrice() > 0);
    }
}