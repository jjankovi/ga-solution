package sk.softec.ga.module.services.client;

import sk.softec.ga.module.connector.model.ClientEvent;

import java.util.List;
import java.util.Date;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface ClientEventReader {

    List<ClientEvent> getClientEvents(Date fromDate);

}
