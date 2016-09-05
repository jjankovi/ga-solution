package sk.softec.ga.clientdb.service;

import sk.softec.ga.clientdb.model.Client;

import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface ClientService {

    List<Client> getAllClients(String login, String name);

    Client login(String login, String password);
}
