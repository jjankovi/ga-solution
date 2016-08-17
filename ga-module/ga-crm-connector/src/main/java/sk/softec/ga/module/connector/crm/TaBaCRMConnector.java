package sk.softec.ga.module.connector.crm;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.api.CRMConnector;
import sk.softec.ga.module.connector.model.ClientData;
import sk.softec.ga.module.connector.model.CRMEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
@Primary
public class TaBaCRMConnector implements CRMConnector {

    private static final String CRM_CLIENT_RATE_URL = "http://localhost:8080/crm/api/clientRate/";
    private static final String CRM_CLIENT_EVENT_URL = "http://localhost:8080/crm/api/event?fromDate=/";

    @Override
    public ClientData getClientData(String clientId) {
        String response = fetchClientData(clientId);
        return parseClientDataResponse(response);
    }

    @Override
    public List<CRMEvent> getClientEvents(Date fromDate) {
        String response = fetchClientEvents(fromDate);
        return parseClientEventResponse(response);
    }

    private String fetchClientData(String clientId) {
        return doGetOnUrl(CRM_CLIENT_RATE_URL + clientId);
    }

    private String fetchClientEvents(Date fromDate) {
        return doGetOnUrl(CRM_CLIENT_EVENT_URL + fromDate);
    }

    private String doGetOnUrl(String url) {
        try {
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
        } catch (Exception exc) {
        }

        return null;
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

        JSONArray parsedResponse = new JSONArray(response);

        for (Object object : parsedResponse) {
            JSONObject entry = (JSONObject) object;
            CRMEvent CRMEvent = new CRMEvent();
            CRMEvent.setId(entry.getLong("id"));
            // TODO este clientId
            CRMEvent.setEventType(entry.getString("eventType"));
            CRMEvents.add(CRMEvent);
        }
        return CRMEvents;
    }
}
