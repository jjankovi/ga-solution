package sk.softec.ga.module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.model.CidData;
import sk.softec.ga.module.connector.model.ClientData;
import sk.softec.ga.module.connector.model.ClientEvent;
import sk.softec.ga.module.connector.model.ClientIdentity;
import sk.softec.ga.module.services.cid.CIDGenerator;
import sk.softec.ga.module.services.client.ClientDataProvider;
import sk.softec.ga.module.services.client.ClientEventReader;
import sk.softec.ga.module.services.event.EventLogger;

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
    ClientEventReader clientEventReader;

    @Autowired
    EventLogger eventLogger;

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
    public void checkNewClientEvents() {
        // TODO obtain last checked events time
        Date fromDate = null;
        List<ClientEvent> clientEvents = clientEventReader.getClientEvents(fromDate);
        for (ClientEvent clientEvent : clientEvents) {
            ClientData clientData = clientDataProvider.getClientData(clientEvent.getClientId());
            // TODO construct data for GA
        }
    }
}
