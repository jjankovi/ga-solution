package sk.softec.ga.module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.model.*;
import sk.softec.ga.module.services.cid.CIDGenerator;
import sk.softec.ga.module.services.client.ClientDataProvider;
import sk.softec.ga.module.services.crm.CRMEventReader;
import sk.softec.ga.module.services.event.GAEventLogger;

import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    CIDGenerator cidGenerator;

    @Autowired
    ClientDataProvider clientDataProvider;

    @Autowired
    CRMEventReader crmEventReader;

    @Autowired
    GAEventLogger gaEventLogger;

    @Override
    public String generateCID(String input) {
        return cidGenerator.generateCID(input);
    }

    @Override
    public CidData generateCIDData(String input) {
        CidData cidData = new CidData();

        cidData.setCid(cidGenerator.generateCID(input));
        ClientIdentity identity = clientDataProvider.getClientIdentity(input);
        if (identity != null) {
            cidData.setClientData(clientDataProvider.getClientData(identity.getId()));
        }
        return cidData;
    }

    @Override
    public void checkCRMEvents() {
        // TODO obtain last checked events time
        Date fromDate = null;
        List<CRMEvent> crmEvents = crmEventReader.getCRMEvents(fromDate);
        for (CRMEvent crmEvent : crmEvents) {
            ClientData clientData = clientDataProvider.getClientData(crmEvent.getClientId());

            // TODO construct data for GA
            GAEvent gaEvent = null;
            sendGAEvent(gaEvent);
        }
    }

    @Override
    public void sendGAEvent(GAEvent gaEvent) {
        gaEventLogger.logEvent(gaEvent);
    }
}
