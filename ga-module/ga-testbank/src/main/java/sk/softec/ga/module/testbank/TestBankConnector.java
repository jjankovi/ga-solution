package sk.softec.ga.module.testbank;

import org.springframework.stereotype.Service;
import sk.softec.ga.module.api.BankConnector;
import sk.softec.ga.module.api.model.CRMData;
import sk.softec.ga.module.api.model.CRMEvent;
import sk.softec.ga.module.api.model.Product;
import sk.softec.ga.module.api.model.Subject;
import sk.softec.ga.module.exception.CRMConnectionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 14. 10. 2016.
 */
@Service
public class TestBankConnector implements BankConnector {

    @Override
    public Subject findSubject(String firstName, String lastName, Date birthDate, String rc, String ico) {
        Subject subject = new Subject();
        subject.setFirstName(firstName);
        subject.setLastName(lastName);
        subject.setBirthDate(birthDate);

        return subject;
    }

    @Override
    public CRMData getSubjectCRMData(String uid) {
        CRMData crmData = new CRMData();
        crmData.setLtv(12L);

        return crmData;
    }

    @Override
    public List<CRMEvent> loadCRMEvents(Date fromDate, Integer batchSize) throws CRMConnectionException {
        return new ArrayList<>();
    }

    @Override
    public Product findSubjectProducts(String uid) {
        return new Product();
    }
}
