package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    Notification<User> findById(Long userId);

    Notification<User> findByUsernameAndPassword(String username, String password);

    List<User> findByRole(Long roleId);

    Notification<Boolean> save(User user);

    Notification<Boolean> update(User user);

    Notification<Boolean> updateWOPassword(User user);

    List<Long> getIdList();

    boolean deleteById(Long clientId);

    void removeAll();
}
