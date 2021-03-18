package model.builder;

import model.Account;
import model.AccountType;

import java.time.LocalDate;

public class AccountBuilder {
    private final Account account;

    public AccountBuilder() { account = new Account(); }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setNumber(String number) {
        account.setNumber(number);
        return this;
    }

    public AccountBuilder setType(AccountType type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmountOfMoney(Double amountOfMoney) {
        account.setAmountOfMoney(amountOfMoney);
        return this;
    }

    public AccountBuilder setDateOfCreation(LocalDate dateOfCreation) {
        account.setDateOfCreation(dateOfCreation);
        return this;
    }

    public Account build() {
        return account;
    }
}
