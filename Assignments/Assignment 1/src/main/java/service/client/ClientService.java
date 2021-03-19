package service.client;

import model.Account;
import model.AccountType;
import model.Client;
import model.dto.AccountDTO;
import model.dto.ClientDTO;
import model.validation.Notification;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    boolean deleteClientById(Long clientId);

    Notification<Boolean> saveWOAccount(ClientDTO clientDTO);

    Notification<Client> findById(Long clientId);

    Notification<Boolean> save(Account account);

    Notification<Boolean> saveAndAddToClient(AccountDTO accountDTO, ClientDTO clientDTO);

    Notification<Boolean> save(Client client);

    Notification<Boolean> save(ClientDTO clientDTO, AccountDTO accountDTO);

    AccountType findAccountTypeByTitle(String type);

    boolean deleteAccountByNumber(String accountNumber);

    Notification<Boolean> update(Client client);

    Notification<Boolean> update(ClientDTO clientDTO);

    Notification<Boolean> updateAccount(Account account);

    Notification<Boolean> updateAccount(AccountDTO accountDTO);

    void addAccountToClient(String accountNumber, Long clientID);

    List<Long> getIdList();

    Account findByNumber(String accountNumber);
}
