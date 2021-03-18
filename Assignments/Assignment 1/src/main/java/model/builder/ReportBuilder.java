package model.builder;

import model.Report;
import model.User;

import java.time.LocalDate;

public class ReportBuilder {

    private final Report report;

    public ReportBuilder() {
        report = new Report();
    }

    public ReportBuilder setId(Long id) {
        report.setId(id);
        return this;
    }

    public ReportBuilder setUser(User user) {
        report.setUser(user);
        return this;
    }

    public ReportBuilder setAction(String action) {
        report.setAction(action);
        return this;
    }

    public ReportBuilder setDate(LocalDate date) {
        report.setDate(date);
        return this;
    }

    public Report build() {
        return report;
    }
}
