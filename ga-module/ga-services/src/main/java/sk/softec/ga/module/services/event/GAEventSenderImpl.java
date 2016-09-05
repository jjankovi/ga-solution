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
import sk.softec.ga.module.services.model.GAEvent;
import sk.softec.ga.module.services.model.GAEventStatus;

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
        gaEvent.setGaAppId(GA_APP_ID);
        log.debug("Sending GA Event to " + gaEvent.getGaAppId() + " ...");

        gaEvent = _saveGAEventStatus(gaEvent, GAEventStatus.SENDING);
        try {
            // TODO use CID to identify client

            GoogleAnalytics ga = new GoogleAnalytics(GA_APP_ID);
            ga.post(new PageViewHit("http://alf.softec.sk:8080/clientdb", "IB Test"));
        } catch (Exception exc) {
            log.error(exc.getLocalizedMessage());
            _saveGAEventStatus(gaEvent, GAEventStatus.ERROR);
            throw new GAEventSendException();
        }
        log.debug("GA Event sent to " + GA_APP_ID);
        _saveGAEventStatus(gaEvent, GAEventStatus.SUCCESS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GAEvent saveGAEvent(GAEvent gaEvent) {
        Session session = sessionFactory.getCurrentSession();
        return (GAEvent) session.merge(gaEvent);
    }

    private GAEvent _saveGAEventStatus(GAEvent gaEvent, GAEventStatus eventStatus) {
        gaEvent.setEventStatus(eventStatus);
        return self.saveGAEvent(gaEvent);
    }
}
