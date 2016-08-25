package sk.softec.ga.crm.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Entity
@Table(name = "CRM_EVENT")
public class CRMEvent {

    private static final String SEQUENCE_NAME = "CRM_EVENT_SEQ";

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    @GenericGenerator(name = SEQUENCE_NAME, strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value=SEQUENCE_NAME)})
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="CLIENT_ID")
    private CRMClientRate clientRate;

    @Column(name = "EVENT_TYPE")
    @Enumerated(EnumType.STRING)
    private CRMEventType eventType;

    @Column(name = "CREATION_TS")
    private Date creationTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CRMClientRate getClientRate() {
        return clientRate;
    }

    public void setClientRate(CRMClientRate clientRate) {
        this.clientRate = clientRate;
    }

    public CRMEventType getEventType() {
        return eventType;
    }

    public void setEventType(CRMEventType eventType) {
        this.eventType = eventType;
    }

    public Date getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(Date creationTs) {
        this.creationTs = creationTs;
    }
}
