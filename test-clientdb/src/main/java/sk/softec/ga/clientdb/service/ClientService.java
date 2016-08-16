package sk.softec.ga.clientdb.service;

import sk.softec.ga.clientdb.model.Client;

import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface ClientService {

    List<Client> getAllClients();

    Client login(String login, String password);

    Client searchByLogin(String login);

    Client searchByName(String name);
}
