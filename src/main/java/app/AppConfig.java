package app;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//Since we're using an embedded Tomcat
//it seems the @ApplicationPath("")
//won't be needed. See "addServletMappingDecoded"
public class AppConfig extends ResourceConfig {
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	// Rest using Jersey
	public AppConfig() {
		logger.info(":: AppConfig has been called");
		
		// packages: tells Jersey to scan for our resources (classes annotated with @Path)
		packages(this.getClass().getPackage().getName());
		// RequestContextfilter: provide a bridge between Jersey and the Spring request attributes
		register(RequestContextFilter.class);
		// property: instead of the server sending a server error page on error statuses, it just sends the status code
		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
	}
}// - AppConfig
