package repository.security;

import launcher.ComponentFactory;
import model.Role;;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;


public class RoleRepositoryMySQLTest {
    private static RoleRepository roleRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        roleRepository = componentFactory.getRoleRepository();
    }

    @Before
    public void cleanUp() {
        roleRepository.removeAll();
    }

    @Test
    public void addRole() {
        assertTrue(roleRepository.addRole(
                "testRole"));
    }

    @Test
    public void findRoleById() throws Exception {
        roleRepository.addRole(
                "testRole");

        Role role = roleRepository.findRoleByTitle("testRole");
        assertNotNull(roleRepository.findRoleById(role.getId()));
    }

    @Test
    public void findRoleByTitle() {
        roleRepository.addRole(
                "testRole");

        assertNotNull(roleRepository.findRoleByTitle("testRole"));
    }


    @Test
    public void removeAll() {
        assertTrue(roleRepository.removeAll());
    }
}
