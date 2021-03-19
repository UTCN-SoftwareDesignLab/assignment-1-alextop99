package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.dto.UserDTO;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RoleRepository;
import repository.user.UserRepository;
import service.security.PasswordEncoder;

import javax.swing.*;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

import java.util.List;

public class UserServiceMySQL implements  UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceMySQL(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Notification<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean deleteUserById(Long userId) {
        return userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Long> getIdList() {
        return userRepository.getIdList();
    }

    @Override
    public List<User> getAllAdmins() {
        Role adminRole = roleRepository.findRoleByTitle(ADMINISTRATOR);
        return userRepository.findByRole(adminRole.getId());
    }

    @Override
    public Notification<Boolean> update(User user) {
        return userRepository.update(user);
    }

    @Override
    public Notification<Boolean> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Notification<Boolean> save(UserDTO userDTO) {
        Notification<Boolean> res = new Notification<>();

        Role userRole = new Role((long) -1, userDTO.getRole());
        User user = new UserBuilder()
                .setUsername(userDTO.getUsername())
                .setPassword(userDTO.getPassword())
                .setRole(userRole)
                .build();
        UserValidator userValidator = new UserValidator(user);
        if(userValidator.validate()) {
            user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
            res = userRepository.save(user);
            return res;
        }
        else {
            res.addError(userValidator.getErrors().get(0));
        }
        return res;
    }

    @Override
    public Notification<Boolean> update(UserDTO userDTO) {
        Notification<Boolean> res = new Notification<>();

        if(this.getAllAdmins().size() == 1 && userRepository.findById(userDTO.getId()).getResult().getRole().getRole().equals(ADMINISTRATOR) && userDTO.getRole().equals(EMPLOYEE)) {
            res.addError("You need at least one admin!!");
        }
        else {
            Role userRole = new Role((long) -1, userDTO.getRole());
            User user = new UserBuilder()
                    .setId(userDTO.getId())
                    .setUsername(userDTO.getUsername())
                    .setPassword(userDTO.getPassword())
                    .setRole(userRole)
                    .build();

            if (user.getPassword().isEmpty()) {
                res = userRepository.updateWOPassword(user);
            } else {
                UserValidator userValidator = new UserValidator(user);
                if (userValidator.validate()) {
                    user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
                    res = userRepository.update(user);
                } else {
                    res.addError(userValidator.getErrors().get(0));
                }
            }
        }
        return res;
    }

    @Override
    public Notification<Boolean> updateWOPassword(User user) {
        return userRepository.updateWOPassword(user);
    }
}
