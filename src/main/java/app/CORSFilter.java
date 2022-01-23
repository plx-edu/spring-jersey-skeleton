package app;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	// CHECK: https://stackoverflow.com/questions/28065963/how-to-handle-cors-using-jax-rs-with-jersey/28067653#28067653
	// CHECK: https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
	
	@Override
	public void filter(ContainerRequestContext req) throws IOException {
		// *** ContainerRequestFilter ***
		logger.info("::::: CORSFilter Start (Request) "+req.getMethod());
		logger.info(":: "+req.getUriInfo().getAbsolutePath());
		
		// If it's a preflight request, we abort the request with
        // a 200 status, and the CORS headers are added in the
        // response filter method below.
		if (isPreflightRequest(req)) {
            req.abortWith(Response.ok().build());
            return;
        }
		
		logger.info("::::: CORSFilter End (Request)");
	}// filter(req)
	
     // A preflight request is an OPTIONS request with an Origin header.
    private static boolean isPreflightRequest(ContainerRequestContext req) {
        return req.getHeaderString("Origin") != null
                && req.getMethod().equalsIgnoreCase("OPTIONS");
    }
	
	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext resp)
			throws IOException {
		// *** ContainerResponseFilter ***
		logger.info("::::: CORSFilter Start (Response) "+req.getMethod());
		
		// if there is no Origin header, then it is not a
        // cross origin request. We don't do anything.
//		System.out.println(" 1 ");
//        if (req.getHeaderString("Origin") == null) {
//        	logger.info(":: Origin header Null");
//        	logger.info("::::: CORSFilter End (Response)\n");
//            return;
//        }

        // If it is a preflight request, then we add all
        // the CORS headers here.
//        System.out.println(" 2 ");
        if (isPreflightRequest(req)) {
            resp.getHeaders().add("Access-Control-Allow-Credentials", "true");
            resp.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            resp.getHeaders().add("Access-Control-Allow-Headers",
                // Whatever other non-standard/safe headers (see list above) 
                // you want the client to be able to send to the server,
                // put it in this list. And remove the ones you don't want.
                "Origin, Accept, X-Requested-With, Content-Type, "
                + "Access-Control-Request-Method, Access-Control-Request-Headers");
            
            // How long information(in Allow-Methods/-Headers) can be cached
            // Number is in seconds (600s = 10min)
            resp.getHeaders().add("Access-Control-Max-Age", "600");
        }

        // Cross origin requests can be either simple requests
        // or preflight request. We need to add this header
        // to both type of requests. Only preflight requests
        // need the previously added headers.
//        System.out.println(" 3 ");
//        resp.getHeaders().add("Access-Control-Allow-Origin", "*"); // Authorize/Allow domain to consume the content 
        resp.getHeaders().add("Access-Control-Allow-Origin", "http://localhost"); // Authorize/Allow domain to consume the content 
		
		logger.info(":: "+resp.getHeaders());
		logger.info("::::: CORSFilter End (Response)\n");
	}// filter(resp)
	
}// - CORSFilter
