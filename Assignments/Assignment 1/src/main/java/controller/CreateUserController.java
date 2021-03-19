package controller;

import model.dto.UserDTO;
import model.validation.Notification;
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
            if(employeeSelection && administratorSelection) {
                JOptionPane.showMessageDialog(createUserView.getContentPane(), "User must be either administrator or employee!!");
            }
            else {
                Notification<Boolean> res = new Notification<>();
                if(!employeeSelection && !administratorSelection) {
                    res.addError("User must be either administrator or employee!!");
                }
                else {
                    UserDTO userDTO = createUserView.getUserDTO();
                    if(administratorSelection) {
                        userDTO.setRole(ADMINISTRATOR);
                    }
                    else {
                        userDTO.setRole(EMPLOYEE);
                    }
                    res = userService.save(userDTO);
                }
                if(!res.hasErrors()) {
                    JOptionPane.showMessageDialog(createUserView.getContentPane(), "User created successfully");
                }
                else {
                    JOptionPane.showMessageDialog(createUserView.getContentPane(), res.getFormattedErrors());
                }
            }
        }
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
