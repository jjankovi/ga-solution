package sk.softec.ga.module.connector.clientdb;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import sk.softec.ga.module.connector.api.ClientDBConnector;
import sk.softec.ga.module.connector.model.ClientIdentity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Service
@Primary
public class TaBaClientDBConnector implements ClientDBConnector {

    private static final String CLIENT_DB_URL = "http://localhost:8080/clientdb/api";

    @Override
    public ClientIdentity getClientIdentity(String login) {
        String response = fetchClientIdentity(login);
        return parseResponse(response);
    }

    private String fetchClientIdentity(String login) {
        try {
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
        JSONObject parsedResponse = new JSONObject(response);

        ClientIdentity identity = new ClientIdentity();
        identity.setId(String.valueOf(parsedResponse.getLong("id")));
        identity.setLogin(parsedResponse.getString("login"));
        identity.setName(parsedResponse.getString("name"));
        return identity;
    }
}