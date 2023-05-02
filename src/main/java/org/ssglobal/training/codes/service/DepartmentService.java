package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.departmentRepoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	private static Logger logger = Logger.getLogger(DepartmentService.class.getName());

	@GET
	@Secured
	@Path("/get")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getAllPasswordRequestsJoinByUsers() {
		List<Department> dep = new ArrayList<>();
		GenericEntity<List<Department>> listDep = null;
		try {
			dep = departmentRepoImpl().selectAllDeparmentImpl();
			if (dep != null) {
				listDep = new GenericEntity<>(dep) {
				};
				return Response.ok(listDep).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			logger.severe("DepartmentService Line 45 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getPosById(@PathParam("id") Integer id) {
		try {
			Department dep = departmentRepoImpl().getDepByIdImpl(id);
			if (dep != null) {
				return Response.ok(dep).build();
			}
			return Response.status(Status.NOT_FOUND.getStatusCode(), "invalid employee ID").build();
		} catch (Exception e) {
			logger.severe("DepartmentService Line 62 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@DELETE
	@Secured
	@Path("/delete/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response deleteDepartment(@PathParam("id") Integer id) {
		try {
			Boolean result = departmentRepoImpl().deleteDepByIdImpl(id);
			if (result) {
				return Response.ok().build();
			}
			return Response.status(404, "invalid department ID").build();
		} catch (Exception e) {
			logger.severe("DepartmentService Line 80 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@PUT
	@Secured
	@Path("/update")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response updateDep(Department dep) {
		try {
			if (departmentRepoImpl().updateDepImpl(dep)) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.severe("DepartmentService Line 97 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@POST
	@Secured
	@Path("/insert")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response createDep(Department dep) {
		try {
			boolean result = departmentRepoImpl().insertDepImpl(dep.getDepartmentName());
			if (result) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.severe("DepartmentService Line 115 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
}
