package repository.account;

import launcher.ComponentFactory;
import model.*;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RoleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AccountRepositoryMySQLTest {
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        accountRepository = componentFactory.getAccountRepository();
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
        accountRepository.removeAllAccountTypes();
    }

    @Test
    public void addAccountType() {
        assertTrue(accountRepository.addAccountType("testType"));
    }

    @Test
    public void findAccountTypeByTitle() {
        accountRepository.addAccountType("testType");

        assertNotNull(accountRepository.findAccountTypeByTitle("testType"));
    }

    @Test
    public void findAccountTypeById() {
        accountRepository.addAccountType("testType");
        AccountType accountType = accountRepository.findAccountTypeByTitle("testType");

        assertNotNull(accountRepository.findAccountTypeById(accountType.getId()));
    }

    @Test
    public void removeAllAccountTypes() {
        assertTrue(accountRepository.removeAllAccountTypes());
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
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

        account = new AccountBuilder()
                .setAmountOfMoney(20.0)
                .setNumber(UUID.randomUUID().toString())
                .setType(accountRepository.findAccountTypeByTitle("testType2"))
                .setDateOfCreation(LocalDate.now())
                .build();

        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 2);
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

        Account accountRes = accountRepository.findByNumber(account.getNumber());
        assertNotNull(accountRepository.findById(accountRes.getId()));
    }

    @Test
    public void findByNumber() {
        accountRepository.addAccountType("testType");

        Account account = new AccountBuilder()
                .setAmountOfMoney(20.0)
                .setNumber(UUID.randomUUID().toString())
                .setType(accountRepository.findAccountTypeByTitle("testType"))
                .setDateOfCreation(LocalDate.now())
                .build();

        accountRepository.save(account);

        assertNotNull(accountRepository.findByNumber(account.getNumber()));
    }

    @Test
    public void save() {
        accountRepository.addAccountType("testType");

        assertTrue(accountRepository.save(
                new AccountBuilder()
                        .setAmountOfMoney(20.0)
                        .setNumber(UUID.randomUUID().toString())
                        .setType(accountRepository.findAccountTypeByTitle("testType"))
                        .setDateOfCreation(LocalDate.now())
                        .build()
        ).getResult());
    }

    @Test
    public void removeAll() {
        accountRepository.removeAll();
        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

}
