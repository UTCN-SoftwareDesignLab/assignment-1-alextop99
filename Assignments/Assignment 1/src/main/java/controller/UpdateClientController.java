package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import service.client.ClientService;
import view.EmployeeView;
import view.UpdateClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.UUID;

import static database.Constants.AccountTypes.CREDIT;
import static database.Constants.AccountTypes.DEBIT;

public class UpdateClientController {
    private final UpdateClientView updateClientView;
    private final ClientService clientService;
    private final EmployeeView employeeView;

    public UpdateClientController(UpdateClientView updateClientView, ClientService clientService, EmployeeView employeeView) {
        this.updateClientView = updateClientView;
        this.clientService = clientService;
        this.employeeView = employeeView;
        updateClientView.setBackButtonListener(new UpdateClientController.BackButtonListener());
        updateClientView.setUpdateClientButtonListener(new UpdateClientController.UpdateClientButtonListener());
        updateClientView.setUpdateAccountButtonListener(new UpdateClientController.UpdateAccountButtonListener());
        updateClientView.setDeleteAccountButtonListener(new UpdateClientController.DeleteAccountButtonListener());
        updateClientView.setCreateAccountButtonListener(new UpdateClientController.CreateAccountButtonListener());
        updateClientView.setWithdrawButtonListener(new UpdateClientController.WithdrawButtonListener());
        updateClientView.setDepositButtonListener(new UpdateClientController.DepositButtonListener());
        updateClientView.setPayBillButtonListener(new UpdateClientController.PayBillButtonListener());
        updateClientView.setTransferButtonListener(new UpdateClientController.TransferButtonListener());
    }

    private class UpdateClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Client client = new ClientBuilder()
                    .setId(updateClientView.getID())
                    .setSurname(updateClientView.getSurname())
                    .setFirstname(updateClientView.getFirstname())
                    .setCnp(updateClientView.getCNP())
                    .setIDCardNumber(updateClientView.getIDCard())
                    .setAddress(updateClientView.getAddress())
                    .build();

            ClientValidator clientValidator = new ClientValidator(client);

