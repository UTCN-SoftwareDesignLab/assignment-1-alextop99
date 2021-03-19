package repository.account;

import model.Account;
import model.AccountType;
import model.builder.AccountBuilder;
import model.validation.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addAccountType(String accountType) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ACCOUNT_TYPE + " values (null, ?)");
            insertStatement.setString(1, accountType);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AccountType findAccountTypeByTitle(String type) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchAccountTypeSql = "Select * from " + ACCOUNT_TYPE + " where `type`='" + type + "'";
            ResultSet accountTypeResultSet = statement.executeQuery(fetchAccountTypeSql);
            accountTypeResultSet.next();
            Long typeId = accountTypeResultSet.getLong("id");
            String typeTitle = accountTypeResultSet.getString("type");
            return new AccountType(typeId, typeTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AccountType findAccountTypeById(Long typeId){
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchAccountTypeSql = "Select * from " + ACCOUNT_TYPE + " where `id`='" + typeId + "'";
            ResultSet accountTypeResultSet = statement.executeQuery(fetchAccountTypeSql);
            accountTypeResultSet.next();
            Long typeIdRes = accountTypeResultSet.getLong("id");
            String typeTitle = accountTypeResultSet.getString("type");
            return new AccountType(typeIdRes, typeTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeAllAccountTypes() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ ACCOUNT_TYPE +" where id >= 0";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Account> findAll() {
        try {
            List<Account> accounts = new ArrayList<>();

            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from `" + ACCOUNT + "`";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);

            while(accountResultSet.next()) {
                Account account = new AccountBuilder()
                        .setId(accountResultSet.getLong("id"))
                        .setNumber(accountResultSet.getString("number"))
                        .setType(this.findAccountTypeById(accountResultSet.getLong("type_id")))
                        .setAmountOfMoney(accountResultSet.getDouble("money"))
                        .setDateOfCreation(accountResultSet.getDate("dateOfCreation").toLocalDate())
                        .build();
                accounts.add(account);
            }

            return accounts;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Account findById(Long accountId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT + " where `id`=" + accountId;
            return getAccount(statement, fetchRoleSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findByNumber(String accountNumber) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT + " where `number`='" + accountNumber + "'";
            return getAccount(statement, fetchRoleSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Account getAccount(Statement statement, String fetchRoleSql) throws SQLException {
        ResultSet accountResultSet = statement.executeQuery(fetchRoleSql);
        if( accountResultSet.next()) {
            return new AccountBuilder()
                    .setId(accountResultSet.getLong("id"))
                    .setNumber(accountResultSet.getString("number"))
                    .setType(this.findAccountTypeById(accountResultSet.getLong("type_id")))
                    .setAmountOfMoney(accountResultSet.getDouble("money"))
                    .setDateOfCreation(accountResultSet.getDate("dateOfCreation").toLocalDate())
                    .build();
        }
        else {
            return null;
        }
    }

    @Override
    public Notification<Boolean> save(Account account) {
        Notification<Boolean> saveNotification = new Notification<>();
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ACCOUNT + " values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, account.getNumber());
            insertStatement.setLong(2, this.findAccountTypeByTitle(account.getType().getType()).getId());
            insertStatement.setDouble(3, account.getAmountOfMoney());
            insertStatement.setDate(4, Date.valueOf(account.getDateOfCreation()));
            insertStatement.executeUpdate();

            saveNotification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("Account creation has failed, please try again later!!");
            saveNotification.setResult(false);
        }
        return saveNotification;
    }

    @Override
    public Notification<Boolean> update(Account account) {
        Notification<Boolean> updateNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE `" + ACCOUNT + "` SET number= ?, type_id = ?, money = ? WHERE id = ?");
            insertUserStatement.setString(1, account.getNumber());
            insertUserStatement.setLong(2, account.getType().getId());
            insertUserStatement.setDouble(3, account.getAmountOfMoney());
            insertUserStatement.setLong(4, account.getId());
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
    public boolean deleteAccountByNumber(String accountNumber) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ ACCOUNT +" where number = '" + accountNumber + "'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ ACCOUNT +" where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
