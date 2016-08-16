package sk.softec.ga.module.connector.model;

import java.util.Date;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public class ClientEvent {

    private Long id;

    private String clientId;

    private String eventType;

    private Date creationTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(Date creationTs) {
        this.creationTs = creationTs;
    }
}