            if(clientValidator.validate()) {
                Notification<Boolean> res = clientService.update(client);
                if(res.hasErrors()) {
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "An error has occurred in the database!");
                }
                else {
                    updateView();
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Client has been updated!");
                }
            }
            else {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), clientValidator.getErrors().get(0));
            }
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean debit = updateClientView.getDebit();
            boolean credit = updateClientView.getCredit();
            if(updateClientView.getAccountIdString().isEmpty()) {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have an account");
            }
            else {
                if (debit && credit) {
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The account can be either debit and credit!");
                } else {
                    if (!debit && !credit) {
                        JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The account can be either debit and credit!");
                    } else if (debit) {
                        Account account = new AccountBuilder()
                                .setId(updateClientView.getAccountId())
                                .setNumber(updateClientView.getAccountNumber())
                                .setAmountOfMoney(updateClientView.getMoneyAccount())
                                .setType(clientService.findAccountTypeByTitle(DEBIT))
                                .build();
                        Notification<Boolean> res = clientService.updateAccount(account);
                        if (res.hasErrors()) {
                            JOptionPane.showMessageDialog(updateClientView.getContentPane(), "An error has occurred in the database!");
                        } else {
                            updateView();
                            JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Account has been updated!");
                        }
                    } else {
                        Account account = new AccountBuilder()
                                .setId(updateClientView.getAccountId())
                                .setNumber(updateClientView.getAccountNumber())
                                .setAmountOfMoney(updateClientView.getMoneyAccount())
                                .setType(clientService.findAccountTypeByTitle(CREDIT))
                                .build();
                        Notification<Boolean> res = clientService.updateAccount(account);
                        if (res.hasErrors()) {
                            JOptionPane.showMessageDialog(updateClientView.getContentPane(), "An error has occurred in the database!");
                        } else {
                            updateView();
                            JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Account has been updated!");
                        }
                    }
                }
            }
        }
    }

    private class WithdrawButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Double moneyToWithdraw = updateClientView.getMoneyDW();
            if(updateClientView.getAccountIdString().isEmpty()) {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have an account");
            }
            else {
                Account account = clientService.findByNumber(updateClientView.getAccountNumber());
                if(account.getAmountOfMoney() < moneyToWithdraw) {
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have enough money!");
                }
                else {
                    account.setAmountOfMoney(account.getAmountOfMoney() - moneyToWithdraw);
                    clientService.updateAccount(account);
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The operation has succeeded");
                    updateView();
                }
            }
        }
    }

    private class PayBillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Double moneyToPay = updateClientView.getMoneyDW();
            if(updateClientView.getAccountIdString().isEmpty()) {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have an account");
            }
            else {
                Account account = clientService.findByNumber(updateClientView.getAccountNumber());
                if(account.getAmountOfMoney() < moneyToPay) {
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have enough money!");
                }
                else {
                    account.setAmountOfMoney(account.getAmountOfMoney() - moneyToPay);
                    clientService.updateAccount(account);
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The operation has succeeded!");
                    updateView();
                }
            }
        }
    }

    private class DepositButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Double moneyToDeposit = updateClientView.getMoneyDW();
            if(updateClientView.getAccountIdString().isEmpty()) {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have an account");
            }
            else {
                Account account = clientService.findByNumber(updateClientView.getAccountNumber());
                account.setAmountOfMoney(account.getAmountOfMoney() + moneyToDeposit);
                clientService.updateAccount(account);
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The operation has succeeded!");
                updateView();
            }
        }
    }

    private class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Double moneyToTransfer = updateClientView.getMoneyTf();
            if(updateClientView.getAccountIdString().isEmpty()) {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have an account");
            }
            else {
                Account account = clientService.findByNumber(updateClientView.getAccountNumber());
                if(account.getAmountOfMoney() < moneyToTransfer) {
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have enough money!");
                }
                else {
                    Account destAccount = clientService.findByNumber(updateClientView.getDestAccount());
                    if(destAccount == null) {
                        JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Destination account does not exist!");
                    }
                    else {
                        account.setAmountOfMoney(account.getAmountOfMoney() - moneyToTransfer);
                        clientService.updateAccount(account);
                        destAccount.setAmountOfMoney(destAccount.getAmountOfMoney() + moneyToTransfer);
                        clientService.updateAccount(destAccount);
                        JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The operation has succeeded");
                        updateView();
                    }
                }
            }
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(updateClientView.getAccountIdString().isEmpty()) {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client does not have an account");
            }
            else {
                clientService.deleteAccountByNumber(updateClientView.getAccountNumber());
                updateView();
            }
        }
    }

    private class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(updateClientView.getAccountIdString().isEmpty()) {
                boolean debitSelection = updateClientView.getDebit();
                boolean creditSelection = updateClientView.getCredit();
                if(debitSelection && creditSelection)
                    JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Account must be either debit or credit");
                else {
                    if (!debitSelection && !creditSelection) {
                        JOptionPane.showMessageDialog(updateClientView.getContentPane(), "Account must be either debit or credit");
                    } else if (debitSelection) {
                        Account account = new AccountBuilder()
                                .setType(clientService.findAccountTypeByTitle(DEBIT))
                                .setNumber(UUID.randomUUID().toString())
                                .setDateOfCreation(LocalDate.now())
                                .setAmountOfMoney(updateClientView.getMoneyAccount())
                                .build();

                        Notification<Boolean> res = clientService.save(account);

                        if(res.hasErrors()) {
                            JOptionPane.showMessageDialog(updateClientView.getContentPane(), "An error has occurred in the database!");
                        }
                        else {
                            clientService.addAccountToClient(account.getNumber(), updateClientView.getID());
                        }
                    } else {
                        Account account = new AccountBuilder()
                                .setType(clientService.findAccountTypeByTitle(CREDIT))
                                .setNumber(UUID.randomUUID().toString())
                                .setDateOfCreation(LocalDate.now())
                                .setAmountOfMoney(updateClientView.getMoneyAccount())
                                .build();

                        Notification<Boolean> res = clientService.save(account);

                        if(res.hasErrors()) {
                            JOptionPane.showMessageDialog(updateClientView.getContentPane(), "An error has occurred in the database!");
                        }
                        else {
                            clientService.addAccountToClient(account.getNumber(), updateClientView.getID());
                        }
                    }
                }
                updateView();
            }
            else {
                JOptionPane.showMessageDialog(updateClientView.getContentPane(), "The client already has an account");
            }
        }
    }

    private void updateView() {
        updateClientView.updateFields(clientService.findById(updateClientView.getID()).getResult());
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateClientView.setNotVisible();
            employeeView.updateAllDataInView(clientService.getAllClients(), clientService.getIdList());
            employeeView.setVisible();
        }
    }
}
