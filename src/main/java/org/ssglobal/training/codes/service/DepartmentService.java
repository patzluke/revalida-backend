package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.departmentRepoImpl;


import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.Department;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/departments")
public class DepartmentService {
	
	@GET
	@Secured
	@Path("/get")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getAllDep() {
		List<Department> dep = new ArrayList<>();
		GenericEntity<List<Department>> listDep = null;
		try {
			dep = departmentRepoImpl().selectAllDeparmentImpl();
			listDep = new GenericEntity<>(dep) {};
			return Response.ok(listDep).build();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getPosById(@PathParam("id") Integer id) {
		try {
			Department dep = departmentRepoImpl().getDepByIdImpl(id);
			return Response.ok(dep).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.noContent().build();
	}
	
	@DELETE
	@Secured
	@Path("/delete/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response deleteDepartment(@PathParam("id") Integer id) {
		try {
			Boolean result = departmentRepoImpl().deleteDepByIdImpl(id);
			if(result) {
				return Response.ok().build();
			} else {
				return Response.status(404, "invalid department ID").build();
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.serverError().build();
	}
	
	@PUT
	@Secured
	@Path("/update")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response updateDep(Department dep) {
		try {
			departmentRepoImpl().updateDepImpl(dep);
			return Response.ok(dep).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@POST
	@Secured
	@Path("/insert")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response createDep(Department dep) {
		try {
			departmentRepoImpl().insertDepImpl(dep.getDepartmentName());
			return Response.ok(dep).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
