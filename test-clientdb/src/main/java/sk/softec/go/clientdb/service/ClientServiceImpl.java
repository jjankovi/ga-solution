package sk.softec.go.clientdb.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.softec.go.clientdb.model.Client;

import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Client> getAllClients() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("FROM Client").list();
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

    @Override
    public Client searchByName(String name) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Client where name=:name")
                .setParameter("name", name);
        List loginPassedClients = query.list();
        if (loginPassedClients.size() > 0) {
            return (Client) loginPassedClients.get(0);
        } else {
            return null;
        }
    }
}
