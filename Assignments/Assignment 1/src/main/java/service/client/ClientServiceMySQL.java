package service.client;

import model.Account;
import model.AccountType;
import model.Client;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceMySQL implements ClientService{

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public ClientServiceMySQL(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public boolean deleteClientById(Long clientId) {
        return clientRepository.deleteById(clientId);
    }

    @Override
    public List<Long> getIdList() {
        return clientRepository.getIdList();
    }

    @Override
    public Notification<Boolean> saveWOAccount(Client client) {return clientRepository.saveWOAccount(client);}

    @Override
    public Notification<Boolean> save(Account account) {return accountRepository.save(account);}

    @Override
    public Notification<Boolean> save(Client client) {return clientRepository.save(client);}

    @Override
    public AccountType findAccountTypeByTitle(String type) {return accountRepository.findAccountTypeByTitle(type);}

    @Override
    public Notification<Client> findById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public Notification<Boolean> update(Client client) {return clientRepository.update(client);}

    @Override
    public Notification<Boolean> updateAccount(Account account) {return accountRepository.update(account);}

    @Override
    public void addAccountToClient(String accountNumber, Long clientID) {clientRepository.updateClientAccount(clientID, accountRepository.findByNumber(accountNumber));}

    @Override
    public boolean deleteAccountByNumber(String accountNumber) {
        return accountRepository.deleteAccountByNumber(accountNumber);
    }

    @Override
    public Account findByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }
}
