package sk.softec.ga.module.services.parameter;

import java.util.Date;

/**
 * Created by jankovj on 22. 8. 2016.
 */
public interface ParameterService {

    void setValue(String paramName, Object paramValue);

    Date getParamAsDate(String paramName);

    Integer getParamAsInt(String paramName);

}
