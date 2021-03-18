package service.report;

import model.Report;
import model.User;
import model.validation.Notification;
import repository.report.ReportRepository;
import repository.user.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class ReportServiceMySQL implements ReportService{
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportServiceMySQL(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification<Boolean> save(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public List<Report> getReportsForUser(Long userId, LocalDate startDate, LocalDate endDate) {
        return reportRepository.getReportsForUser(userRepository.findById(userId).getResult(), startDate, endDate);
    }

    @Override
    public Notification<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
