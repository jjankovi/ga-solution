package sk.softec.ga.module.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.exception.CRMConnectionException;
import sk.softec.ga.module.connector.model.*;
import sk.softec.ga.module.services.cid.CIDGenerator;
import sk.softec.ga.module.services.client.ClientDataProvider;
import sk.softec.ga.module.services.crm.CRMEventReader;
import sk.softec.ga.module.services.event.GAEventSendException;
import sk.softec.ga.module.services.event.GAEventSender;
import sk.softec.ga.module.services.model.AppParam;
import sk.softec.ga.module.services.parameter.ParameterService;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    private static final Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

    @Autowired
    CIDGenerator cidGenerator;

    @Autowired
    ClientDataProvider clientDataProvider;

    @Autowired
    CRMEventReader crmEventReader;

    @Autowired
    GAEventSender gaEventSender;

    @Autowired
    ParameterService parameterService;

    @Override
    public String generateCID(String input) {
        log.debug("Generating CID for input {}...", input);

        return cidGenerator.generateCID(input);
    }

    @Override
    public CidData generateCIDData(String input) {
        log.debug("Generating CID with client data for input {}...", input);

        CidData cidData = new CidData();

        cidData.setCid(cidGenerator.generateCID(input));
        ClientIdentity identity = clientDataProvider.getClientIdentity(input);
        if (identity != null) {
            try {
                cidData.setClientData(clientDataProvider.getClientData(identity.getId()));
            } catch (CRMConnectionException exc) {
                log.error("Error when getting Client Data.", exc);
            }
        }
        return cidData;
    }

    @Override
    public void checkCRMEvents() {
        LocalDateTime fromDate = parameterService.getParamAsDateTime(AppParam.CRM_EVENT_READ_LAST_TIME);
        Integer batchSize = parameterService.getParamAsInt(AppParam.CRM_EVENT_READ_BATCH_SIZE);

        try {
            List<CRMEvent> crmEvents = crmEventReader.getCRMEvents(fromDate, batchSize);
            log.debug("{} CRM events read fromDate: {} and batchSize: {}", new Object[]{crmEvents.size(), fromDate, batchSize});

            Iterator<CRMEvent> iter = crmEvents.iterator();
            while (iter.hasNext()) {
                CRMEvent crmEvent = iter.next();

                GAEvent gaEvent = prepareGAEvent(crmEvent);
                sendGAEvent(gaEvent);

                if (!iter.hasNext()) {
                    parameterService.setValue(AppParam.CRM_EVENT_READ_LAST_TIME, crmEvent.getCreationTs());
                }
            }
        } catch (Exception exc) {
            log.error("Error when checking CRM events", exc);
        }
    }

    @Override
    public void sendGAEvent(GAEvent gaEvent) throws GAEventSendException {
        gaEventSender.sendEvent(gaEvent);
    }

    private GAEvent prepareGAEvent(CRMEvent crmEvent) throws CRMConnectionException {
        ClientData clientData = clientDataProvider.getClientData(crmEvent.getClientId());

        GAEvent gaEvent = new GAEvent();
        gaEvent.setCid(generateCID(clientData.getClientId()));
        // TODO add other information to GAEvent

        return gaEvent;
    }
}
