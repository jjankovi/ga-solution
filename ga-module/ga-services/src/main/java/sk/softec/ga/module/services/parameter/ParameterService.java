package sk.softec.ga.module.services.parameter;

import java.time.LocalDateTime;

/**
 * Created by jankovj on 22. 8. 2016.
 */
public interface ParameterService {

    void setValue(String paramName, Object paramValue);

    LocalDateTime getParamAsDateTime(String paramName);

    Integer getParamAsInt(String paramName);

}
