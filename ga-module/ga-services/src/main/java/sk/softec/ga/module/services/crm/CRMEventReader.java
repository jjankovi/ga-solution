package sk.softec.ga.module.services.crm;

import sk.softec.ga.module.connector.exception.CRMConnectionException;
import sk.softec.ga.module.connector.model.crm.CRMEvent;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
public interface CRMEventReader {

    List<CRMEvent> getCRMEvents(LocalDateTime fromDate, Integer batchSize) throws CRMConnectionException;

}
