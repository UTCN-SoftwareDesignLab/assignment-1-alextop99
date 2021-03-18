package service.user;

import model.Client;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserService {

    Notification<User> findById(Long userId);

    boolean deleteUserById(Long userId);

    List<Long> getIdList();

    List<User> getAllUsers();

    List<User> getAllAdmins();

    Notification<Boolean> save(User user);

    Notification<Boolean> update(User user);

    Notification<Boolean> updateWOPassword(User user);
}
