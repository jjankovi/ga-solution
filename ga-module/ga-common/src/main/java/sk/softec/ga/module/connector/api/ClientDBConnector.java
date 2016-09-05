package sk.softec.ga.module.connector.api;

import sk.softec.ga.module.connector.model.clientdb.ClientIdentity;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface ClientDBConnector {

    ClientIdentity getClientIdentity(String login);

}
