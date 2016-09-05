package sk.softec.ga.module.connector.model;

import sk.softec.ga.module.connector.model.crm.ClientData;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public class CidData {

    private String cid;

    private ClientData clientData;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }
}
