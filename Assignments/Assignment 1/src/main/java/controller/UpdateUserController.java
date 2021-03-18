package controller;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import service.security.PasswordEncoder;
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
            boolean employeeSelection = updateUserView.getEmployee();
            boolean administratorSelection = updateUserView.getAdministrator();
            if(employeeSelection && administratorSelection)
                JOptionPane.showMessageDialog(updateUserView.getContentPane(), "User must be either administrator or employee!!");
            else {
                Notification<Boolean> res = new Notification<>();
                if(!employeeSelection && !administratorSelection) {
                    JOptionPane.showMessageDialog(updateUserView.getContentPane(), "User must be either administrator or employee!!");
                    res.addError("User must be either administrator or employee!!");
                }
                else if(administratorSelection) {
                    res = getBooleanNotification(res, ADMINISTRATOR);
                }
                else {
                    if(userService.getAllAdmins().size() == 1 && userService.findById(updateUserView.getID()).getResult().getRole().getRole().equals(ADMINISTRATOR)) {
                        JOptionPane.showMessageDialog(administratorView.getContentPane(), "You need at least one admin!!");
                        res.addError("You need at least one admin!!");
                    }
                    else {
                        res = getBooleanNotification(res, EMPLOYEE);
                    }
                }
                if(!res.hasErrors())
                    JOptionPane.showMessageDialog(updateUserView.getContentPane(), "User updated successfully");
            }
        }
    }

    private Notification<Boolean> getBooleanNotification(Notification<Boolean> res, String roleName) {
        Role userRole = new Role((long) -1, roleName);
        User user = new UserBuilder()
                .setId(updateUserView.getID())
                .setUsername(updateUserView.getUsername())
                .setPassword(updateUserView.getPassword())
                .setRole(userRole)
                .build();

        if(user.getPassword().isEmpty()) {
            res = userService.updateWOPassword(user);
        }
        else {
            UserValidator userValidator = new UserValidator(user);
            if(userValidator.validate()) {
                user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
                res = userService.update(user);
            }
            else {
                JOptionPane.showMessageDialog(updateUserView.getContentPane(), userValidator.getErrors().get(0));
                res.addError(userValidator.getErrors().get(0));
            }
        }
        return res;
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
