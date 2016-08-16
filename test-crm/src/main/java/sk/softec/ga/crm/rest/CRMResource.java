package sk.softec.ga.crm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.ga.crm.model.CRMClientRate;
import sk.softec.ga.crm.model.CRMEvent;
import sk.softec.ga.crm.model.CRMEventType;
import sk.softec.ga.crm.service.CRMService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Date;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Path("/api")
public class CRMResource extends SpringBeanAutowiringSupport {

    @Autowired
    CRMService crmService;

    @GET
    @Path("/event")
    @Produces("application/json")
    public Response getAllEvents(@QueryParam("fromDate") Date fromDate) {
        List<CRMEvent> events = crmService.getAllEvents(fromDate);
        return Response.ok().entity(events).build();
    }

    @GET
    @Path("/clientRate/{clientId}")
    @Produces("application/json")
    public Response getClientRate(@PathParam("clientId") Long clientId) {
        CRMClientRate clientRate = crmService.getClientRate(clientId);
        return Response.ok().entity(clientRate).build();
    }

    @POST
    @Path("/event/{clientId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response logEvent(@PathParam("clientId") Long clientId, CRMEventType eventType) {
        boolean result = crmService.logEvent(clientId, eventType);
        return Response.ok().entity(result).build();
    }

}
