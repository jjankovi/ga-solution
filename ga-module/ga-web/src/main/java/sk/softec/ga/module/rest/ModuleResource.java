package sk.softec.ga.module.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.ga.module.connector.model.CidData;
import sk.softec.ga.module.connector.model.GAEvent;
import sk.softec.ga.module.services.ModuleService;
import sk.softec.ga.module.services.event.GAEventSendException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Path("/api")
public class ModuleResource extends SpringBeanAutowiringSupport {

    @Autowired
    ModuleService moduleService;

    @GET
    @Path("/cid/{input}")
    @Produces("application/json")
    public Response generateCID(@PathParam("input") String input ) {
        String cid = moduleService.generateCID(input);
        return Response.ok().entity(cid).build();
    }

    @GET
    @Path("/cid-data/{input}")
    @Produces("application/json")
    public Response generateFullCID(@PathParam("input") String input ) {
        CidData cidData = moduleService.generateCIDData(input);
        return Response.ok().entity(cidData).build();
    }

    @POST
    @Path("/ga-event")
    @Consumes("application/json")
    @Produces("application/json")
    public Response sendGaEvent(GAEvent gaEvent) {
        try {
            moduleService.sendGAEvent(gaEvent);
            return Response.ok().build();
        } catch (GAEventSendException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
