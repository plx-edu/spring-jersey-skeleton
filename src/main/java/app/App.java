package app;

import java.nio.charset.Charset;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.buf.UDecoder;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.custom.CustomIdGenerator;

/**
 * App info etc.
 * export as Java - Runnable JAR file
 * 
 * Sidenote(s):
 * - Check Build Path
 * 
 *
 */
public class App {
	// REMINDER: update .getLogger(this.class)
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		try {
			logger.info("::: Starting Tomcat Server");
			CustomIdGenerator cig = new CustomIdGenerator();
			cig.generate(null, null);
			
			tomcatStart();
		} catch (Exception e) {
			logger.error("### Application failed to run", e);
			//e.printStackTrace();
		}
	}// main()
	
	private static void tomcatStart() throws Exception {
		String contextPath = "";
		String appBase = ".";
		//String webappDir = new File("WebContent").getAbsolutePath();
		
		// Creating and setting up Tomcat server
		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir("temp");
//        tomcat.setPort(8080);
        tomcat.setPort(5050);
        tomcat.getHost().setAppBase(appBase);
        // Define a web application context
        StandardContext context = (StandardContext) tomcat.addWebapp(contextPath, appBase);
        // Disables embedded tomcat's jar scanning and its warnings
        ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
        
        // Setting up servlet (Jersey ResourceConfig)
        ServletContainer servlet = new ServletContainer(new AppConfig());
        // Why "Tomcat" instead of "tomcat", both seem to work
        // yet "tomcat" gives a warning: "Change acces to static"
//        Tomcat.addServlet(context, servlet.getClass().getName(), servlet);
        Tomcat.addServlet(context, servlet.getClass().getName(), servlet).setAsyncSupported(true); // Async for Server Sent Events
        context.addServletMappingDecoded(UDecoder.URLDecode("/api/*", Charset.defaultCharset()), servlet.getClass().getName());
        
        // Start Tomcat process and wait for requests
//        tomcat.getConnector().setURIEncoding("UTF-8");
        tomcat.getConnector();
        tomcat.start();
        logger.info("::: Tomcat Server is Live");
        tomcat.getServer().await();
	}// tomcatStart()
	
}// - App





