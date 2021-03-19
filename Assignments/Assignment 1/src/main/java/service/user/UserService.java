package service.user;

import model.Client;
import model.User;
import model.dto.UserDTO;
import model.validation.Notification;

import java.util.List;

public interface UserService {

    Notification<User> findById(Long userId);

    boolean deleteUserById(Long userId);

    List<Long> getIdList();

    List<User> getAllUsers();

    List<User> getAllAdmins();

    Notification<Boolean> save(User user);

    Notification<Boolean> save(UserDTO userDTO);

    Notification<Boolean> update(User user);

    Notification<Boolean> update(UserDTO userDTO);

    Notification<Boolean> updateWOPassword(User user);
}
