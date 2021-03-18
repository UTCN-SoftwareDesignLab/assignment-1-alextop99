package launcher;

import controller.*;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;
import repository.security.RoleRepository;
import repository.security.RoleRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.report.ReportService;
import service.report.ReportServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;
import view.*;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;

    private final LoginController loginController;

    private final AuthenticationService authenticationService;

    private final AdministratorView administratorView;
    private final AdministratorController administratorController;
    private final UserService userService;

    private final EmployeeView employeeView;
    private final EmployeeController employeeController;
    private final ClientService clientService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.roleRepository = new RoleRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, roleRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository);
        this.userService = new UserServiceMySQL(this.userRepository, this.roleRepository);

        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
        this.clientService = new ClientServiceMySQL(this.clientRepository, this.accountRepository);

        ReportRepository reportRepository = new ReportRepositoryMySQL(connection, userRepository);
        ReportService reportService = new ReportServiceMySQL(reportRepository, userRepository);

        this.loginView = new LoginView();
        this.administratorView = new AdministratorView();
        this.employeeView = new EmployeeView();
        CreateClientView createClientView = new CreateClientView();
        CreateUserView createUserView = new CreateUserView();
        UpdateClientView updateClientView = new UpdateClientView();
        UpdateUserView updateUserView = new UpdateUserView();

        this.administratorController = new AdministratorController(administratorView, userService, loginView, createUserView, updateUserView, reportService);
        this.employeeController = new EmployeeController(employeeView, clientService, loginView, createClientView, updateClientView, reportService);
        this.loginController = new LoginController(loginView, authenticationService, administratorView, employeeView, reportService);
        CreateClientController createClientController = new CreateClientController(createClientView, clientService, employeeView);
        CreateUserController createUserController = new CreateUserController(createUserView, userService, administratorView);
        UpdateUserController updateUserController = new UpdateUserController(updateUserView, userService, administratorView);
        UpdateClientController updateClientController = new UpdateClientController(updateClientView, clientService, employeeView);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AdministratorView getAdministratorView() {
        return administratorView;
    }

    public AdministratorController getAdministratorController() {
        return administratorController;
    }

    public UserService getUserService() {
        return userService;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public ClientService getClientService() {
        return clientService;
    }
}
