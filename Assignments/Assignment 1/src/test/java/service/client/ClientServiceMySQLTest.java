package service.client;

import launcher.ComponentFactory;
import model.Client;
import model.User;
import model.builder.UserBuilder;

import static database.Constants.AccountTypes.DEBIT;
import static org.junit.Assert.*;

import model.dto.AccountDTO;
import model.dto.ClientDTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.util.List;
import java.util.UUID;

public class ClientServiceMySQLTest {
    private static ClientService clientService;
    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);
        clientRepository = componentFactory.getClientRepository();
        accountRepository = componentFactory.getAccountRepository();
        clientService = componentFactory.getClientService();
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void getAllClients() {
        List<Client> allClients = clientService.getAllClients();
        assertEquals(0, allClients.size());
    }

    @Test
    public void saveWOAccount() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setAddress("test");
        clientDTO.setCnp("1990101101010");
        clientDTO.setIDCardNumber("TT133337");
        clientDTO.setFirstname("Test");
        clientDTO.setSurname("Test");

        clientService.saveWOAccount(clientDTO);

        assertEquals(1, clientService.getAllClients().size());
    }

    @Test
    public void save() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setAddress("test");
        clientDTO.setCnp("1990101101010");
        clientDTO.setIDCardNumber("TT133337");
        clientDTO.setFirstname("Test");
        clientDTO.setSurname("Test");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setType(DEBIT);
        accountDTO.setAmountOfMoney(20.0);
        accountDTO.setNumber(UUID.randomUUID().toString());

        clientService.save(clientDTO, accountDTO);

        assertEquals(1, clientService.getAllClients().size());
    }

    @Test
    public void update() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setAddress("test");
        clientDTO.setCnp("1990101101010");
        clientDTO.setIDCardNumber("TT133337");
        clientDTO.setFirstname("Test");
        clientDTO.setSurname("Test");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setType(DEBIT);
        accountDTO.setAmountOfMoney(20.0);
        accountDTO.setNumber(UUID.randomUUID().toString());

        clientService.save(clientDTO, accountDTO);

        Client client = clientService.getAllClients().get(0);
        client.setFirstname("TestChanged");

        assertFalse(clientService.update(client).hasErrors());
    }

    @Test
    public void saveAndAddToClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setAddress("test");
        clientDTO.setCnp("1990101101010");
        clientDTO.setIDCardNumber("TT133337");
        clientDTO.setFirstname("Test");
        clientDTO.setSurname("Test");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setType(DEBIT);
        accountDTO.setAmountOfMoney(20.0);
        accountDTO.setNumber(UUID.randomUUID().toString());

        clientService.saveWOAccount(clientDTO);

        Client client = clientService.getAllClients().get(0);
        clientDTO.setId(client.getId());

        assertFalse(clientService.saveAndAddToClient(accountDTO, clientDTO).hasErrors());
    }

    @Test
    public void deleteClientById() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setAddress("test");
        clientDTO.setCnp("1990101101010");
        clientDTO.setIDCardNumber("TT133337");
        clientDTO.setFirstname("Test");
        clientDTO.setSurname("Test");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setType(DEBIT);
        accountDTO.setAmountOfMoney(20.0);
        accountDTO.setNumber(UUID.randomUUID().toString());

        clientService.saveWOAccount(clientDTO);

        Client client = clientService.getAllClients().get(0);
        clientService.deleteClientById(client.getId());

        assertEquals(0, clientService.getAllClients().size());
        assertNull(clientService.findByNumber(accountDTO.getNumber()));
    }
}
