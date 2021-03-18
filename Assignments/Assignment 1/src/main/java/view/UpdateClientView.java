package view;

import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static database.Constants.AccountTypes.DEBIT;
import static javax.swing.BoxLayout.Y_AXIS;

public class UpdateClientView extends JFrame {
    private JLabel lbId;
    private JLabel lbIdVal;
    private JLabel lbSurname;
    private JTextField tfSurname;
    private JLabel lbFirstname;
    private JTextField tfFirstname;
    private JLabel lbIDCard;
    private JTextField tfIDCard;
    private JLabel lbCNP;
    private JTextField tfCNP;
    private JLabel lbAddress;
    private JTextField tfAddress;
    private JButton btnUpdateClient;

    private JLabel lbAccountId;
    private JLabel lbAccountIdVal;
    private JLabel lbAccountNumber;
    private JLabel lbAccountNumberVal;
    private JLabel lbDebit;
    
    private JCheckBox chbDebit;
    
    private JLabel lbCredit;
    
    private JCheckBox chbCredit;
    private JLabel lbMoneyAccount;
    private JTextField tfMoneyAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;
    private JButton btnCreateAccount;

    private JLabel lbMoneyDW;
    private JTextField tfMoneyDW;
    private JButton btnWithdraw;
    private JButton btnDeposit;
    private JButton btnPayBill;

    private JLabel lbMoneyTf;
    private JTextField tfMoneyTf;
    private JLabel lbAccountTf;
    private JTextField tfAccountTf;
    private JButton btnTransfer;

    private JButton btnBack;

