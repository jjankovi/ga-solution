package sk.softec.go.clientdb.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.go.clientdb.model.Client;
import sk.softec.go.clientdb.service.ClientService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Path("/")
public class ClientResource extends SpringBeanAutowiringSupport {

    @Autowired
    ClientService clientService;

    @GET
    @Path("/all")
    @Produces("application/json")
    public Response listAllClients() {
        List<Client> clients = clientService.getAllClients();
        return Response.ok().entity(clients).build();
    }

    @GET
    @Path("/login")
    @Produces("application/json")
    public Response login(@QueryParam("login") String login, @QueryParam("password") String password) {
        Client foundClient = clientService.login(login, password);
        return Response.ok().entity(foundClient).build();
    }

    @GET
    @Path("/name")
    @Produces("application/json")
    public Response findByName(@QueryParam("name") String name) {
        Client foundClient = clientService.searchByName(name);
        return Response.ok().entity(foundClient).build();
    }

}
