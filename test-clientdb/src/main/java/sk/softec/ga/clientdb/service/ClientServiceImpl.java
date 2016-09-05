package sk.softec.ga.clientdb.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sk.softec.ga.clientdb.model.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Client> getAllClients(String login, String name) {
        Session session = sessionFactory.getCurrentSession();

        StringBuilder hql = new StringBuilder("FROM Client ");

        hql.append("WHERE 1=1");

        Map<String, Object> queryParams = new HashMap<>();
        if (!StringUtils.isEmpty(login)) {
            hql.append(" AND login=:login");
            queryParams.put("login", login);
        }
        if (!StringUtils.isEmpty(name)) {
            hql.append(" AND (firstName=:name or lastName=:name)");
            queryParams.put("name", name);
        }

        Query query = session.createQuery(hql.toString());
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.list();
    }

    @Override
    public Client login(String login, String password) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Client where login=:login and password=:password")
                .setParameter("login", login)
                .setParameter("password", password);
        List loginPassedClients = query.list();
        if (loginPassedClients.size() > 0) {
            return (Client) loginPassedClients.get(0);
        } else {
            return null;
        }
    }

}
