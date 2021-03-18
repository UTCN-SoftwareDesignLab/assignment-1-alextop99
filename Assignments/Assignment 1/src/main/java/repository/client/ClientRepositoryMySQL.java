package repository.client;

import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.account.AccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;
    private final AccountRepository accountRepository;

    public ClientRepositoryMySQL(Connection connection, AccountRepository accountRepository) {
        this.connection = connection;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Client> findAll() {
        try {
            List<Client> clients = new ArrayList<>();

            Statement statement = connection.createStatement();
            String fetchClientSql = "Select * from `" + CLIENT + "`";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);

            while(clientResultSet.next()) {
                Client client = new ClientBuilder()
                        .setId(clientResultSet.getLong("id"))
                        .setSurname(clientResultSet.getString("surname"))
                        .setFirstname(clientResultSet.getString("firstname"))
                        .setIDCardNumber(clientResultSet.getString("idcardnumber"))
                        .setCnp(clientResultSet.getString("cnp"))
                        .setAddress(clientResultSet.getString("address"))
                        .setAccount(accountRepository.findById(clientResultSet.getLong("account_id")))
                        .build();
                clients.add(client);
            }

            return clients;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Notification<Client> findById(Long clientId) {
        Notification<Client> findByIdNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + CLIENT + "` where `id`=" + clientId;
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            return getClientNotification(findByIdNotification, clientResultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            findByIdNotification.addError("Something is wrong with the Database");
        }
        return findByIdNotification;
    }

    private Notification<Client> getClientNotification(Notification<Client> findByIdNotification, ResultSet clientResultSet) throws SQLException {
        if (clientResultSet.next()) {
            Client client = new ClientBuilder()
                    .setId(clientResultSet.getLong("id"))
                    .setSurname(clientResultSet.getString("surname"))
                    .setFirstname(clientResultSet.getString("firstname"))
                    .setIDCardNumber(clientResultSet.getString("idcardnumber"))
                    .setCnp(clientResultSet.getString("cnp"))
                    .setAddress(clientResultSet.getString("address"))
                    .setAccount(accountRepository.findById(clientResultSet.getLong("account_id")))
                    .build();
            findByIdNotification.setResult(client);
            return findByIdNotification;
        } else {
            findByIdNotification.addError("User does not exist!");
            return findByIdNotification;
        }
    }

    @Override
    public Notification<Client> findByCNP(String cnp) {
        Notification<Client> findByIdNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + CLIENT + "` where `cnp`='" + cnp +"'";
            ResultSet clientResultSet = statement.executeQuery(fetchUserSql);
            return getClientNotification(findByIdNotification, clientResultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            findByIdNotification.addError("Something is wrong with the Database");
        }
        return findByIdNotification;
    }

    @Override
    public Notification<Boolean> update(Client client) {
        Notification<Boolean> updateNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE `" + CLIENT + "` SET surname= ?, firstname = ?, idcardnumber = ?, cnp = ?, address = ? WHERE id = ?");
            insertUserStatement.setString(1, client.getSurname());
            insertUserStatement.setString(2, client.getFirstname());
            insertUserStatement.setString(3, client.getIDCardNumber());
            insertUserStatement.setString(4, client.getCnp());
            insertUserStatement.setString(5, client.getAddress());
            insertUserStatement.setLong(6, client.getId());
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
    public void updateClientAccount(Long clientID, Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE `" + CLIENT + "` SET account_id = ? WHERE id = ?");
            insertUserStatement.setLong(1, account.getId());
            insertUserStatement.setLong(2, clientID);
            insertUserStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Long> getIdList() {
        try {
            List<Long> ids = new ArrayList<>();

            Statement statement = connection.createStatement();
            String fetchIdsrSql = "Select id from `" + CLIENT + "`";
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
    public boolean deleteById(Long clientId) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + CLIENT + "` where id = " + clientId.toString();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Notification<Boolean> save(Client client) {
        Notification<Boolean> saveNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + CLIENT + "` values (null, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getSurname());
            insertUserStatement.setString(2, client.getFirstname());
            insertUserStatement.setString(3, client.getIDCardNumber());
            insertUserStatement.setString(4, client.getCnp());
            insertUserStatement.setString(5, client.getAddress());
            insertUserStatement.setLong(6, accountRepository.findByNumber(client.getAccount().getNumber()).getId());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            client.setId(userId);
            saveNotification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("Check CNP!! An error has happened in database!");
            saveNotification.setResult(false);
        }
        return saveNotification;
    }

    @Override
    public Notification<Boolean> saveWOAccount(Client client) {
        Notification<Boolean> saveNotification = new Notification<>();
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO `" + CLIENT + "` values (null, ?, ?, ?, ?, ?, null)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, client.getSurname());
            insertUserStatement.setString(2, client.getFirstname());
            insertUserStatement.setString(3, client.getIDCardNumber());
            insertUserStatement.setString(4, client.getCnp());
            insertUserStatement.setString(5, client.getAddress());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            client.setId(userId);
            saveNotification.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("Check CNP!! An error has happened in database!");
            saveNotification.setResult(false);
        }
        return saveNotification;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + CLIENT + "` where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
