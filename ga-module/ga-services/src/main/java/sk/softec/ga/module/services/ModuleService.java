package sk.softec.ga.module.services;

import sk.softec.ga.module.api.model.Subject;
import sk.softec.ga.module.services.event.GAEventSendException;
import sk.softec.ga.module.services.model.GAEvent;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public interface ModuleService {

    String generateCID(String input);

    Subject getSubjectWithGeneratedCID(String input);

    void checkCRMEvents();

    void sendGAEvent(GAEvent gaEvent) throws GAEventSendException;
}
