package database;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import service.security.PasswordEncoder;

public class Constants {
    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String ROLE = "role";
        public static final String REPORT = "report";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{ROLE, ACCOUNT_TYPE, ACCOUNT, CLIENT , USER, REPORT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class AccountTypes {
        public static final String DEBIT = "debit";
        public static final String CREDIT = "credit";

        public static final String[] ACCOUNT_TYPES = new String[]{DEBIT, CREDIT};
    }

    public static class Users {

        private static final Role adminrole = new Role((long) -1, "administrator");
        public static final User ADMINUSER = new UserBuilder()
                .setId((long) -1)
                .setUsername("admin")
                .setPassword(PasswordEncoder.encodePassword("admin"))
                .setRole(adminrole)
                .build();

        public static final User[] USERS = new User[]{ADMINUSER};
    }
}