package Backend.ServiceLayer.Backend.ServiceLayer;



import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;

import java.time.LocalDate;
import java.util.List;



public class DiscountService {

    public discountcontroller discountController = discountcontroller.getInstance();

    public DiscountService() {
    }

    public String buildProductDiscount(Product product, double percent, LocalDate start,
                                       LocalDate end) {
        String output = "{}";
        try {
            output = discountController.buildProductDiscount( product, percent, start, end);
            System.out.println(output);

            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }

    }
    //
    public String buildCategoryDiscount( Category category, double percent, LocalDate start,
                                         LocalDate end) {
        String output = "{}";
        try {
            output = discountController.buildCategoryDiscount(category, percent, start, end);
            System.out.println(output);

            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();

        }
    }
    // //
    public String endDiscounts() {
        String output = "{}";
        try {
            output = discountController.endDiscounts();
            System.out.println(output);

            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }}
    public String addPDiscount( Product product, double discount) {
        String output = "{}";
        try {
            output = discountController.addPDiscount(product,discount);
            System.out.println(output);

            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }
    }
    public String endDiscount(Product product) {
        String output = "{}";
        try {
            output = discountController.endDiscount(product);
            System.out.println(output);
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();          }
    }
    public List<Discount> geDiscounts() {
        try {
            return discountController.returnDiscounts();
        } catch (Exception e) {
            return null;
        }
    }
    public String getactiveCategoriesDiscounts() {
        String output = "{}";
        try {
            output = discountController.getactiveCategoriesDiscounts();
            System.out.println(output);

            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }}  public String getactiveProductsDiscounts() {
        String output = "{}";
        try {
            output = discountController.getactiveProductsDiscounts();
            System.out.println(output);

            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return e.getMessage();
        }}
}
