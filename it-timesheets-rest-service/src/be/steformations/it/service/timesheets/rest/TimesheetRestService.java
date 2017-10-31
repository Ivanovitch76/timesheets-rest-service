package be.steformations.it.service.timesheets.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.PathParam;

import be.steformations.it.service.timesheets.dto.EmployeeDto;
import be.steformations.it.service.timesheets.dto.PrestationDto;
import be.steformations.it.service.timesheets.dto.ProjectDto;
import be.steformations.java_data.timesheets.entities.Employee;
import be.steformations.java_data.timesheets.entities.Prestation;
import be.steformations.java_data.timesheets.entities.Project;
import be.steformations.java_data.timesheets.service.TimesheetsDataService;
import be.steformations.pc.timesheets.dao.entities.EmployeeImpl;
import be.steformations.pc.timesheets.dao.service.spring_jdbc.TimesheetsSpringJdbcDataServiceImpl;

@javax.ws.rs.Path("rs")
public class TimesheetRestService {

	private TimesheetsDataService service;
//	private TimesheetsDtoFactory timesheetsDtoFactory;
	
	public TimesheetRestService() {
			super();
			System.out.println("TimesheetRestService.TimesheetRestService()");
			javax.sql.DataSource dataSource
				= new org.springframework.jdbc.datasource.SingleConnectionDataSource("jdbc:postgresql://localhost/timesheets", "postgres", "postgres", true);
			org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
			jdbcTemplate = new org.springframework.jdbc.core.JdbcTemplate(dataSource);
			this.service = new TimesheetsSpringJdbcDataServiceImpl(jdbcTemplate);
		}

