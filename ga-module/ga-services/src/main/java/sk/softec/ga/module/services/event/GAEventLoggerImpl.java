package sk.softec.ga.module.services.event;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.model.GAEvent;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class GAEventLoggerImpl implements GAEventLogger {

    // TODO config parameter
    private static final String GA_APP_ID = "UA-82477820-1";

    @Override
    public boolean logEvent(GAEvent gaEvent) {
        GoogleAnalytics ga = new GoogleAnalytics(GA_APP_ID);
        ga.post(new PageViewHit("http://alf.softec.sk:8080/clientdb", "IB Test"));
        System.out.println("GA Event logged");
        return true;
    }
}
