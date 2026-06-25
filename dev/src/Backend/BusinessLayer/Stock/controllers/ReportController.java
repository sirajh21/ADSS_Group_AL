package Backend.BusinessLayer.Stock.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import Backend.DataAccessLayer.*;
import Backend.BusinessLayer.Stock.objects.*;



public class ReportController {
    public HashMap<Integer, Report> reports;
    private static ReportController instance = null;

    public int reportCounter = 1;
    public ReportController() {
        reports = new HashMap<Integer, Report>();
    }
    public static ReportController getInstance(){
        if(instance == null){
            instance = new ReportController();
            }
            return instance;
    }

    public Report buildWeeklyReport( HashMap<Integer,Integer> productsAndAmount, LocalDate startDate,
                                     LocalDate endDate) {


        Report report = new Report(reportCounter, productsAndAmount, startDate, endDate);
        reports.put(reportCounter, report);

        reportCounter++;
        return report;
    }

    public Report builddmgItemReport(List<Item> items, LocalDate startDate, LocalDate endDate) {
        Report report = new DMGReport(reportCounter, items, startDate, endDate);
        reports.put(reportCounter, report);
        reportCounter++;
        return report;
    }
    public Report buildItemReport(List<Item> items, LocalDate startDate, LocalDate endDate) {
        Report report = new DMGReport(reportCounter, items, startDate, endDate);
        reports.put(reportCounter, report);
        reportCounter++;
        return report;
    }
    public Report buildexpiredItemReport(List<Item> items, LocalDate startDate, LocalDate endDate) {
        Report report = new Expiredreport(reportCounter, items, startDate, endDate);
        reports.put(reportCounter, report);
        reportCounter++;
        return report;
    }


    public Report buildProductReport( List<Integer> products, LocalDate startDate, LocalDate endDate) {


        Report report = new Report(products, reportCounter, startDate, endDate);

        reports.put(reportCounter, report);

        reportCounter++;
        return report;
    }
    //
    public Report getReport( int id) {



        if (reports.containsKey(id)) {
            return reports.get(id);
        } else {
            throw new IllegalArgumentException("No such report");
        }
    }
    public int getReportCounter(){
        return  reportCounter;
    }
    public void ADDdtoreport(ReportDTO reportDTO) throws SQLException {
        if(!reports.containsKey(reportDTO.getId())){
        HashMap<Integer,Integer> p_a= new HashMap<>();
p_a.put(reportDTO.getPid(),reportDTO.getAmount());
Report report = new Report(reportDTO.getId(),p_a, null, null);
reports.put(reportDTO.getId(), report);

    }
    else{
reports.get(reportDTO.getId()).getProductsAndAmount().put(reportDTO.getPid(), reportDTO.getAmount());
    }
    reportCounter=reportDTO.getId()+1;
    
//
}
public void printmin(){
    for(Report report: reports.values()){
        if(!report.getProductsAndAmount().isEmpty()&&report.getProductsAndAmount().containsKey(0)){
            System.out.println("Report ID: " + report.getReportID());
            for (Integer productId : report.getProductsAndAmount().keySet()) {
                System.out.printf("Product ID: %d    Amount: %d%n", productId, report.getProductsAndAmount().get(productId));
            }
    }
}
}
}