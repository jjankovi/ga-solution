package sk.softec.ga.module.services.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.api.CRMConnector;
import sk.softec.ga.module.connector.api.ClientDBConnector;
import sk.softec.ga.module.connector.exception.CRMConnectionException;
import sk.softec.ga.module.connector.model.crm.ClientData;
import sk.softec.ga.module.connector.model.clientdb.ClientIdentity;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
public class ClientDataProviderImpl implements ClientDataProvider {

    @Autowired
    private CRMConnector crmConnector;

    @Autowired
    private ClientDBConnector clientDBConnector;

    @Override
    public ClientIdentity getClientIdentity(String login) {
        return clientDBConnector.getClientIdentity(login);
    }

    @Override
    public ClientData getClientData(String clientId) throws CRMConnectionException {
        return crmConnector.getClientData(clientId);
    }
}
