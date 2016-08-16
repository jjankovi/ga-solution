package sk.softec.ga.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Entity
@Table(name = "CRM_CLIENT_RATE")
public class CRMClientRate {

    @Id
    @Column(name = "CLIENT_ID", unique = true, nullable = false, precision = 22, scale = 0)
    private Long clientId;

    private Long ltv;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getLtv() {
        return ltv;
    }

    public void setLtv(Long ltv) {
        this.ltv = ltv;
    }
}
