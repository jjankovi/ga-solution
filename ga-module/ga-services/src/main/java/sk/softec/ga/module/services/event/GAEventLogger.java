package sk.softec.ga.module.services.event;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.model.ClientEvent;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class GAEventLogger implements EventLogger {

    // TODO config parameter
    private static final String GA_APP_ID = "UA-82477820-1";

    @Override
    public boolean logClientEvent(ClientEvent clientEvent) {
        GoogleAnalytics ga = new GoogleAnalytics(GA_APP_ID);
        ga.post(new PageViewHit("http://alf.softec.sk:8080/clientdb", "IB Test"));

        return true;
    }
}
