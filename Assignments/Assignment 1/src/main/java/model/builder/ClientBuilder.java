package model.builder;

import model.Account;
import model.Client;

public class ClientBuilder {
    private final Client client;

    public ClientBuilder() { client = new Client(); }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setSurname(String surname) {
        client.setSurname(surname);
        return this;
    }

    public ClientBuilder setFirstname(String firstname) {
        client.setFirstname(firstname);
        return this;
    }

    public ClientBuilder setIDCardNumber(String IDCardNumber) {
        client.setIDCardNumber(IDCardNumber);
        return this;
    }

    public ClientBuilder setCnp(String cnp) {
        client.setCnp(cnp);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setAccount(Account account) {
        client.setAccount(account);
        return this;
    }

    public Client build() {
        return client;
    }
}
