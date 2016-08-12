package sk.softec.go.crm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.go.crm.model.CRMClientRate;
import sk.softec.go.crm.model.CRMEvent;
import sk.softec.go.crm.model.CRMEventType;
import sk.softec.go.crm.service.CRMService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Path("/")
public class CRMResource extends SpringBeanAutowiringSupport {

    @Autowired
    CRMService crmService;

    @GET
    @Path("/all")
    @Produces("application/json")
    public Response getAllEvents() {
        List<CRMEvent> events = crmService.getAllEvents();
        return Response.ok().entity(events).build();
    }

    @GET
    @Path("/{clientId}/clientRate")
    @Produces("application/json")
    public Response getClientRate(@PathParam("clientId") Long clientId) {
        CRMClientRate clientRate = crmService.getClientRate(clientId);
        return Response.ok().entity(clientRate).build();
    }

    @POST
    @Path("/{clientId}/event")
    @Consumes("application/json")
    @Produces("application/json")
    public Response logEvent(@PathParam("clientId") Long clientId, CRMEventType eventType) {
        boolean result = crmService.logEvent(clientId, eventType);
        return Response.ok().entity(result).build();
    }

}
