package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class CreateUserView extends JFrame{
    private JLabel lbUsername;
    private JTextField tfUsername;
    private JLabel lbPassword;
    private JTextField tfPassword;
    private JLabel lbEmployee;
    private JCheckBox chbEmployee;
    private JLabel lbAdministrator;
    private JCheckBox chbAdministrator;

    private JButton btnCreate;
    private JButton btnBack;

    public CreateUserView() throws HeadlessException {
        setSize(300, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lbUsername);
        add(tfUsername);
        add(lbPassword);
        add(tfPassword);
        add(lbEmployee);
        add(chbEmployee);
        add(lbAdministrator);
        add(chbAdministrator);
        add(btnCreate);
        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        lbUsername = new JLabel("Username");
        tfUsername = new JTextField();
        lbPassword = new JLabel("Password");
        tfPassword = new JTextField();
        lbEmployee = new JLabel("Employee");
        chbEmployee = new JCheckBox();
        lbAdministrator = new JLabel("Administrator");
        chbAdministrator = new JCheckBox();
        btnCreate = new JButton("Create");
        btnBack = new JButton("Back");
    }

    private void resetFields() {
        tfUsername.setText("");
        tfPassword.setText("");
        chbEmployee.setSelected(false);
        chbAdministrator.setSelected(false);
    }

    public String getUsername() { return tfUsername.getText();}

    public String getPassword() { return tfPassword.getText();}

    public boolean getEmployee() { return chbEmployee.isSelected();}

    public boolean getAdministrator() { return chbAdministrator.isSelected();}

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
