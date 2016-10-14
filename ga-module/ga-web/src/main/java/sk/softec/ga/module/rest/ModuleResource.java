package sk.softec.ga.module.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.ga.module.api.model.Subject;
import sk.softec.ga.module.services.ModuleService;
import sk.softec.ga.module.services.event.GAEventSendException;
import sk.softec.ga.module.services.model.GAEvent;

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
        Subject subject = moduleService.getSubjectWithGeneratedCID(input);
        return Response.ok().entity(subject).build();
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
