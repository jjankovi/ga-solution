package sk.softec.ga.module.connector.crm;

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
import sk.softec.ga.module.connector.api.CRMConnector;
import sk.softec.ga.module.connector.exception.CRMConnectionException;
import sk.softec.ga.module.connector.model.ClientData;
import sk.softec.ga.module.connector.model.CRMEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
@Primary
public class TaBaCRMConnector implements CRMConnector {

    private static final Logger log = LoggerFactory.getLogger(TaBaCRMConnector.class);

    private static final String CRM_CLIENT_RATE_URL = "http://localhost:8080/crm/api/clientRate/";
    private static final String CRM_CLIENT_EVENT_URL = "http://localhost:8080/crm/api/event?";

    @Override
    public ClientData getClientData(String clientId) throws CRMConnectionException {
        String response = fetchClientData(clientId);
        return parseClientDataResponse(response);
    }

    @Override
    public List<CRMEvent> getClientEvents(LocalDateTime fromDate, Integer batchSize) throws CRMConnectionException {
        log.debug("Getting CRM client events fromDate: {} and batchSize: {} ...", fromDate, batchSize);
        String response = fetchClientEvents(fromDate, batchSize);
        return parseClientEventResponse(response);
    }

    private String fetchClientData(String clientId) throws CRMConnectionException {
        try {
            return doGetOnUrl(CRM_CLIENT_RATE_URL + clientId);
        } catch (Exception exc) {
            throw new CRMConnectionException();
        }
    }

    private String fetchClientEvents(LocalDateTime fromDate, Integer batchSize) throws CRMConnectionException {
        String uri = CRM_CLIENT_EVENT_URL;
        if (fromDate != null) {
            uri += "fromDate=" + fromDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss"));
            uri += "&";
        }
        if (batchSize != null) {
            uri += "batchSize=" + batchSize;
        }

        try {
            return doGetOnUrl(uri);
        } catch (Exception exc) {
            throw new CRMConnectionException();
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

    private ClientData parseClientDataResponse(String response) {
        JSONObject parsedResponse = new JSONObject(response);

        ClientData clientData = new ClientData();
        clientData.setClientId(String.valueOf(parsedResponse.getLong("clientId")));
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
