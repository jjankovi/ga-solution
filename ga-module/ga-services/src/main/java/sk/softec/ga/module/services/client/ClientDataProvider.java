package sk.softec.ga.module.services.client;

import sk.softec.ga.module.connector.exception.CRMConnectionException;
import sk.softec.ga.module.connector.model.crm.ClientData;
import sk.softec.ga.module.connector.model.clientdb.ClientIdentity;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface ClientDataProvider {

    ClientIdentity getClientIdentity(String login);

    ClientData getClientData(String clientId) throws CRMConnectionException;

}
