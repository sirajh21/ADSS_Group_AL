package Backend.ServiceLayer.Backend.ServiceLayer;


import java.util.List;


import Backend.BusinessLayer.Stock.controllers.*;

public class ReportService {

    public productscontroller productsController ;

    public ReportService(ProductService productsController ) {
        this.productsController=productsController.getProductsController();
    }




    public String getDefectiveItemsByStore(List<Integer> cats) {
        try {
            String out= productsController.getDefectiveItemsByStore(cats);
            System.out.println(out);
            return out;        }
        catch (Exception e) {
            return null;

        }
    }

    public String getProductsAndAmount(List<Integer> cats) {
        try {
            String out= productsController.getProductsAndAmount(cats);
            System.out.println(out);
            return out;        }
        catch (Exception e) {
            return null;

        }
    }
    public String getDamagedItemReportsByStore(List<Integer> cats) {
        try {
            String out= productsController.getDamagedItemReportsByStore(cats);
            System.out.println(out);
            return out;
        } catch (Exception e) {
            return null;
        }
    }

    public String getExpiredItemReportsByStore(List<Integer> cats) {
        try {
            String out= productsController.getExpiredItemReportsByStore(cats);
            System.out.println(out);
            return out;
        } catch (Exception e) {
            return null;
        }
    }

    public String getMinReport(List<Integer> cats) {
        try {
            String output= productsController.getMinReport(cats );
            System.out.println(output);
            return output;
        } catch (Exception e) {
            return null;
        }
    }
}
