package repository.report;

import model.Report;
import model.User;
import model.builder.ReportBuilder;
import model.validation.Notification;
import repository.user.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.REPORT;

public class ReportRepositoryMySQL implements ReportRepository{
    private final Connection connection;
    private final UserRepository userRepository;

    public ReportRepositoryMySQL(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
    }

    @Override
    public Notification<Boolean> save(Report report) {
        Notification<Boolean> saveNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + REPORT + "` values (null, ?, ?, ?)");
            insertUserStatement.setLong(1, report.getUser().getId());
            insertUserStatement.setString(2, report.getAction());
            insertUserStatement.setDate(3, Date.valueOf(report.getDate()));
            insertUserStatement.executeUpdate();

            saveNotification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("An error has happened in database!");
            saveNotification.setResult(false);
        }
        return saveNotification;
    }

    @Override
    public List<Report> getReportsForUser(User user, LocalDate startDate, LocalDate endDate) {
        try {
            List<Report> reports = new ArrayList<>();

            Statement statement = connection.createStatement();
            String fetchReportSql = "Select * from `" + REPORT + "` where `user_id` = " + user.getId() + " and `actionDate` between '" + Date.valueOf(startDate) + "' and '" + Date.valueOf(endDate) + "'";
            ResultSet reportResultSet = statement.executeQuery(fetchReportSql);

            while (reportResultSet.next()) {
                Report report = new ReportBuilder()
                        .setId(reportResultSet.getLong("id"))
                        .setUser(userRepository.findById(reportResultSet.getLong("user_id")).getResult())
                        .setAction(reportResultSet.getString("action"))
                        .setDate(reportResultSet.getDate("actionDate").toLocalDate())
                        .build();
                reports.add(report);
            }

            return reports;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
