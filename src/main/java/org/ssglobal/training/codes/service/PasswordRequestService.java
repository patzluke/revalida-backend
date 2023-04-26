package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.passwordRequestRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.PasswordRequest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/passwordrequest")
public class PasswordRequestService {

	@GET
	@Secured
	@Path("/get")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getAllDep() {
		List<PasswordRequest> dep = new ArrayList<>();
		GenericEntity<List<PasswordRequest>> listDep = null;
		try {
			dep = passwordRequestRepositoryImpl().getAllPasswordRequestsJoinByUsersImpl();
			if (dep != null) {
				listDep = new GenericEntity<>(dep) {
				};
				return Response.ok(listDep).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@POST
	@Secured
	@Path("/insert")
	@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createDep(Integer employeeId) {
		try {
			boolean result = passwordRequestRepositoryImpl().insertPasswordRequestImpl(employeeId);
			if (result) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			e.getMessage();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
}
