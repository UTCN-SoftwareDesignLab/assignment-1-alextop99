package repository.user;

import launcher.ComponentFactory;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RoleRepository;

import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryMySQLTest {
    private static UserRepository userRepository;
    private static RoleRepository roleRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        userRepository = componentFactory.getUserRepository();
        roleRepository = componentFactory.getRoleRepository();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        User user = new UserBuilder()
                .setUsername("testUser")
                .setPassword("testPassword")
                .setRole(roleRepository.findRoleByTitle("administrator"))
                .build();
        userRepository.save(user);

        user = new UserBuilder()
                .setUsername("testUser2")
                .setPassword("testPassword2")
                .setRole(roleRepository.findRoleByTitle("employee"))
                .build();

        userRepository.save(user);

        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 2);
    }

    @Test
    public void findById() {
        User user = new UserBuilder()
                .setUsername("testUser")
                .setPassword("testPassword")
                .setRole(roleRepository.findRoleByTitle("administrator"))
                .build();
        userRepository.save(user);

        Notification<User> result = userRepository.findById(user.getId());

        assertFalse(result.hasErrors());
    }

    @Test
    public void save() {
        assertTrue(userRepository.save(
                new UserBuilder()
                        .setUsername("testUser")
                        .setPassword("testPassword")
                        .setRole(roleRepository.findRoleByTitle("administrator"))
                        .build()
        ).getResult());
    }

    @Test
    public void removeAll() {
        userRepository.removeAll();
        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 0);
    }
}
