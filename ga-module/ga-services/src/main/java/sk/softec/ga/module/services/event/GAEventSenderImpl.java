package sk.softec.ga.module.services.event;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.softec.ga.module.connector.model.GAEvent;
import sk.softec.ga.module.services.model.GAEventLog;
import sk.softec.ga.module.services.model.GAEventStatus;

import java.time.LocalDateTime;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class GAEventSenderImpl implements GAEventSender {

    private static final Logger log = LoggerFactory.getLogger(GAEventSenderImpl.class);

    // TODO GA_APP_ID as app parameter
    private static final String GA_APP_ID = "UA-82477820-1";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GAEventSender self;

    @Override
    public void sendEvent(GAEvent gaEvent) throws GAEventSendException {
        log.debug("Sending GA Event to " + GA_APP_ID + " ...");

        GAEventLog eventLog = _prepareGAEventLog();
        eventLog = _saveGAEventStatus(eventLog, GAEventStatus.SENDING);
        try {
            GoogleAnalytics ga = new GoogleAnalytics(GA_APP_ID);
            ga.post(new PageViewHit("http://alf.softec.sk:8080/clientdb", "IB Test"));
        } catch (Exception exc) {
            log.error(exc.getLocalizedMessage());
            _saveGAEventStatus(eventLog, GAEventStatus.ERROR);
            throw new GAEventSendException();
        }
        log.debug("GA Event sent to " + GA_APP_ID);
        _saveGAEventStatus(eventLog, GAEventStatus.SUCCESS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GAEventLog saveGAEventLog(GAEventLog eventLog) {
        Session session = sessionFactory.getCurrentSession();

        return (GAEventLog) session.merge(eventLog);
    }

    private GAEventLog _prepareGAEventLog() {
        GAEventLog eventLog = new GAEventLog();
        eventLog.setGaAppId(GA_APP_ID);
        eventLog.setCreationTs(LocalDateTime.now());

        return eventLog;
    }

    private GAEventLog _saveGAEventStatus(GAEventLog eventLog, GAEventStatus eventStatus) {
        eventLog.setEventStatus(eventStatus);
        return self.saveGAEventLog(eventLog);
    }
}
