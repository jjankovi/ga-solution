package sk.softec.ga.module.services.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by jankovj on 19. 8. 2016.
 */
@Entity
@Table(name = "GA_EVENT")
public class GAEvent {

    private static final String SEQUENCE_NAME = "GA_EVENT_SEQ";

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "CID")
    private String cid;

    @Column(name = "CREATION_TS")
    private LocalDateTime creationTs;

    @Column(name = "GA_EVENT_STATUS")
    @Enumerated(EnumType.STRING)
    private GAEventStatus eventStatus;

    @Column(name = "GA_APP_ID")
    private String gaAppId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public LocalDateTime getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(LocalDateTime creationTs) {
        this.creationTs = creationTs;
    }

    public GAEventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(GAEventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getGaAppId() {
        return gaAppId;
    }

    public void setGaAppId(String gaAppId) {
        this.gaAppId = gaAppId;
    }
}
