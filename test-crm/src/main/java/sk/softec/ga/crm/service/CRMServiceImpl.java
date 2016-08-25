package sk.softec.ga.crm.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.softec.ga.crm.model.CRMClientRate;
import sk.softec.ga.crm.model.CRMEvent;
import sk.softec.ga.crm.model.CRMEventType;

import java.util.*;

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
    public List<CRMEvent> getAllEvents(Date fromDate, Integer batchSize) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "from CRMEvent";
        Map<String, Object> params = new HashMap<String, Object>();
        if (fromDate != null) {
            hql += " where creationTs > :creationTs";
            params.put("creationTs", fromDate);
        }
        hql += " order by creationTs asc";

        Query query = session.createQuery(hql);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        if (batchSize != null) {
            query.setMaxResults(batchSize);
        }
        return query.list();
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
