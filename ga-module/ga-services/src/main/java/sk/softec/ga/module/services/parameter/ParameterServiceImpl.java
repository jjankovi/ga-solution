package sk.softec.ga.module.services.parameter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sk.softec.ga.module.services.model.AppParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jankovj on 22. 8. 2016.
 */
@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

    private static final Logger log = LoggerFactory.getLogger(ParameterServiceImpl.class);
    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void setValue(String paramName, Object paramValue) {
        log.debug("Setting parameter with name {} to new value {}", paramName, paramValue);

        AppParam appParam = _getAppParam(paramName);

        if (paramValue == null) {
            appParam.setValue(null);
        } else {
            switch (appParam.getParamType()) {
                case NUMBER:
                    appParam.setValue(paramValue.toString());
                    break;
                case DATE_TIME:
                    LocalDateTime localDateTime = (LocalDateTime)paramValue;
                    appParam.setValue(localDateTime.format(df));
                    break;
            }
        }
    }

    @Override
    public LocalDateTime getParamAsDateTime(String paramName) {
        AppParam appParam = _getAppParam(paramName);
        String appParamValue = appParam.getValue();
        if (StringUtils.isEmpty(appParamValue)) {
            return null;
        }

        return LocalDateTime.parse(appParamValue, df);
    }

    @Override
    public Integer getParamAsInt(String paramName) {
        AppParam appParam = _getAppParam(paramName);
        String appParamValue = appParam.getValue();

        return Integer.parseInt(appParamValue);
    }

    private AppParam _getAppParam(String name) {
        Session session = sessionFactory.getCurrentSession();

        AppParam appParam = (AppParam) session.get(AppParam.class, name);
        return appParam;
    }

}
