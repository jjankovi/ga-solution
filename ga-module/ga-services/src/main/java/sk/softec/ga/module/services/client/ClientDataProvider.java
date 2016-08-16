package sk.softec.ga.module.services.client;

import sk.softec.ga.module.connector.model.ClientData;
import sk.softec.ga.module.connector.model.ClientIdentity;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface ClientDataProvider {

    ClientIdentity getClientIdentity(String login);

    ClientData getClientData(String clientId);

}