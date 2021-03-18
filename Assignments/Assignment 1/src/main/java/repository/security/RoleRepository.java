package repository.security;

import model.Role;

public interface RoleRepository {

    boolean addRole(String role);

    Role findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    boolean removeAll();
}
