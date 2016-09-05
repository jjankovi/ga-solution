package sk.softec.ga.module.connector.clientdb;

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
import sk.softec.ga.module.connector.api.ClientDBConnector;
import sk.softec.ga.module.connector.model.clientdb.ClientIdentity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
@Primary
public class TaBaClientDBConnector implements ClientDBConnector {

    private static final Logger log = LoggerFactory.getLogger(TaBaClientDBConnector.class);

    private static final String CLIENT_DB_URL = "http://localhost:8080/clientdb/api";

    @Override
    public ClientIdentity getClientIdentity(String login) {
        String response = fetchClientIdentity(login);
        return parseResponse(response);
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

    private ClientIdentity parseResponse(String response) {
        JSONArray parsedResponse = new JSONArray(response);

        if (parsedResponse.length() != 1) {
            return null;
        }

        JSONObject client = (JSONObject) parsedResponse.get(0);
        ClientIdentity identity = new ClientIdentity();
        identity.setId(String.valueOf(client.getLong("id")));
        identity.setLogin(client.getString("login"));
        identity.setFirstName(client.getString("firstName"));
        identity.setLastName(client.getString("lastName"));

        return identity;
    }
}