	//http://localhost:8080/timesheets-rest/rs/employee/id
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("employee/{id}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response findOneEmployeeById(
			@javax.ws.rs.PathParam("id") int id){
		javax.ws.rs.core.Response response = null;
		
		Employee e = this.service.findOneEmployeeById(id);
		System.out.println("EmployeeRestService.findOneEmployeeById()" + e);
		if (e == null){
			response = javax.ws.rs.core.Response.status(404).build();
		} else {
			EmployeeDto employee = new EmployeeDto();
			employee.setId(e.getId());
			employee.setFirstname(e.getFirstname());
			employee.setName(e.getName());
			employee.setLogin(e.getLogin());
			employee.setPassword(e.getPassword());
			response = javax.ws.rs.core.Response.ok(employee).build();
		}
		
		return response;
	}
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("employee/{firstname}/{name}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response findOneEmployeeByFirstnameAndName(
			@javax.ws.rs.PathParam("firstname") String firstname,
			@javax.ws.rs.PathParam("name") String name
			){
		javax.ws.rs.core.Response response = null;
		Employee e = this.service.findOneEmployeeByFirstnameAndName(firstname, name);
		if (e == null){
			response = javax.ws.rs.core.Response.status(404).build();
		} else {
			EmployeeDto employee = new EmployeeDto();
			employee.setId(e.getId());
			employee.setFirstname(e.getFirstname());
			employee.setName(e.getName());
			employee.setLogin(e.getLogin());
			employee.setPassword(e.getPassword());
			response = javax.ws.rs.core.Response.ok(employee).build();
		}
		
		return response;
	}
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("employee")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response findAllEmployees(){
		javax.ws.rs.core.Response response = null;
		java.util.List<? extends Employee> list = this.service.findAllEmployees();
		java.util.List<EmployeeDto> dtos = new java.util.ArrayList<>();
		for (Employee e : list){
			EmployeeDto dto = new EmployeeDto();
			dto.setId(e.getId());
			dto.setFirstname(e.getFirstname());
			dto.setName(e.getName());
			dto.setLogin(e.getLogin());
			dto.setPassword(e.getPassword());
			dtos.add(dto);
		}
		
		javax.ws.rs.core.GenericEntity<java.util.List<EmployeeDto>> entity
			= new javax.ws.rs.core.GenericEntity<java.util.List<EmployeeDto>>(dtos){};
		response = javax.ws.rs.core.Response.ok(entity).build();	
		
		return response;
	}	
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("project/{id:[1-9]+}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response findOneProjectById(
			@javax.ws.rs.PathParam("id") int id){
		javax.ws.rs.core.Response response = null;
		
		Project p = this.service.findOneProjectById(id);
		if (p == null){
			response = javax.ws.rs.core.Response.status(404).build();
		} else {
			ProjectDto project = new ProjectDto();
			EmployeeDto manager = null;
			System.out.println("manager: " + p.getManager());
			if(p.getManager() != null){
				manager = new EmployeeDto();
				manager.setId(p.getManager().getId());
				manager.setFirstname(p.getManager().getFirstname());
				manager.setName(p.getManager().getName());
				manager.setLogin(p.getManager().getLogin());
				manager.setPassword(p.getManager().getPassword());
			}
			project.setId(p.getId());
			project.setName(p.getName());
			project.setDescription(p.getDescription());
			project.setStartDate(p.getStartDate());
			project.setEndDate(p.getEndDate());
			project.setManager(manager);
			response = javax.ws.rs.core.Response.ok(project).build();
		}
		
		return response;
	}
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("project/{name}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response findOneProjectByName(
			@javax.ws.rs.PathParam("name") String name){
		javax.ws.rs.core.Response response = null;
		
		Project p = this.service.findOneProjectByName(name);
		if (p == null){
			response = javax.ws.rs.core.Response.status(404).build();
		} else {
			ProjectDto project = new ProjectDto();
			EmployeeDto manager = null;
			System.out.println("manager: " + p.getManager());
			if(p.getManager() != null){
				manager = new EmployeeDto();
				manager.setId(p.getManager().getId());
				manager.setFirstname(p.getManager().getFirstname());
				manager.setName(p.getManager().getName());
				manager.setLogin(p.getManager().getLogin());
				manager.setPassword(p.getManager().getPassword());
			}
			
			project.setId(p.getId());
			project.setName(p.getName());
			project.setDescription(p.getDescription());
			project.setStartDate(p.getStartDate());
			project.setEndDate(p.getEndDate());
			project.setManager(manager);
			response = javax.ws.rs.core.Response.ok(project).build();
		}
		
		return response;
	}
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("project")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response findAllProjects(){
		javax.ws.rs.core.Response response = null;
		java.util.List<? extends Project> list = this.service.findAllProjects();
		java.util.List<ProjectDto> dtos = new java.util.ArrayList<>();
		for (Project p : list){
			ProjectDto dto = new ProjectDto();
			EmployeeDto manager = null;
			System.out.println("manager: " + p.getManager());
			if(p.getManager() != null){
				manager = new EmployeeDto();
				manager.setId(p.getManager().getId());
				manager.setFirstname(p.getManager().getFirstname());
				manager.setName(p.getManager().getName());
				manager.setLogin(p.getManager().getLogin());
				manager.setPassword(p.getManager().getPassword());
			}
			dto.setId(p.getId());
			dto.setName(p.getName());
			dto.setDescription(p.getDescription());
			dto.setStartDate(p.getStartDate());
			dto.setEndDate(p.getEndDate());
			dto.setManager(manager);
			dtos.add(dto);
		}
		
		javax.ws.rs.core.GenericEntity<java.util.List<ProjectDto>> entity
			= new javax.ws.rs.core.GenericEntity<java.util.List<ProjectDto>>(dtos){};
		response = javax.ws.rs.core.Response.ok(entity).build();	
		
		return response;
	}
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("prestation/{id:[1-9]+}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response findOnePrestationById(
			@javax.ws.rs.PathParam("id") int id){
		javax.ws.rs.core.Response response = null;
		
		Prestation p = this.service.findOnePrestationById(id);
		if (p == null){
			response = javax.ws.rs.core.Response.status(404).build();
		} else {
			PrestationDto prestation = this.createPrestation(p);
			response = javax.ws.rs.core.Response.ok(prestation).build();
		}
		
		return response;
	}

