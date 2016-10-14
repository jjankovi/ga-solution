package sk.softec.ga.module.api.model;

import java.time.LocalDateTime;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public class CRMEvent {

    private Long id;

    private String uid;

    private String eventType;

    private LocalDateTime creationTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
