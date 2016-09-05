package sk.softec.ga.crm.model.dto;

import sk.softec.ga.crm.model.CRMEventType;

/**
 * Created by jankovj on 5. 9. 2016.
 */
public class CRMEventContainer {

    private CRMEventType eventType;

    public CRMEventType getEventType() {
        return eventType;
    }

    public void setEventType(CRMEventType eventType) {
        this.eventType = eventType;
    }
}
