package be.steformations.it.service.timesheets.rest.server;

import be.steformations.it.service.timesheets.rest.TimesheetRestService;

public class TimesheetRestServer {

	public static void main(String[] args) throws Exception {
		java.net.URI uri = new java.net.URI("http://localhost:8080/timesheets-rest");
		
		org.glassfish.jersey.server.ResourceConfig config
			= new org.glassfish.jersey.server.ResourceConfig();
		config.register(TimesheetRestService.class);
		
		org.glassfish.jersey.jdkhttp.JdkHttpServerFactory.createHttpServer(uri, config);
		
		System.out.println("Timesheet Rest service ready");
	}
	
}
