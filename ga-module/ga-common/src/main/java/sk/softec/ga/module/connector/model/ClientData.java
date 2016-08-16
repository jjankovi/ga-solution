package sk.softec.ga.module.connector.model;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public class ClientData {

    private String clientId;

    private Long ltv;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getLtv() {
        return ltv;
    }

    public void setLtv(Long ltv) {
        this.ltv = ltv;
    }
}
