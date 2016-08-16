package sk.softec.ga.crm.service;

import sk.softec.ga.crm.model.CRMEvent;
import sk.softec.ga.crm.model.CRMClientRate;
import sk.softec.ga.crm.model.CRMEventType;
import java.util.Date;

import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface CRMService {

    boolean logEvent(Long clientId, CRMEventType eventType);

    List<CRMEvent> getAllEvents(Date fromDate);

    CRMClientRate getClientRate(Long clientId);

}
