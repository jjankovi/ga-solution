package sk.softec.ga.module.services.event;

import sk.softec.ga.module.connector.model.CRMEvent;
import sk.softec.ga.module.connector.model.GAEvent;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public interface GAEventSender {

    void sendEvent(GAEvent gaEvent) throws GAEventSendException;

}
