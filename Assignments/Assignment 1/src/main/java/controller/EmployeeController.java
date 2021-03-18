package controller;

import model.Client;
import model.Report;
import model.builder.ReportBuilder;
import service.client.ClientService;
import service.report.ReportService;
import view.CreateClientView;
import view.EmployeeView;
import view.LoginView;
import view.UpdateClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final ClientService clientService;
    private final ReportService reportService;
    private final CreateClientView createClientView;
    private final UpdateClientView updateClientView;
    private final LoginView loginView;

    public EmployeeController(EmployeeView employeeView, ClientService clientService, LoginView loginView, CreateClientView createClientView, UpdateClientView updateClientView, ReportService reportService) {
        this.employeeView = employeeView;
        this.clientService = clientService;
        this.loginView = loginView;
        this.createClientView = createClientView;
        this.updateClientView = updateClientView;
        this.reportService = reportService;
        employeeView.setCreateButtonListener(new EmployeeController.CreateButtonListener());
        employeeView.setUTAButtonListener(new EmployeeController.UTAButtonListener());
        employeeView.setDeleteButtonListener(new EmployeeController.DeleteButtonListener());
        employeeView.setBackButtonListener(new EmployeeController.BackButtonListener());
        updateAllDataInView();
    }

    private class CreateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Report report = new ReportBuilder()
                    .setId((long) -1)
                    .setUser(reportService.findById(employeeView.getUserId()).getResult())
                    .setAction("Add new client")
                    .setDate(LocalDate.now())
                    .build();
            reportService.save(report);
            employeeView.setNotVisible();
            createClientView.setVisible();
        }
    }

    private class UTAButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long cbSelection = employeeView.getCbSelectionItem();
            if(cbSelection == -1)
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select a valid ID!!");
            else {
                Report report = new ReportBuilder()
                        .setId((long) -1)
                        .setUser(reportService.findById(employeeView.getUserId()).getResult())
                        .setAction("Edit existing client and account information")
                        .setDate(LocalDate.now())
                        .build();
                reportService.save(report);
                updateClientView.setVisible();
                updateClientView.updateFields(clientService.findById(cbSelection).getResult());
                employeeView.setNotVisible();
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long cbSelection = employeeView.getCbSelectionItem();

            if(cbSelection == -1)
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select a valid ID!!");
            else {
                Client client = clientService.findById(cbSelection).getResult();
                if(client.getAccount() != null)
                    clientService.deleteAccountByNumber(client.getAccount().getNumber());
                clientService.deleteClientById(cbSelection);
                Report report = new ReportBuilder()
                        .setId((long) -1)
                        .setUser(reportService.findById(employeeView.getUserId()).getResult())
                        .setAction("Delete existing client and account information")
                        .setDate(LocalDate.now())
                        .build();
                reportService.save(report);
                updateAllDataInView();
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client with ID " + cbSelection.toString() + " has been deleted!!");
            }
        }
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.setVisible();
            employeeView.setNotVisible();
        }
    }

    private void updateAllDataInView() {
        employeeView.updateAllDataInView(clientService.getAllClients(), clientService.getIdList());
    }
}
