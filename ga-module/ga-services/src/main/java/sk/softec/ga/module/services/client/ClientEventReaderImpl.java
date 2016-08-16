package sk.softec.ga.module.services.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.api.CRMConnector;
import sk.softec.ga.module.connector.model.ClientEvent;

import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
public class ClientEventReaderImpl implements ClientEventReader {

    @Autowired
    private CRMConnector crmConnector;

    @Override
    public List<ClientEvent> getClientEvents(Date fromDate) {
        return crmConnector.getClientEvents(fromDate);
    }
}
