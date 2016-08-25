package sk.softec.ga.module.connector.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public class CRMEvent {

    private Long id;

    private String clientId;

    private String eventType;

    private LocalDateTime creationTs;

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

    public LocalDateTime getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(LocalDateTime creationTs) {
        this.creationTs = creationTs;
    }
}
