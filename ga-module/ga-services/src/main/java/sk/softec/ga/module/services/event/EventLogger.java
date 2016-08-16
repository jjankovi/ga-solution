package sk.softec.ga.module.services.event;

import sk.softec.ga.module.connector.model.ClientEvent;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public interface EventLogger {

    boolean logClientEvent(ClientEvent clientEvent);

}
