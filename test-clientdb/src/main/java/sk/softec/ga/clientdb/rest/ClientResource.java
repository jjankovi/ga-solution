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
        if (StringUtils.isEmpty(login) && StringUtils.isEmpty(name)) {
            return Response.ok().entity(clientService.getAllClients()).build();
        }
        if (!StringUtils.isEmpty(login)) {
            return Response.ok().entity(clientService.searchByLogin(login)).build();
        } else {
            return Response.ok().entity(clientService.searchByName(name)).build();
        }
    }

    @GET
    @Path("/login")
    @Produces("application/json")
    public Response doLogin(@QueryParam("login") String login, @QueryParam("password") String password) {
        Client foundClient = clientService.login(login, password);
        return Response.ok().entity(foundClient).build();
    }

}
