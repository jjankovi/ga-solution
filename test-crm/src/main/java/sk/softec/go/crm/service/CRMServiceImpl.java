package sk.softec.go.crm.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.softec.go.crm.model.CRMClientRate;
import sk.softec.go.crm.model.CRMEvent;
import sk.softec.go.crm.model.CRMEventType;

import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
@Transactional
public class CRMServiceImpl implements CRMService {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean logEvent(Long clientId, CRMEventType eventType) {
        if (clientId != null && eventType != null) {
            Session session = sessionFactory.getCurrentSession();

            CRMClientRate clientRate = (CRMClientRate) session.byId(CRMClientRate.class).load(clientId);

            CRMEvent crmEvent = new CRMEvent();
            crmEvent.setClientRate(clientRate);
            crmEvent.setCreationTs(new Date());
            crmEvent.setEventType(eventType);
            session.save(crmEvent);
            return true;
        }
        return false;
    }

    @Override
    public List<CRMEvent> getAllEvents() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from CRMEvent").list();
    }

    @Override
    public CRMClientRate getClientRate(Long clientId) {
        Session session = sessionFactory.getCurrentSession();

        List clientRates = session.createQuery("from CRMClientRate where clientId=:clientId")
                .setParameter("clientId", clientId)
                .list();
        if (clientRates.size() > 0) {
            return (CRMClientRate) clientRates.get(0);
        } else {
            return null;
        }
    }
}
