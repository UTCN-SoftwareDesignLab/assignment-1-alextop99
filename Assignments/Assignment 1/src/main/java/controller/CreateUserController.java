package controller;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import service.security.PasswordEncoder;
import service.user.UserService;
import view.AdministratorView;
import view.CreateUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class CreateUserController {
    private final CreateUserView createUserView;
    private final UserService userService;
    private final AdministratorView administratorView;

    public CreateUserController(CreateUserView createUserView, UserService userService, AdministratorView administratorView) {
        this.createUserView = createUserView;
        this.userService = userService;
        this.administratorView = administratorView;
        createUserView.setCreateButtonListener(new CreateUserController.CreateButtonListener());
        createUserView.setBackButtonListener(new CreateUserController.BackButtonListener());
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean employeeSelection = createUserView.getEmployee();
            boolean administratorSelection = createUserView.getAdministrator();
            if(employeeSelection && administratorSelection)
                JOptionPane.showMessageDialog(createUserView.getContentPane(), "User must be either administrator or employee!!");
            else {
                Notification<Boolean> res = new Notification<>();
                if(!employeeSelection && !administratorSelection) {
                    JOptionPane.showMessageDialog(createUserView.getContentPane(), "User must be either administrator or employee!!");
                    res.addError("User must be either administrator or employee!!");
                }
                else if(administratorSelection) {
                    res = getBooleanNotification(res, ADMINISTRATOR);
                }
                else {
                    res = getBooleanNotification(res, EMPLOYEE);
                }
                if(!res.hasErrors())
                    JOptionPane.showMessageDialog(createUserView.getContentPane(), "User created successfully");
            }
        }
    }

    private Notification<Boolean> getBooleanNotification(Notification<Boolean> res, String roleName) {
        Role userRole = new Role((long) -1, roleName);
        User user = new UserBuilder()
                .setUsername(createUserView.getUsername())
                .setPassword(createUserView.getPassword())
                .setRole(userRole)
                .build();
        UserValidator userValidator = new UserValidator(user);
        if(userValidator.validate()) {
            user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
            res = userService.save(user);
            if (res.hasErrors()) {
                JOptionPane.showMessageDialog(createUserView.getContentPane(), res.getFormattedErrors());
            }
        }
        else {
            res.addError(userValidator.getErrors().get(0));
            JOptionPane.showMessageDialog(createUserView.getContentPane(), userValidator.getErrors().get(0));
        }
        return res;
    }

    private class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            createUserView.setNotVisible();
            administratorView.updateAllDataInView(userService.getAllUsers(), userService.getIdList());
            administratorView.setVisible();
        }
    }
}
