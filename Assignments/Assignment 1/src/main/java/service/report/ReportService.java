package service.report;

import model.Report;
import model.User;
import model.validation.Notification;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Notification<Boolean> save(Report report);

    List<Report> getReportsForUser(Long userId, LocalDate startDate, LocalDate endDate);

    Notification<User> findById(Long userId);
}
