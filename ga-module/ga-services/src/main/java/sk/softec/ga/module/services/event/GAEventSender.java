package sk.softec.ga.module.services.event;

import sk.softec.ga.module.services.model.GAEvent;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public interface GAEventSender {

    void sendEvent(GAEvent gaEvent) throws GAEventSendException;

    GAEvent saveGAEvent(GAEvent gaEvent);

}
