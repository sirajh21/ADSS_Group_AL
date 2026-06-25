package Backend.BusinessLayer.Stock.objects;
import java.time.LocalDate;

import java.util.List;


public class DMGReport extends Report {
        // for only items
        public DMGReport(int reportID, List<Item> items, LocalDate startDate, LocalDate endDate) {
            super(reportID, items, startDate, endDate);
        }
    
     
}
