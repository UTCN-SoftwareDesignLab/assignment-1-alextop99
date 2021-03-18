package repository.account;

import model.Account;
import model.AccountType;
import model.validation.Notification;

import java.util.List;

public interface AccountRepository {
    boolean addAccountType(String accountType);

    AccountType findAccountTypeByTitle(String type);

    AccountType findAccountTypeById(Long typeId);

    boolean removeAllAccountTypes();

    List<Account> findAll();

    Account findById(Long accountId);

    Account findByNumber(String accountNumber);

    boolean deleteAccountByNumber(String accountNumber);

    Notification<Boolean> save(Account account);

    Notification<Boolean> update(Account account);

    void removeAll();
}
