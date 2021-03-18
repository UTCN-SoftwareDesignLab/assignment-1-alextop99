package service.user;

import model.User;

import model.validation.Notification;
import repository.user.UserRepository;
import service.security.PasswordEncoder;


public class AuthenticationServiceMySQL implements AuthenticationService{
    private final UserRepository userRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, PasswordEncoder.encodePassword(password));
    }
}
