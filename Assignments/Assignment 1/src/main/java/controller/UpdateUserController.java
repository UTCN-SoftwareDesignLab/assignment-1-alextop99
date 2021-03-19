package controller;

import model.dto.UserDTO;
import model.validation.Notification;
import service.user.UserService;
import view.AdministratorView;
import view.UpdateUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class UpdateUserController {
    private final UpdateUserView updateUserView;
    private final UserService userService;
    private final AdministratorView administratorView;

    public UpdateUserController(UpdateUserView updateUserView, UserService userService, AdministratorView administratorView) {
        this.updateUserView = updateUserView;
        this.userService = userService;
        this.administratorView = administratorView;
        updateUserView.setUpdateButtonListener(new UpdateUserController.UpdateButtonListener());
        updateUserView.setBackButtonListener(new UpdateUserController.BackButtonListener());
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean employee = updateUserView.getEmployee();
            boolean administrator = updateUserView.getAdministrator();
            if(employee && administrator) {
                JOptionPane.showMessageDialog(updateUserView.getContentPane(), "User must be either administrator or employee!!");
            }
            else {
                Notification<Boolean> res = new Notification<>();
                if(!employee && !administrator) {
                    res.addError("User must be either administrator or employee!!");
                }
                else {
                    UserDTO userDTO = updateUserView.getUserDTO();
                    if(administrator) {
                        userDTO.setRole(ADMINISTRATOR);
                    }
                    else {
                        userDTO.setRole(EMPLOYEE);
                    }
                    res = userService.update(userDTO);
                }

                if(!res.hasErrors()) {
                    JOptionPane.showMessageDialog(updateUserView.getContentPane(), "User updated successfully");
                }
                else {
                    JOptionPane.showMessageDialog(updateUserView.getContentPane(), res.getFormattedErrors());
                }
            }
        }
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateUserView.setNotVisible();
            administratorView.updateAllDataInView(userService.getAllUsers(), userService.getIdList());
            administratorView.setVisible();
        }
    }
}
