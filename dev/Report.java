package src.domain.Objects;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {
    private static int idCounter = 0;
    private final int id;
    protected ReportType ReportReason;
    private final Date ReportDate;
    private final String ReportName;
    private final String ReportDescription;

    public Report(Date reportDate, String reportName,ReportType ReportReason,String discription) {
        this.id = idCounter++;
        this.ReportDate = reportDate;
        this.ReportName = reportName;
        this.ReportReason=ReportReason;
        this.ReportDescription = discription;
        writeToCSV();
    }
    public int getId() {
        return id;
    }
    public String getReportReason() {
        return ReportReason.toString();
    }
    public Date getReportDate() {
        return ReportDate;
    }
    public String getReportName() {
        return ReportName;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("** Report **\n---------------------------\nReport ID: %d\nReport Name: %s\nProduction date: %s\n---------------------------\nReport Reason: %s\n%s\n", this.getId(), this.getReportName(), sdf.format(this.getReportDate()), this.getReportReason(), ReportDescription);
    }


    private void writeToCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String csvFileName = "docs/Reports/" + "report_" + this.getId() + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            writer.println("Report");
            writer.printf("%s\n%s\n%s\n%s\n",
                    "ID: " + this.getId(),
                    "Report name" + this.getReportName(),
                    "Date: " + sdf.format(this.getReportDate()),
                    "report reason: " + this.getReportReason()
            );
            writer.println(this.ReportDescription);
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
