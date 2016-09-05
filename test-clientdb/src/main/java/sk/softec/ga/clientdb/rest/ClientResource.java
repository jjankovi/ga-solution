package sk.softec.ga.clientdb.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.ga.clientdb.model.Client;
import sk.softec.ga.clientdb.service.ClientService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Path("/api")
public class ClientResource extends SpringBeanAutowiringSupport {

    @Autowired
    ClientService clientService;

    @GET
    @Produces("application/json")
    public Response listAllClients(@QueryParam("login") String login, @QueryParam("name") String name) {
        return Response.ok().entity(clientService.getAllClients(login, name)).build();
    }

    @GET
    @Path("/login")
    @Produces("application/json")
    public Response doLogin(@QueryParam("login") String login, @QueryParam("password") String password) {
        Client foundClient = clientService.login(login, password);
        return Response.ok().entity(foundClient).build();
    }

}
