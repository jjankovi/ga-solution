package sk.softec.ga.module.taba;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.api.BankConnector;
import sk.softec.ga.module.api.model.CRMData;
import sk.softec.ga.module.api.model.CRMEvent;
import sk.softec.ga.module.api.model.Product;
import sk.softec.ga.module.api.model.Subject;
import sk.softec.ga.module.exception.CRMConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 14. 10. 2016.
 */
@Service
@Primary
public class TabaBankConnector implements BankConnector {

    private static final Logger log = LoggerFactory.getLogger(TabaBankConnector.class);

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.mm.yyyy");

    private static final String CLIENT_DB_URL = "http://localhost:8080/clientdb/api";
    private static final String CRM_CLIENT_RATE_URL = "http://localhost:8080/crm/api/clientRate/";
    private static final String CRM_CLIENT_EVENT_URL = "http://localhost:8080/crm/api/event?";

    @Override
    public Subject findSubject(String firstName, String lastName, Date birthDate, String rc, String ico) {
        String response = fetchClientIdentity(firstName);
        return parseResponse(response);
    }

    @Override
    public CRMData getSubjectCRMData(String uid) throws CRMConnectionException {
        String response = fetchClientData(uid);
        return parseCRMDataResponse(response);
    }

    @Override
    public List<CRMEvent> loadCRMEvents(Date fromDate, Integer batchSize) throws CRMConnectionException {
        String response = fetchClientEvents(fromDate, batchSize);
        return parseClientEventResponse(response);
    }

    @Override
    public Product findSubjectProducts(String uid) {
        return null;
    }

    private String fetchClientIdentity(String login) {
        try {
            log.debug("Fetching client identity for login {}", login);

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet get = new HttpGet(CLIENT_DB_URL + "?login=" + login);
            get.addHeader("content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            StringBuffer buffer = new StringBuffer();
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception exc) {

        }
        return null;
    }

    private Subject parseResponse(String response) {
        JSONArray parsedResponse = new JSONArray(response);

        if (parsedResponse.length() != 1) {
            return null;
        }

        JSONObject client = (JSONObject) parsedResponse.get(0);
        Subject identity = new Subject();
        identity.setUid(String.valueOf(client.getLong("id")));
        identity.setFirstName(client.getString("firstName"));
        identity.setLastName(client.getString("lastName"));

        return identity;
    }

    private String fetchClientData(String uid) throws CRMConnectionException {
        try {
            log.debug("Fetching crm client data for uid {}", uid);

            return doGetOnUrl(CRM_CLIENT_RATE_URL + uid);
        } catch (Exception exc) {
            throw new CRMConnectionException(exc.getMessage());
        }
    }

    private String fetchClientEvents(Date fromDate, Integer batchSize) throws CRMConnectionException {
        log.debug("Getting CRM client events fromDate: {} and batchSize: {} ...", fromDate, batchSize);

        String uri = CRM_CLIENT_EVENT_URL;
        if (fromDate != null) {
            uri += "fromDate=" + DATE_FORMAT.format(fromDate);
            uri += "&";
        }
        if (batchSize != null) {
            uri += "batchSize=" + batchSize;
        }

        try {
            return doGetOnUrl(uri);
        } catch (Exception exc) {
            throw new CRMConnectionException(exc.getMessage());
        }
    }

    private String doGetOnUrl(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet get = new HttpGet(url);
        get.addHeader("content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }
        StringBuffer buffer = new StringBuffer();
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();

    }

    private CRMData parseCRMDataResponse(String response) {
        JSONObject parsedResponse = new JSONObject(response);

        CRMData clientData = new CRMData();
        clientData.setUid(String.valueOf(parsedResponse.getLong("clientId")));
        clientData.setLtv(parsedResponse.getLong("ltv"));
        return clientData;
    }

    private List<CRMEvent> parseClientEventResponse(String response) {
        List<CRMEvent> CRMEvents = new ArrayList<CRMEvent>();

        if (response != null) {
            JSONArray parsedResponse = new JSONArray(response);

            for (Object object : parsedResponse) {
                JSONObject entry = (JSONObject) object;
                CRMEvent crmEvent = new CRMEvent();
                crmEvent.setId(entry.getLong("id"));
                crmEvent.setClientId(String.valueOf(entry.getJSONObject("clientRate").getLong("clientId")));
                crmEvent.setEventType(entry.getString("eventType"));
                crmEvent.setCreationTs(LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.getLong("creationTs")), ZoneId.systemDefault()));
                CRMEvents.add(crmEvent);
            }
        }

        return CRMEvents;
    }
}
