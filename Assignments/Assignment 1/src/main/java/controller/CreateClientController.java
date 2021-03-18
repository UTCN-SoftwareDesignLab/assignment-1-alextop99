package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
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
                    res = clientService.saveWOAccount(new ClientBuilder()
                            .setSurname(createClientView.getSurname())
                            .setFirstname(createClientView.getFirstname())
                            .setIDCardNumber(createClientView.getIDCard())
                            .setCnp(createClientView.getCNP())
                            .setAddress(createClientView.getAddress())
                            .build());


                } else if (debitSelection) {
                    Account account = new AccountBuilder()
                            .setType(clientService.findAccountTypeByTitle("debit"))
                            .setNumber(UUID.randomUUID().toString())
                            .setDateOfCreation(LocalDate.now())
                            .setAmountOfMoney(Double.valueOf(createClientView.getMoney()))
                            .build();

                    res = getBooleanNotification(account);

                } else {
                    Account account = new AccountBuilder()
                            .setType(clientService.findAccountTypeByTitle("credit"))
                            .setNumber(UUID.randomUUID().toString())
                            .setDateOfCreation(LocalDate.now())
                            .setAmountOfMoney(Double.valueOf(createClientView.getMoney()))
                            .build();
                    res = getBooleanNotification(account);
                }

                if(!res.hasErrors())
                    JOptionPane.showMessageDialog(createClientView.getContentPane(), "Client created successfully");
            }
        }
    }

    private Notification<Boolean> getBooleanNotification(Account account) {
        Notification<Boolean> res;
        res = clientService.save(account);

        if(res.hasErrors()) {
            JOptionPane.showMessageDialog(createClientView.getContentPane(), res.getFormattedErrors());
        }
        else {
            Client client = new ClientBuilder()
                    .setSurname(createClientView.getSurname())
                    .setFirstname(createClientView.getFirstname())
                    .setIDCardNumber(createClientView.getIDCard())
                    .setCnp(createClientView.getCNP())
                    .setAddress(createClientView.getAddress())
                    .setAccount(account)
                    .build();

            ClientValidator clientValidator = new ClientValidator(client);

            if(clientValidator.validate()) {
                res = clientService.save(client);
                if (res.hasErrors())
                    JOptionPane.showMessageDialog(createClientView.getContentPane(), res.getFormattedErrors());
            }
            else {
                res.addError(clientValidator.getErrors().get(0));
                JOptionPane.showMessageDialog(createClientView.getContentPane(), clientValidator.getErrors().get(0));
            }
        }
        return res;
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
