package sk.softec.ga.crm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.ga.crm.model.CRMClientRate;
import sk.softec.ga.crm.model.CRMEvent;
import sk.softec.ga.crm.model.dto.CRMEventContainer;
import sk.softec.ga.crm.service.CRMService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public Response getAllEvents(@QueryParam("fromDate") String fromDate, @QueryParam("batchSize") Integer batchSize) {
        Date from = null;

        if (fromDate != null) {
            try {
                from = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").parse(fromDate);

            } catch (ParseException e) {
            }
        }

        List<CRMEvent> events = crmService.getAllEvents(from, batchSize);
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
    public Response logEvent(@PathParam("clientId") Long clientId, CRMEventContainer eventContainer) {
        boolean result = crmService.logEvent(clientId, eventContainer);
        return Response.ok().entity(result).build();
    }

}
