package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static javax.swing.BoxLayout.Y_AXIS;

public class UpdateUserView extends JFrame{
    private JLabel lbId;
    private JLabel lbIdVal;
    private JLabel lbUsername;
    private JTextField tfUsername;
    private JLabel lbPassword;
    private JTextField tfPassword;
    private JLabel lbEmployee;
    private JCheckBox chbEmployee;
    private JLabel lbAdministrator;
    private JCheckBox chbAdministrator;

    private JButton btnUpdate;
    private JButton btnBack;

    public UpdateUserView() throws HeadlessException {
        setSize(300, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lbId);
        add(lbIdVal);
        add(lbUsername);
        add(tfUsername);
        add(lbPassword);
        add(tfPassword);
        add(lbEmployee);
        add(chbEmployee);
        add(lbAdministrator);
        add(chbAdministrator);
        add(btnUpdate);
        add(btnBack);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        lbId = new JLabel("ID");
        lbIdVal = new JLabel("");
        lbUsername = new JLabel("Username");
        tfUsername = new JTextField();
        lbPassword = new JLabel("Password");
        tfPassword = new JTextField();
        lbEmployee = new JLabel("Employee");
        chbEmployee = new JCheckBox();
        lbAdministrator = new JLabel("Administrator");
        chbAdministrator = new JCheckBox();
        btnUpdate = new JButton("Update");
        btnBack = new JButton("Back");
    }

    public void updateFields(User user) {
        lbIdVal.setText(user.getId().toString());
        tfUsername.setText(user.getUsername());
        tfPassword.setText("");
        if(user.getRole().getRole().equals(ADMINISTRATOR)) {
            chbAdministrator.setSelected(true);
        }
        else {
            chbEmployee.setSelected(true);
        }
    }

    private void resetFields() {
        tfUsername.setText("");
        tfPassword.setText("");
        chbEmployee.setSelected(false);
        chbAdministrator.setSelected(false);
    }

    public Long getID() {return Long.valueOf(lbIdVal.getText());}

    public String getUsername() { return tfUsername.getText();}

    public String getPassword() { return tfPassword.getText();}

    public boolean getEmployee() { return chbEmployee.isSelected();}

    public boolean getAdministrator() { return chbAdministrator.isSelected();}

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        btnUpdate.addActionListener(updateButtonListener);
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
