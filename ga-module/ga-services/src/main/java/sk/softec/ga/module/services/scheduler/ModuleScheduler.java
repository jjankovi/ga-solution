package sk.softec.ga.module.services.scheduler;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.services.GAModuleCntxRefreshedAppListener;
import sk.softec.ga.module.services.ModuleService;
import sk.softec.ga.module.services.model.AppParam;
import sk.softec.ga.module.services.parameter.ParameterService;

import java.util.Date;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class ModuleScheduler {

    private static final Logger log = LoggerFactory.getLogger(ModuleScheduler.class);

    @Autowired
    ModuleService moduleService;

    @Autowired
    ParameterService parameterService;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private GAModuleCntxRefreshedAppListener appListener;

    public void scheduleCRMEventRead(Date nextRead) {
        log.info("Schedulling next trigger for ModuleService.checkCRMEvents to {} ...", nextRead);
        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                executeAndScheduleCRMEventRead();
            }
        }, nextRead);
    }

    private void executeAndScheduleCRMEventRead() {
        if (appListener.isMaster()) {
            Date now = new Date();
            try {
                log.debug("Starting ModuleService.checkCRMEvents() ...");
                moduleService.checkCRMEvents();
            } finally {
                scheduleCRMEventRead(getNextRuntime(AppParam.CRM_EVENT_READ_INTERVAL, now));
            }
        }
    }

    private Date getNextRuntime(String paramName, Date now) {
        Integer schedulePeriodParam = parameterService.getParamAsInt(paramName);
        return DateUtils.addSeconds(now, schedulePeriodParam);
    }

}
