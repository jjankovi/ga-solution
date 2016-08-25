package sk.softec.ga.module.services.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sk.softec.ga.module.services.ModuleService;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Component
public class ModuleScheduler {

    private static final Logger log = LoggerFactory.getLogger(ModuleScheduler.class);

    @Autowired
    ModuleService moduleService;

    @Scheduled(fixedRate = 15000)
    public void checkClientEvents() {
        log.info("CRM events check started...");
        moduleService.checkCRMEvents();
        log.info("CRM events check finished.");
    }

}
