package sk.softec.ga.module.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.api.BankConnector;
import sk.softec.ga.module.api.model.CRMData;
import sk.softec.ga.module.api.model.CRMEvent;
import sk.softec.ga.module.api.model.Subject;
import sk.softec.ga.module.exception.CRMConnectionException;
import sk.softec.ga.module.services.cid.CIDGenerator;
import sk.softec.ga.module.services.event.GAEventSendException;
import sk.softec.ga.module.services.event.GAEventSender;
import sk.softec.ga.module.services.model.AppParam;
import sk.softec.ga.module.services.model.GAEvent;
import sk.softec.ga.module.services.parameter.ParameterService;

import java.time.LocalDateTime;
import java.util.Date;
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
    GAEventSender gaEventSender;

    @Autowired
    BankConnector bankConnector;

    @Autowired
    ParameterService parameterService;

    @Override
    public String generateCID(String input) {
        log.debug("Generating CID for input {}...", input);

        return cidGenerator.generateCID(input);
    }

    @Override
    public Subject getSubjectWithGeneratedCID(String input) {
        log.debug("Generating CID with client data for input {}...", input);

        Subject subject = bankConnector.findSubject(input, null, null, null, null);
        subject.setCid(cidGenerator.generateCID(input));
        if (subject != null) {
            try {
                CRMData crmData = bankConnector.getSubjectCRMData(subject.getUid());
                subject.setCrmData(crmData);;
            } catch (CRMConnectionException exc) {
                log.error("Error when getting Client Data.", exc);
            }
        }
        return subject;
    }

    @Override
    public void checkCRMEvents() {
        Date fromDate = parameterService.getParamAsDate(AppParam.CRM_EVENT_READ_LAST_TIME);
        Integer batchSize = parameterService.getParamAsInt(AppParam.CRM_EVENT_READ_BATCH_SIZE);

        try {
            List<CRMEvent> crmEvents = bankConnector.loadCRMEvents(fromDate, batchSize);
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
        gaEvent.setCreationTs(LocalDateTime.now());

        gaEventSender.sendEvent(gaEvent);
    }

    private GAEvent prepareGAEvent(CRMEvent crmEvent) throws CRMConnectionException {
        CRMData crmData = bankConnector.getSubjectCRMData(crmEvent.getUid());

        GAEvent gaEvent = new GAEvent();
        gaEvent.setCid(generateCID(crmData.getUid()));
        // TODO add other information to GAEvent

        return gaEvent;
    }
}