	@javax.ws.rs.GET
	@javax.ws.rs.Path("prestation/employee/{id:[1-9]+}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response findAllPrestationsByEmployeeId(
			@javax.ws.rs.PathParam("id") int id){
		javax.ws.rs.core.Response response = null;
		
		java.util.List<? extends Prestation> list = this.service.findAllPrestationsByEmployeeId(id);
		java.util.List<PrestationDto> dtos = new java.util.ArrayList<>();
		for (Prestation prestation : list){
			dtos.add(this.createPrestation(prestation));
		}
		javax.ws.rs.core.GenericEntity<java.util.List<PrestationDto>> entity
			= new javax.ws.rs.core.GenericEntity<java.util.List<PrestationDto>>(dtos){};
		response = javax.ws.rs.core.Response.ok(entity).build();
			
		return response;
	}	
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("prestation/project/{id:[1-9]+}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response findAllPrestationsByProjectId(
			@javax.ws.rs.PathParam("id") int id){
		javax.ws.rs.core.Response response = null;
		
		java.util.List<? extends Prestation> list = this.service.findAllPrestationsByProjectId(id);
		java.util.List<PrestationDto> dtos = new java.util.ArrayList<>();
		for (Prestation prestation : list){
			dtos.add(this.createPrestation(prestation));
		}
		javax.ws.rs.core.GenericEntity<java.util.List<PrestationDto>> entity
			= new javax.ws.rs.core.GenericEntity<java.util.List<PrestationDto>>(dtos){};
		response = javax.ws.rs.core.Response.ok(entity).build();
			
		return response;
	}	
	
	
//Dans le navigateur via RestClient
//Method = Post		URL = http://localhost:8080/timesheets-rest/rs
//Headers Content-Type 	application/x-www-form-urlencoded
//Body : day=2017-10-30&employeeId=1&projectId=2&duration=4&comment=Farniente	
	@javax.ws.rs.POST
	@javax.ws.rs.Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response addPrestation(
			@javax.ws.rs.FormParam("day") String day,
			@javax.ws.rs.FormParam("employee") long employeeId,
			@javax.ws.rs.FormParam("project") long projectId,
			@javax.ws.rs.FormParam("duration") int duration,
			@javax.ws.rs.FormParam("comment") String comment){		
		System.out.println("TimesheetRestService.addPrestation()");
		javax.ws.rs.core.Response response = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		try {
			date = format.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Prestation prestation = this.service.addPrestation(employeeId, projectId, comment, date, duration);
		if (prestation != null){
			PrestationDto dto = this.createPrestation(prestation);
			response = javax.ws.rs.core.Response.ok(dto).build();
		} else {
			response = javax.ws.rs.core.Response.status(204).build();
		}
		
		return response;		
	}
	
// Body style : {"id":1,"day":"2017-06-22T00:00:00+02:00","employee":{"firstname":"bruce","id":1,"login":"batman","name":"wayne","password":"I am Batman"},"project":{"id":1,"name":"Justice League","description":"Superheros League","startDate":"2017-06-21T00:00:00+02:00","manager":{"firstname":"bruce","id":1,"login":"batman","name":"wayne","password":"I am Batman"}},"duration":2,"comment":"Send email"}	
	@javax.ws.rs.POST
	@javax.ws.rs.Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response addPrestationJson(PrestationDto input){		
		System.out.println("TimesheetRestService.addPrestationJson()");
		javax.ws.rs.core.Response response = null;
		Prestation prestation = this.service.addPrestation(input.getEmployee().getId(), input.getProject().getId(),input.getComment(), input.getDay(), input.getDuration());
		if (prestation != null){
			PrestationDto dto = this.createPrestation(prestation);
			response = javax.ws.rs.core.Response.ok(dto).build();
		} else {
			response = javax.ws.rs.core.Response.status(204).build();
		}
		
		return response;		
	}
	
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path("prestation/{id:[1-9]+}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response deletePrestation(
			@javax.ws.rs.PathParam("id") long id){
		javax.ws.rs.core.Response response = null;
		Prestation p = this.service.findOnePrestationById(id);
		if (p != null){
			PrestationDto dto = this.createPrestation(p);
			this.service.deletePrestation(id);
			response = javax.ws.rs.core.Response.ok(dto).build();
			
		} else {
			response = javax.ws.rs.core.Response.status(404).build();
		}
		return response;
	}
	
	
	private PrestationDto createPrestation(Prestation p) {
		PrestationDto prestation = new PrestationDto();
		ProjectDto project = null;
		EmployeeDto employee = null;
		if (p.getEmployee() != null){
			employee = new EmployeeDto();
			employee.setId(p.getEmployee().getId());
			employee.setFirstname(p.getEmployee().getFirstname());
			employee.setName(p.getEmployee().getName());
			employee.setLogin(p.getEmployee().getLogin());
			employee.setPassword(p.getEmployee().getPassword());
		}
		if (p.getProject() != null){
			project = new ProjectDto();
			project.setId(p.getProject().getId());
			project.setName(p.getProject().getName());
			project.setDescription(p.getProject().getDescription());
			project.setStartDate(p.getProject().getStartDate());
			project.setEndDate(p.getProject().getEndDate());
			project.setManager(employee);
		}
		
		prestation.setId(p.getId());
		prestation.setDay(p.getDay());
		prestation.setEmployee(employee);
		prestation.setProject(project);
		prestation.setDuration(p.getDuration());
		prestation.setComment(p.getComment());
		
		return prestation;
	}
}
