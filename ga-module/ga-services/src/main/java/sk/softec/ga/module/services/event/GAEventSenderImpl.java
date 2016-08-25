package sk.softec.ga.module.services.event;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.model.GAEvent;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class GAEventSenderImpl implements GAEventSender {

    private static final Logger log = LoggerFactory.getLogger(GAEventSenderImpl.class);

    // TODO config parameter
    private static final String GA_APP_ID = "UA-82477820-1";

    @Override
    public void sendEvent(GAEvent gaEvent) throws GAEventSendException {
        try {
            log.debug("Sending GA Event to " + GA_APP_ID + " ...");

            GoogleAnalytics ga = new GoogleAnalytics(GA_APP_ID);
            ga.post(new PageViewHit("http://alf.softec.sk:8080/clientdb", "IB Test"));
            log.debug("GA Event sent to " + GA_APP_ID);

        } catch (Exception exc) {
            log.error(exc.getLocalizedMessage());
            throw new GAEventSendException();
        }
    }
}
