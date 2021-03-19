package view;

import model.dto.AccountDTO;
import model.dto.ClientDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class CreateClientView extends JFrame{
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
    private JLabel lbDebit;
    private JCheckBox chbDebit;
    private JLabel lbCredit;
    private JCheckBox chbCredit;
    private JLabel lbMoney;
    private JTextField tfMoney;

    private JButton btnCreate;
    private JButton btnBack;

    public CreateClientView() throws HeadlessException {
        setSize(300, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
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
        add(lbDebit);
        add(chbDebit);
        add(lbCredit);
        add(chbCredit);
        add(lbMoney);
        add(tfMoney);
        add(btnCreate);
        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        lbSurname = new JLabel("Surname");
        tfSurname = new JTextField();
        lbFirstname = new JLabel("Firstname");
        tfFirstname = new JTextField();
        lbIDCard = new JLabel("ID Card");
        tfIDCard = new JTextField();
        lbCNP = new JLabel("CNP");
        tfCNP = new JTextField();
        lbAddress = new JLabel("Address");
        tfAddress = new JTextField();
        lbDebit = new JLabel("Debit");
        chbDebit = new JCheckBox();
        lbCredit = new JLabel("Credit");
        chbCredit = new JCheckBox();
        lbMoney = new JLabel("Money");
        tfMoney = new JTextField("0.0");
        btnCreate = new JButton("Create");
        btnBack = new JButton("Back");
    }

    private void resetFields() {
        tfSurname.setText("");
        tfFirstname.setText("");
        tfIDCard.setText("");
        tfCNP.setText("");
        tfAddress.setText("");
        chbDebit.setSelected(false);
        chbCredit.setSelected(false);
        tfMoney.setText("0.0");
    }

    public ClientDTO getClientDTO() {
        return new ClientDTO(
                tfSurname.getText(),
                tfFirstname.getText(),
                tfIDCard.getText(),
                tfCNP.getText(),
                tfAddress.getText()
        );
    }

    public AccountDTO getAccountDTO() {
        return new AccountDTO(
                Double.valueOf(tfMoney.getText())
        );
    }

    public String getSurname() { return tfSurname.getText();}

    public String getFirstname() { return tfFirstname.getText();}

    public String getIDCard() { return tfIDCard.getText();}

    public String getCNP() { return tfCNP.getText();}

    public String getAddress() { return tfAddress.getText();}

    public String getMoney() { return tfMoney.getText();}

    public boolean getDebit() { return chbDebit.isSelected();}

    public boolean getCredit() { return chbCredit.isSelected();}

    public void setCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);
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
