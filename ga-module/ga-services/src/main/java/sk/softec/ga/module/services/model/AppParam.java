package sk.softec.ga.module.services.model;

import javax.persistence.*;

/**
 * Created by jankovj on 22. 8. 2016.
 */
@Entity
@Table(name = "APP_PARAM")
public class AppParam {

    public static final String CRM_EVENT_READ_LAST_TIME = "CRM_EVENT_READ_LAST_TIME";
    public static final String CRM_EVENT_READ_BATCH_SIZE= "CRM_EVENT_READ_BATCH_SIZE";

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    private String code;

    private String value;

    @Column(name = "PARAM_TYPE")
    @Enumerated(EnumType.STRING)
    private AppParamType paramType;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AppParamType getParamType() {
        return paramType;
    }

    public void setParamType(AppParamType paramType) {
        this.paramType = paramType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
