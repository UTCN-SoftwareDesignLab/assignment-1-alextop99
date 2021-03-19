package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.dto.AccountDTO;
import model.validation.ClientValidator;
import model.validation.Notification;
import service.client.ClientService;
import view.CreateClientView;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.UUID;

import static database.Constants.AccountTypes.DEBIT;

public class CreateClientController {
    private final CreateClientView createClientView;
    private final ClientService clientService;
    private final EmployeeView employeeView;

    public CreateClientController(CreateClientView createClientView, ClientService clientService, EmployeeView employeeView) {
        this.createClientView = createClientView;
        this.clientService = clientService;
        this.employeeView = employeeView;
        createClientView.setCreateButtonListener(new CreateClientController.CreateButtonListener());
        createClientView.setBackButtonListener(new CreateClientController.BackButtonListener());
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean debitSelection = createClientView.getDebit();
            boolean creditSelection = createClientView.getCredit();
            if(debitSelection && creditSelection)
                JOptionPane.showMessageDialog(createClientView.getContentPane(), "Account must be either debit or credit");
            else {
                Notification<Boolean> res;

                if (!debitSelection && !creditSelection) {
                    res = clientService.saveWOAccount(createClientView.getClientDTO());
                } else if (debitSelection) {
                    AccountDTO accountDTO = createClientView.getAccountDTO();
                    accountDTO.setType(DEBIT);

                    res = clientService.save(createClientView.getClientDTO(), accountDTO);

                } else {
                    AccountDTO accountDTO = createClientView.getAccountDTO();
                    accountDTO.setType(DEBIT);

                    res = clientService.save(createClientView.getClientDTO(), accountDTO);
                }

                if(!res.hasErrors()) {
                    JOptionPane.showMessageDialog(createClientView.getContentPane(), "Client created successfully");
                }
                else {
                    JOptionPane.showMessageDialog(createClientView.getContentPane(), res.getFormattedErrors());
                }
            }
        }
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            createClientView.setNotVisible();
            employeeView.updateAllDataInView(clientService.getAllClients(), clientService.getIdList());
            employeeView.setVisible();
        }
    }
}
