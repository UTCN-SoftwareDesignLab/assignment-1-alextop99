package service.user;

import launcher.ComponentFactory;
import model.Client;
import model.User;
import model.dto.AccountDTO;
import model.dto.ClientDTO;
import model.dto.UserDTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.security.RoleRepository;
import repository.user.UserRepository;
import service.client.ClientService;

import java.util.List;
import java.util.UUID;

import static database.Constants.AccountTypes.DEBIT;
import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class UserServiceMySQLTest {
    private static UserService userService;
    private static UserRepository userRepository;
    private static RoleRepository roleRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userRepository = componentFactory.getUserRepository();
        roleRepository = componentFactory.getRoleRepository();
        userService = componentFactory.getUserService();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        assertEquals(0, allUsers.size());
    }

    @Test
    public void getAllAdmins() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("123456a!");
        userDTO.setRole(ADMINISTRATOR);

        userService.save(userDTO);

        List<User> allUsers = userService.getAllAdmins();
        assertEquals(1, allUsers.size());
    }

    @Test
    public void deleteUserById() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("123456a!");
        userDTO.setRole(ADMINISTRATOR);

        userService.save(userDTO);

        List<User> allUsers = userService.getAllAdmins();

        userService.deleteUserById(allUsers.get(0).getId());

        allUsers = userService.getAllAdmins();

        assertEquals(0, allUsers.size());
    }

    @Test
    public void save() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("123456a!");
        userDTO.setRole(ADMINISTRATOR);

        userService.save(userDTO);

        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    public void update() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("123456a!");
        userDTO.setRole(ADMINISTRATOR);

        userService.save(userDTO);

        User user = userService.getAllUsers().get(0);
        userDTO.setUsername("testChanged");
        userDTO.setPassword("a123456!");
        userDTO.setId(user.getId());

        assertFalse(userService.update(userDTO).hasErrors());
    }

    @Test
    public void updateWOPassword() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("123456a!");
        userDTO.setRole(ADMINISTRATOR);

        userService.save(userDTO);

        User user = userService.getAllUsers().get(0);
        user.setUsername("testChanged");

        assertFalse(userService.updateWOPassword(user).hasErrors());
    }
}
