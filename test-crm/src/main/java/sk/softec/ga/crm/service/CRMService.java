package sk.softec.ga.crm.service;

import sk.softec.ga.crm.model.CRMClientRate;
import sk.softec.ga.crm.model.CRMEvent;
import sk.softec.ga.crm.model.dto.CRMEventContainer;

import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface CRMService {

    boolean logEvent(Long clientId, CRMEventContainer eventContainer);

    List<CRMEvent> getAllEvents(Date fromDate, Integer batchSize);

    CRMClientRate getClientRate(Long clientId);

}
