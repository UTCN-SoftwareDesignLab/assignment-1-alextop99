package repository.report;

import model.Report;
import model.User;
import model.validation.Notification;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {
    Notification<Boolean> save(Report report);

    List<Report> getReportsForUser(User user, LocalDate startDate, LocalDate endDate);
}
