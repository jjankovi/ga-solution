package sk.softec.ga.module.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sk.softec.ga.module.connector.model.CidData;
import sk.softec.ga.module.services.ModuleService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    @Path("/ciddata/{input}")
    @Produces("application/json")
    public Response generateFullCID(@PathParam("input") String input ) {
        CidData cidData = moduleService.generateCIDData(input);
        return Response.ok().entity(cidData).build();
    }

}
