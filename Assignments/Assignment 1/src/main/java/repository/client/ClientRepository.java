package repository.client;

import model.Account;
import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Notification<Client> findById(Long clientId);

    Notification<Client> findByCNP(String cnp);

    List<Long> getIdList();

    boolean deleteById(Long clientId);

    Notification<Boolean> save(Client client);

    Notification<Boolean> saveWOAccount(Client client);

    Notification<Boolean> update(Client client);

    void updateClientAccount(Long clientID, Account account);

    void removeAll();
}
