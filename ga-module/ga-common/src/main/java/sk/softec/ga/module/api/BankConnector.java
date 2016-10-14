package sk.softec.ga.module.api;

import sk.softec.ga.module.api.model.CRMData;
import sk.softec.ga.module.api.model.CRMEvent;
import sk.softec.ga.module.api.model.Product;
import sk.softec.ga.module.api.model.Subject;
import sk.softec.ga.module.exception.CRMConnectionException;

import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 14. 10. 2016.
 */
public interface BankConnector {

    /* CLIENT DB interface */
    Subject findSubject(String firstName, String lastName, Date birthDate, String rc, String ico);

    /* CRM interface */
    CRMData getSubjectCRMData(String uid) throws CRMConnectionException;

    List<CRMEvent> loadCRMEvents(Date fromDate, Integer batchSize) throws CRMConnectionException;

    /* Product interface */
    Product findSubjectProducts(String uid);

}
