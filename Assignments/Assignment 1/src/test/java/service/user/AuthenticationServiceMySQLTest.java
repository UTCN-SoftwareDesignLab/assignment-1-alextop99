package service.user;

import launcher.ComponentFactory;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RoleRepository;
import repository.user.UserRepository;
import service.security.PasswordEncoder;

public class AuthenticationServiceMySQLTest {

    public static final String TEST_USERNAME = "test@username.com";
    public static final String TEST_PASSWORD = "TestPassword1@";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static RoleRepository roleRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        userRepository = componentFactory.getUserRepository();
        roleRepository = componentFactory.getRoleRepository();
        authenticationService = componentFactory.getAuthenticationService();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void login() throws Exception {
        userRepository.save(
                new UserBuilder()
                        .setUsername(TEST_USERNAME)
                        .setPassword(PasswordEncoder.encodePassword(TEST_PASSWORD))
                        .setRole(roleRepository.findRoleByTitle("administrator"))
                        .build()
        );

        User user = authenticationService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
        Assert.assertNotNull(user);
    }
}
