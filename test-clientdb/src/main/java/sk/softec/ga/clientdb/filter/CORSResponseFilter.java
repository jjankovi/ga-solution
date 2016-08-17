package sk.softec.ga.clientdb.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jankovj on 2. 6. 2015.
 */
public class CORSResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
    	String[] allowDomain = {"http://db2:8080", "http://alf:8080", "http://hadibar:8080"};
	    Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(allowDomain));

	    MultivaluedMap<String, Object> headers = containerResponseContext.getHeaders();
	    
	    Object originHeader = containerRequestContext.getHeaderString("Origin");

//	    if(allowedOrigins.contains(originHeader)) {
	    	headers.add("Access-Control-Allow-Origin", originHeader);
	    	headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
	        headers.add("Access-Control-Allow-Credentials", "true");
	        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
	        headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token");
//	    }
    }
}
