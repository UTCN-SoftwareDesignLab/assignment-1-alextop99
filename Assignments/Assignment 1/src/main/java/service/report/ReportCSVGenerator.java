package service.report;

import model.Report;
import model.validation.Notification;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReportCSVGenerator {
    public static Notification<String> generateCSV(List<Report> reports, String username) {
        Notification<String> csvNotification = new Notification<>();

        List<String> headers= Arrays.asList("Username","Action","Action Date");
        StringBuilder objectsCommaSeparated = new StringBuilder(String.join(",", headers));
        objectsCommaSeparated.append("\n");

        for(Report report : reports) {
            List<String> contents = Arrays.asList(username,report.getAction(),report.getDate().toString());
            objectsCommaSeparated.append(String.join(",", contents));
            objectsCommaSeparated.append("\n");
        }

        try {
            String filename = "Report_" + username + "_" + LocalDate.now().toString() +".csv";
            PrintWriter out = new PrintWriter(filename);
            out.write(objectsCommaSeparated.toString());
            out.close();
            csvNotification.setResult("Report has been generated successfully!!");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            csvNotification.addError("An error has occurred while creating the report!!");
        }
        return csvNotification;
    }
}
