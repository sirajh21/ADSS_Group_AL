import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static int idCounter = 0;

    private final int id;
    private final Date ate;
    private final String name;
    private final String Description;

    protected status_report reportReason;

    public Report(Date reportDate, String reportName, status_report reportReason, String description) {
        this.id = idCounter++;
        this.ate = reportDate;
        this.name = reportName;
        this.reportReason = reportReason;
        this.Description = description;
        writeToCsv();
    }

    public int getId() {
        return id;
    }

    public String getReportReason() {
        return reportReason.toString();
    }

    public Date getAte() {
        return ate;
    }

    public String getReportName() {
        return name;
    }

    private String formatDate() {
        return new SimpleDateFormat(DATE_PATTERN).format(ate);
    }

    @Override
    public String toString() {
        return String.format(
                "** Report **\n" +
                        "---------------------------\n" +
                        "Report ID: %d\n" +
                        "Report Name: %s\n" +
                        "Production date: %s\n" +
                        "---------------------------\n" +
                        "Report Reason: %s\n" +
                        "%s\n",
                getId(),
                getReportName(),
                formatDate(),
                getReportReason(),
                Description
        );
    }

    private void writeToCsv() {
        String folderPath = "docs/Reports";
        String filePath = folderPath + "/report_" + getId() + ".csv";

        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.println("Report");
                writer.printf("%s\n%s\n%s\n%s\n",
                        "ID: " + getId(),
                        "Report name" + getReportName(),
                        "Date: " + formatDate(),
                        "report reason: " + getReportReason()
                );
                writer.println(Description);
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // compatibility
    private void writeToCSV() {
        writeToCsv();
    }
}