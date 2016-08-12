package sk.softec.go.clientdb.service;

import sk.softec.go.clientdb.model.Client;

import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface ClientService {

    List<Client> getAllClients();

    Client login(String login, String password);

    Client searchByName(String name);

}
