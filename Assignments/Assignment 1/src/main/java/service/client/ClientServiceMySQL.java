package service.client;

import model.Account;
import model.AccountType;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.dto.AccountDTO;
import model.dto.ClientDTO;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import service.security.PasswordEncoder;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static database.Constants.AccountTypes.DEBIT;

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
    public Notification<Boolean> saveWOAccount(ClientDTO clientDTO) {
        Client client = new ClientBuilder()
                .setSurname(clientDTO.getSurname())
                .setFirstname(clientDTO.getFirstname())
                .setIDCardNumber(clientDTO.getIDCardNumber())
                .setCnp(clientDTO.getCnp())
                .setAddress(clientDTO.getAddress())
                .build();
        return clientRepository.saveWOAccount(client);
    }

    @Override
    public Notification<Boolean> save(Account account) {return accountRepository.save(account);}

    @Override
    public Notification<Boolean> saveAndAddToClient(AccountDTO accountDTO, ClientDTO clientDTO) {
        Account account = new AccountBuilder()
                .setType(accountRepository.findAccountTypeByTitle(accountDTO.getType()))
                .setNumber(UUID.randomUUID().toString())
                .setDateOfCreation(LocalDate.now())
                .setAmountOfMoney(accountDTO.getAmountOfMoney())
                .build();
        Notification<Boolean> res = accountRepository.save(account);
        if(res.hasErrors()) {
            return  res;
        }
        else {
            clientRepository.updateClientAccount(clientDTO.getId(), accountRepository.findByNumber(account.getNumber()));
        }
        return res;
    }

    @Override
    public Notification<Boolean> save(Client client) {return clientRepository.save(client);}

    @Override
    public Notification<Boolean> save(ClientDTO clientDTO, AccountDTO accountDTO) {
        Notification<Boolean> res;

        Account account = new AccountBuilder()
                .setType(accountRepository.findAccountTypeByTitle(accountDTO.getType()))
                .setNumber(UUID.randomUUID().toString())
                .setDateOfCreation(LocalDate.now())
                .setAmountOfMoney(accountDTO.getAmountOfMoney())
                .build();

        res = accountRepository.save(account);
        if(res.hasErrors()) {
            return res;
        }
        else {
            Client client = new ClientBuilder()
                    .setSurname(clientDTO.getSurname())
                    .setFirstname(clientDTO.getFirstname())
                    .setIDCardNumber(clientDTO.getIDCardNumber())
                    .setCnp(clientDTO.getCnp())
                    .setAddress(clientDTO.getAddress())
                    .setAccount(account)
                    .build();

            ClientValidator clientValidator = new ClientValidator(client);

            if(clientValidator.validate()) {
                res = clientRepository.save(client);
                if (res.hasErrors()) {
                    return res;
                }
            }
            else {
                res.addError(clientValidator.getErrors().get(0));
                return res;
            }
        }
        return res;
    }

    @Override
    public Notification<Boolean> update(ClientDTO clientDTO) {
        Client client = new ClientBuilder()
                .setId(clientDTO.getId())
                .setSurname(clientDTO.getSurname())
                .setFirstname(clientDTO.getFirstname())
                .setCnp(clientDTO.getCnp())
                .setIDCardNumber(clientDTO.getIDCardNumber())
                .setAddress(clientDTO.getAddress())
                .build();

        ClientValidator clientValidator = new ClientValidator(client);
        Notification<Boolean> res = new Notification<>();
        if(clientValidator.validate()) {
            res = clientRepository.update(client);
            return res;
        }
        else {
            res.addError(clientValidator.getErrors().get(0));
        }
        return res;
    }

    @Override
    public Notification<Boolean> updateAccount(AccountDTO accountDTO) {
        Account account = new AccountBuilder()
                .setId(accountDTO.getId())
                .setNumber(accountDTO.getNumber())
                .setAmountOfMoney(accountDTO.getAmountOfMoney())
                .setType(accountRepository.findAccountTypeByTitle(accountDTO.getType()))
                .build();

        return accountRepository.update(account);
    }

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
