package service.client;

import model.Account;
import model.AccountType;
import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    boolean deleteClientById(Long clientId);

    Notification<Boolean> saveWOAccount(Client client);

    Notification<Client> findById(Long clientId);

    Notification<Boolean> save(Account account);

    Notification<Boolean> save(Client client);

    AccountType findAccountTypeByTitle(String type);

    boolean deleteAccountByNumber(String accountNumber);

    Notification<Boolean> update(Client client);

    Notification<Boolean> updateAccount(Account account);

    void addAccountToClient(String accountNumber, Long clientID);

    List<Long> getIdList();

    Account findByNumber(String accountNumber);
}
