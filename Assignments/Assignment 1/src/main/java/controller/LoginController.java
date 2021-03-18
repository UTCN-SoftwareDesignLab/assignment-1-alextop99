package controller;

import model.Report;
import model.User;
import model.builder.ReportBuilder;
import model.validation.Notification;
import service.report.ReportService;
import service.user.AuthenticationService;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import static database.Constants.Roles.*;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final ReportService reportService;
    private final AdministratorView administratorView;
    private final EmployeeView employeeView;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, AdministratorView administratorView, EmployeeView employeeView, ReportService reportService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.administratorView = administratorView;
        this.employeeView = employeeView;
        this.reportService = reportService;
        loginView.setLoginButtonListener(new LoginButtonListener());
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                if (loginNotification.getResult().getRole().getRole().equals(ADMINISTRATOR))
                    administratorView.setVisible();
                else if (loginNotification.getResult().getRole().getRole().equals(EMPLOYEE)) {
                    Report report = new ReportBuilder()
                            .setId((long) -1)
                            .setUser(loginNotification.getResult())
                            .setAction("Login")
                            .setDate(LocalDate.now())
                            .build();
                    reportService.save(report);
                    employeeView.setUserId(loginNotification.getResult().getId());
                    employeeView.setVisible();
                }
                loginView.setNotVisible();
            }
        }
    }
}
