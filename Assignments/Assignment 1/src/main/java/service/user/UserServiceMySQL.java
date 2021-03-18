package service.user;

import model.Role;
import model.User;
import model.validation.Notification;
import repository.security.RoleRepository;
import repository.user.UserRepository;
import static database.Constants.Roles.ADMINISTRATOR;

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
    public Notification<Boolean> updateWOPassword(User user) {
        return userRepository.updateWOPassword(user);
    }
}
