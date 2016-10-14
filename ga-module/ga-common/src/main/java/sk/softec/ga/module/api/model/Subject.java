package sk.softec.ga.module.api.model;

import java.util.Date;

/**
 * Created by jankovj on 14. 10. 2016.
 */
public class Subject {

    private String uid;

    private String cid;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String rc;

    private String ico;

    private CRMData crmData;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public CRMData getCrmData() {
        return crmData;
    }

    public void setCrmData(CRMData crmData) {
        this.crmData = crmData;
    }
}
