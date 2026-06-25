package Backend.BusinessLayer.Stock.objects;

import java.time.LocalDate;

import java.util.List;

public  class Expiredreport  extends Report{

   

    // for only items
    public Expiredreport(int reportID, List<Item> items, LocalDate startDate, LocalDate endDate) {
        super(reportID, items, startDate, endDate);
    }

  
  
 
}
