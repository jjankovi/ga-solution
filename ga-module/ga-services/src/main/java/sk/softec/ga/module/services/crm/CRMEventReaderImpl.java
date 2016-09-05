package sk.softec.ga.module.services.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.api.CRMConnector;
import sk.softec.ga.module.connector.exception.CRMConnectionException;
import sk.softec.ga.module.connector.model.crm.CRMEvent;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
public class CRMEventReaderImpl implements CRMEventReader {

    @Autowired
    private CRMConnector crmConnector;

    @Override
    public List<CRMEvent> getCRMEvents(LocalDateTime fromDate, Integer batchSize) throws CRMConnectionException {
        return crmConnector.getClientEvents(fromDate, batchSize);
    }
}
