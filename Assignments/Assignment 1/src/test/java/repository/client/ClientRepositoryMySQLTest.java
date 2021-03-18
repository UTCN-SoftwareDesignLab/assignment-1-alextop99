package repository.client;

import launcher.ComponentFactory;
import model.Account;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ClientRepositoryMySQLTest {
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        clientRepository = componentFactory.getClientRepository();
        accountRepository = componentFactory.getAccountRepository();
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        accountRepository.addAccountType("testType");
        accountRepository.addAccountType("testType2");

        Account account = new AccountBuilder()
                .setAmountOfMoney(20.0)
                .setNumber(UUID.randomUUID().toString())
                .setType(accountRepository.findAccountTypeByTitle("testType"))
                .setDateOfCreation(LocalDate.now())
                .build();

        accountRepository.save(account);

        Client client = new ClientBuilder()
                .setSurname("testSurname")
                .setFirstname("testFirstname")
                .setAddress("home address")
                .setIDCardNumber("HD202020")
                .setCnp("1133713371337")
                .setAccount(account)
                .build();

        clientRepository.save(client);

        account = new AccountBuilder()
                .setAmountOfMoney(40.0)
                .setNumber(UUID.randomUUID().toString())
                .setType(accountRepository.findAccountTypeByTitle("testType2"))
                .setDateOfCreation(LocalDate.now())
                .build();

        accountRepository.save(account);

        client = new ClientBuilder()
                .setSurname("testSurname2")
                .setFirstname("testFirstname2")
                .setAddress("home address2")
                .setIDCardNumber("HD212121")
                .setCnp("1133613361336")
                .setAccount(account)
                .build();

        clientRepository.save(client);

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 2);
    }

    @Test
    public void findById() {
        accountRepository.addAccountType("testType");

        Account account = new AccountBuilder()
                .setAmountOfMoney(20.0)
                .setNumber(UUID.randomUUID().toString())
                .setType(accountRepository.findAccountTypeByTitle("testType"))
                .setDateOfCreation(LocalDate.now())
                .build();

        accountRepository.save(account);

        Client client = new ClientBuilder()
                .setSurname("testSurname")
                .setFirstname("testFirstname")
                .setAddress("home address")
                .setIDCardNumber("HD202020")
                .setCnp("1133713371337")
                .setAccount(account)
                .build();

        clientRepository.save(client);

        Notification<Client> result = clientRepository.findById(client.getId());

        assertFalse(result.hasErrors());
    }

    @Test
    public void findByCNP() {
        accountRepository.addAccountType("testType");

        Account account = new AccountBuilder()
                .setAmountOfMoney(20.0)
                .setNumber(UUID.randomUUID().toString())
                .setType(accountRepository.findAccountTypeByTitle("testType"))
                .setDateOfCreation(LocalDate.now())
                .build();

        accountRepository.save(account);

        Client client = new ClientBuilder()
                .setSurname("testSurname")
                .setFirstname("testFirstname")
                .setAddress("home address")
                .setIDCardNumber("HD202020")
                .setCnp("1133713371337")
                .setAccount(account)
                .build();

        clientRepository.save(client);

        Notification<Client> result = clientRepository.findByCNP(client.getCnp());

        assertFalse(result.hasErrors());
    }

    @Test
    public void save() {
        accountRepository.addAccountType("testType");

        Account account = new AccountBuilder()
                .setAmountOfMoney(20.0)
                .setNumber(UUID.randomUUID().toString())
                .setType(accountRepository.findAccountTypeByTitle("testType"))
                .setDateOfCreation(LocalDate.now())
                .build();

        accountRepository.save(account);

        assertTrue(clientRepository.save(
                new ClientBuilder()
                        .setSurname("testSurname")
                        .setFirstname("testFirstname")
                        .setAddress("home address")
                        .setIDCardNumber("HD202020")
                        .setCnp("1133713371337")
                        .setAccount(account)
                        .build()
        ).getResult());
    }

    @Test
    public void removeAll() {
        clientRepository.removeAll();
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }
}
