package sk.softec.ga.module.services.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sk.softec.ga.module.services.ModuleService;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Component
public class GaScheduler {

    @Autowired
    ModuleService moduleService;

    @Scheduled(fixedRate = 2000)
    public void checkClientEvents() {
        System.out.println("Client events check started...");
        moduleService.checkNewClientEvents();
    }

}
