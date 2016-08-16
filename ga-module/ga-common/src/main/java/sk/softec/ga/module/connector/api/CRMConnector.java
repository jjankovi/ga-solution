package sk.softec.ga.module.connector.api;

import sk.softec.ga.module.connector.model.ClientData;
import sk.softec.ga.module.connector.model.ClientEvent;

import java.util.List;
import java.util.Date;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface CRMConnector {

    ClientData getClientData(String clientId);

    List<ClientEvent> getClientEvents(Date fromDate);

}
