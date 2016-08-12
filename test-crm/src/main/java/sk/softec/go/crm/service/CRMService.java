package sk.softec.go.crm.service;

import sk.softec.go.crm.model.CRMClientRate;
import sk.softec.go.crm.model.CRMEvent;
import sk.softec.go.crm.model.CRMEventType;

import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface CRMService {

    boolean logEvent(Long clientId, CRMEventType eventType);

    List<CRMEvent> getAllEvents();

    CRMClientRate getClientRate(Long clientId);

}
