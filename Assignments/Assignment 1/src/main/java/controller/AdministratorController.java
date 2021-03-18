package controller;

import static database.Constants.Roles.ADMINISTRATOR;

import model.Report;
import service.report.ReportService;
import service.user.UserService;
import view.AdministratorView;
import view.CreateUserView;
import view.LoginView;
import view.UpdateUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
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
            else {
                if(userService.getAllAdmins().size() == 1 && userService.findById(cbSelection).getResult().getRole().getRole().equals(ADMINISTRATOR)) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "You need at least one admin!!");
                }
                else {
                    userService.deleteUserById(cbSelection);
                    updateAllDataInView();
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "User with ID " + cbSelection.toString() + " has been deleted!!");
                }
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

                if(startDate == null)
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a start Date!!");
                else if(startDate.compareTo(LocalDate.now()) > 0)
                        JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a valid start Date!!");
                else if(endDate == null)
                            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a end Date!!");
                else if(endDate.compareTo(LocalDate.now()) > 0)
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Please select a valid end Date!!");
                else if(startDate.compareTo(endDate) > 0)
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Start date cannot be after end date!!");
                else {
                    List<Report> reports = reportService.getReportsForUser(cbSelection, startDate, endDate);
                    generateCSV(reports, userService.findById(cbSelection).getResult().getUsername());
                }
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

    private void generateCSV(List<Report> reports, String username) {
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
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "Report has been generated successfully!!");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(administratorView.getContentPane(), "An error has occurred while creating the report!!");
        }
    }
}