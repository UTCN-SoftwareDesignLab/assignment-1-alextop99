package database;

import model.User;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.security.RoleRepository;
import repository.security.RoleRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static database.Constants.Roles.ROLES;
import static database.Constants.AccountTypes.ACCOUNT_TYPES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.Users.USERS;

public class Boostraper {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;

    public void execute() throws SQLException {
        dropAll();

        bootstrapTables();
        bootstrapUserData();
        bootstrapClientData();
    }

    private void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "DROP TABLE  `report`, `role`, `account_type`, `account`, `client`, `user`;"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private void bootstrapUserData() {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            roleRepository = new RoleRepositoryMySQL(connectionWrapper.getConnection());
            userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), roleRepository);

            bootstrapRoles();
            bootstrapUsers();
        }
    }

    private void bootstrapRoles() {
        for (String role : ROLES) {
            roleRepository.addRole(role);
        }
    }

    private void bootstrapUsers() {
        for (User user : USERS) {
            userRepository.save(user);
        }
    }

    private void bootstrapClientData() {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping client data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            accountRepository = new AccountRepositoryMySQL(connectionWrapper.getConnection());

            bootstrapAccountTypes();
        }
    }

    private void bootstrapAccountTypes() {
        for (String accountType : ACCOUNT_TYPES) {
            accountRepository.addAccountType(accountType);
        }
    }
}