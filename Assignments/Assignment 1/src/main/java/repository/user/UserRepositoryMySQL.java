package repository.user;

import model.builder.UserBuilder;
import model.User;
import model.validation.Notification;
import repository.security.RoleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository{

    private final Connection connection;
    private final RoleRepository roleRepository;

    public UserRepositoryMySQL(Connection connection, RoleRepository roleRepository) {
        this.connection = connection;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();

            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "`";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);

            while(userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRole(roleRepository.findRoleById(userResultSet.getLong("role_id")))
                        .build();
                users.add(user);
            }
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Notification<User> findById(Long userId) {
        Notification<User> findByIdNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `id`=" + userId;
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRole(roleRepository.findRoleById(userResultSet.getLong("role_id")))
                        .build();
                findByIdNotification.setResult(user);
                return findByIdNotification;
            } else {
                findByIdNotification.addError("User does not exist!");
                return findByIdNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByIdNotification.addError("Something is wrong with the Database");
        }
        return findByIdNotification;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`='" + username + "' and `password`='" + password + "'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRole(roleRepository.findRoleById(userResultSet.getLong("role_id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public List<User> findByRole(Long roleId) {
        try {
            List<User> users = new ArrayList<>();

            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `role_id`=" + roleId;
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);

            while(userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRole(roleRepository.findRoleById(userResultSet.getLong("role_id")))
                        .build();
                users.add(user);
            }

            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Notification<Boolean> save(User user) {
        Notification<Boolean> saveNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + USER + "` values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.setLong(3, roleRepository.findRoleByTitle(user.getRole().getRole()).getId());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);
            saveNotification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("Check the username!! An error has happened in database!");
            saveNotification.setResult(true);
        }
        return saveNotification;
    }

    @Override
    public Notification<Boolean> update(User user) {
        Notification<Boolean> updateNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE `" + USER + "` SET username= ?, password = ?, role_id = ? WHERE id = ?");
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.setLong(3, roleRepository.findRoleByTitle(user.getRole().getRole()).getId());
            insertUserStatement.setLong(4, user.getId());
            insertUserStatement.executeUpdate();
            updateNotification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            updateNotification.addError("An error has happened in database!");
            updateNotification.setResult(true);
        }
        return updateNotification;
    }

    @Override
    public Notification<Boolean> updateWOPassword(User user) {
        Notification<Boolean> updateNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE `" + USER + "` SET username= ?, role_id = ? WHERE id = ?");
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setLong(2, roleRepository.findRoleByTitle(user.getRole().getRole()).getId());
            insertUserStatement.setLong(3, user.getId());
            insertUserStatement.executeUpdate();
            updateNotification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            updateNotification.addError("An error has happened in database!");
            updateNotification.setResult(true);
        }
        return updateNotification;
    }

    @Override
    public List<Long> getIdList() {
        try {
            List<Long> ids = new ArrayList<>();

            Statement statement = connection.createStatement();
            String fetchIdsrSql = "Select id from `" + USER + "`";
            ResultSet idResultSet = statement.executeQuery(fetchIdsrSql);

            while(idResultSet.next()) {
                ids.add(idResultSet.getLong("id"));
            }

            return ids;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public boolean deleteById(Long userId) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + USER + "` where id = " + userId.toString();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + USER + "` where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
