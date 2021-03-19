package controller;

import static database.Constants.Roles.ADMINISTRATOR;

import model.Report;
import model.validation.Notification;
import service.report.ReportCSVGenerator;
import service.report.ReportService;
import service.user.UserService;
import view.AdministratorView;
import view.CreateUserView;
import view.LoginView;
import view.UpdateUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AdministratorController {
    private final AdministratorView administratorView;
    private final UserService userService;
    private final ReportService reportService;
    private final CreateUserView createUserView;
    private final UpdateUserView updateUserView;
    private final LoginView loginView;

    public AdministratorController(AdministratorView administratorView, UserService userService, LoginView loginView, CreateUserView createUserView, UpdateUserView updateUserView, ReportService reportService) {
        this.administratorView = administratorView;
        this.userService = userService;
        this.loginView = loginView;
        this.createUserView = createUserView;
        this.updateUserView = updateUserView;
        this.reportService = reportService;
        administratorView.setCreateButtonListener(new AdministratorController.CreateButtonListener());
        administratorView.setUpdateButtonListener(new AdministratorController.UpdateButtonListener());
        administratorView.setDeleteButtonListener(new AdministratorController.DeleteButtonListener());
        administratorView.setBackButtonListener(new AdministratorController.BackButtonListener());
        administratorView.setReportGeneratorButtonListener(new AdministratorController.GenerateReportButtonListener());
        updateAllDataInView();
    }

    private class CreateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            administratorView.setNotVisible();
            createUserView.setVisible();
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long cbSelection = administratorView.getCbSelectionItem();
            if(cbSelection == -1)
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a valid ID!!");
            else {
                administratorView.setNotVisible();
                updateUserView.setVisible();
                updateUserView.updateFields(userService.findById(cbSelection).getResult());
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long cbSelection = administratorView.getCbSelectionItem();

            if(cbSelection == -1)
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a valid ID!!");
            else if(userService.getAllAdmins().size() == 1 && userService.findById(cbSelection).getResult().getRole().getRole().equals(ADMINISTRATOR)) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "You need at least one admin!!");
            }
            else {
                userService.deleteUserById(cbSelection);
                updateAllDataInView();
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "User with ID " + cbSelection.toString() + " has been deleted!!");
            }
        }
    }

    private class GenerateReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Long cbSelection = administratorView.getCbSelectionItem();

            if(cbSelection == -1)
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a valid ID!!");
            else if(userService.findById(cbSelection).getResult().getRole().getRole().equals(ADMINISTRATOR))
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Reports can be generated only on employees!!");
            else
            {
                LocalDate startDate = administratorView.getStartDate();
                LocalDate endDate = administratorView.getEndDate();

                reportDateChecker(cbSelection, startDate, endDate);
            }
        }
    }

    private void reportDateChecker(Long cbSelection, LocalDate startDate, LocalDate endDate) {
        if(startDate == null) {
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a start Date!!");
        }
        else if(startDate.compareTo(LocalDate.now()) > 0) {
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a valid start Date!!");
        }
        else if(endDate == null) {
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a end Date!!");
        }
        else if(endDate.compareTo(LocalDate.now()) > 0) {
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a valid end Date!!");
        }
        else if(startDate.compareTo(endDate) > 0) {
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Start date cannot be after end date!!");
        }
        else {
            List<Report> reports = reportService.getReportsForUser(cbSelection, startDate, endDate);
            Notification<String> res = ReportCSVGenerator.generateCSV(reports, userService.findById(cbSelection).getResult().getUsername());
            if(res.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), res.getFormattedErrors());
            }
            else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), res.getResult());
            }
        }
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.setVisible();
            administratorView.setNotVisible();
        }
    }

    private void updateAllDataInView() {
        administratorView.updateAllDataInView(userService.getAllUsers(), userService.getIdList());
    }


}