    public UpdateClientView() throws HeadlessException {
        setSize(300, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(lbId);
        add(lbIdVal);
        add(lbSurname);
        add(tfSurname);
        add(lbFirstname);
        add(tfFirstname);
        add(lbIDCard);
        add(tfIDCard);
        add(lbCNP);
        add(tfCNP);
        add(lbAddress);
        add(tfAddress);
        add(btnUpdateClient);

        add(lbAccountId);
        add(lbAccountIdVal);
        add(lbAccountNumber);
        add(lbAccountNumberVal);
        add(lbDebit);
        add(chbDebit);
        add(lbCredit);
        add(chbCredit);
        add(lbMoneyAccount);
        add(tfMoneyAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);
        add(btnCreateAccount);

        add(lbMoneyDW);
        add(tfMoneyDW);
        add(btnWithdraw);
        add(btnDeposit);
        add(btnPayBill);

        add(lbMoneyTf);
        add(tfMoneyTf);
        add(lbAccountTf);
        add(tfAccountTf);
        add(btnTransfer);

        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        lbId = new JLabel("ID");
        lbIdVal = new JLabel("");
        lbSurname = new JLabel("Surname");
        tfSurname = new JTextField("");
        lbFirstname = new JLabel("Firstname");
        tfFirstname = new JTextField("");
        lbIDCard = new JLabel("Id Card");
        tfIDCard = new JTextField("");
        lbCNP = new JLabel("CNP");
        tfCNP = new JTextField("");
        lbAddress = new JLabel("Address");
        tfAddress = new JTextField("");
        btnUpdateClient = new JButton("Update Client");

        lbAccountId = new JLabel("Account ID");
        lbAccountIdVal = new JLabel("");
        lbAccountNumber = new JLabel("Account Number");
        lbAccountNumberVal = new JLabel("");
        lbDebit = new JLabel("Debit");
        chbDebit = new JCheckBox();
        lbCredit = new JLabel("Credit");
        chbCredit = new JCheckBox();
        lbMoneyAccount = new JLabel("Money");
        tfMoneyAccount = new JTextField("0.0");
        btnUpdateAccount = new JButton("Update Account");
        btnDeleteAccount = new JButton("Delete Account");
        btnCreateAccount = new JButton("Create Account");

        lbMoneyDW = new JLabel("Money");
        tfMoneyDW = new JTextField("0.0");
        btnWithdraw = new JButton("Withdraw");
        btnDeposit = new JButton("Deposit");
        btnPayBill = new JButton("Pay Bill");

        lbMoneyTf = new JLabel("Money");
        tfMoneyTf = new JTextField("0.0");
        lbAccountTf = new JLabel("Account Number");
        tfAccountTf = new JTextField("");
        btnTransfer = new JButton("Transfer");

        btnBack = new JButton("Back");
    }

    public void updateFields(Client client) {
        resetFields();
        lbIdVal.setText(client.getId().toString());
        tfSurname.setText(client.getSurname());
        tfFirstname.setText(client.getFirstname());
        tfIDCard.setText(client.getIDCardNumber());
        tfCNP.setText(client.getCnp());
        tfAddress.setText(client.getAddress());

        if(client.getAccount() != null) {
            lbAccountIdVal.setText(client.getAccount().getId().toString());
            lbAccountNumberVal.setText(client.getAccount().getNumber());
            if (client.getAccount().getType().getType().equals(DEBIT)) {
                chbDebit.setSelected(true);
            } else {
                chbCredit.setSelected(true);
            }
            tfMoneyAccount.setText(client.getAccount().getAmountOfMoney().toString());
        }
        else {
            lbAccountNumberVal.setText("The client has no account");
        }
    }

    private void resetFields() {
        lbIdVal.setText("");
        tfSurname.setText("");
        tfFirstname.setText("");
        tfIDCard.setText("");
        tfCNP.setText("");
        tfAddress.setText("");
        lbAccountIdVal.setText("");
        lbAccountNumberVal.setText("");
        chbDebit.setSelected(false);
        chbCredit.setSelected(false);
        tfMoneyAccount.setText("0.0");
        tfMoneyDW.setText("0.0");
        tfMoneyTf.setText("0.0");
        tfAccountTf.setText("");
    }

    public Long getID() {return Long.valueOf(lbIdVal.getText());}

    public String getSurname() { return tfSurname.getText();}

    public String getFirstname() { return tfFirstname.getText();}

    public String getIDCard() { return tfIDCard.getText();}

    public String getCNP() { return tfCNP.getText();}

    public String getAddress() {return tfAddress.getText();}

    public Long getAccountId() {return Long.valueOf(lbAccountIdVal.getText());}

    public String getAccountIdString() {return lbAccountIdVal.getText();}

    public String getAccountNumber() {return lbAccountNumberVal.getText();}

    public boolean getDebit() {return chbDebit.isSelected();}

    public boolean getCredit() {return chbCredit.isSelected();}

    public Double getMoneyAccount() {return Double.valueOf(tfMoneyAccount.getText());}

    public Double getMoneyDW() {return Double.valueOf(tfMoneyDW.getText());}

    public Double getMoneyTf() {return Double.valueOf(tfMoneyTf.getText());}

    public String getDestAccount() {return tfAccountTf.getText();}

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        btnDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void setCreateAccountButtonListener(ActionListener createAccountButtonListener) {
        btnCreateAccount.addActionListener(createAccountButtonListener);
    }

    public void setWithdrawButtonListener(ActionListener withdrawButtonListener) {
        btnWithdraw.addActionListener(withdrawButtonListener);
    }

    public void setDepositButtonListener(ActionListener depositButtonListener) {
        btnDeposit.addActionListener(depositButtonListener);
    }

    public void setPayBillButtonListener(ActionListener payBillButtonListener) {
        btnPayBill.addActionListener(payBillButtonListener);
    }

    public void setTransferButtonListener(ActionListener transferButtonListener) {
        btnTransfer.addActionListener(transferButtonListener);
    }

    public void setBackButtonListener(ActionListener backButtonListener) {
        btnBack.addActionListener(backButtonListener);
    }

    public void setVisible() {
        resetFields();
        this.setVisible(true);
    }

    public void setNotVisible() {
        this.setVisible(false);
    }
}
