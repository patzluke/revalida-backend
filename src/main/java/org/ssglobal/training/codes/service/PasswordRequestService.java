package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.passwordRequestRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.PasswordRequest;

import jakarta.ws.rs.Consumes;
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

@Path("/passwordrequest")
public class PasswordRequestService {
	private static Logger logger = Logger.getLogger(PasswordRequestService.class.getName());

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
			logger.severe("PasswordRequestService Line 44 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@POST
	@Secured
	@Path("/insert")
	@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response createDep(PasswordRequest passwordRequest) {
		try {
			boolean result = passwordRequestRepositoryImpl().insertPasswordRequestImpl(passwordRequest.getEmpId());
			if (result) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.severe("PasswordRequestService Line 62 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@PUT
	@Secured
	@Path("/update")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response updateStatus(PasswordRequest pr) {
		try {
			if (passwordRequestRepositoryImpl().updateStatusImpl(pr)) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.severe("PasswordRequestService Line 79 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getPosById(@PathParam("id") Integer id) {
		try {
			PasswordRequest pr = passwordRequestRepositoryImpl().getRequestByIdImpl(id);
			if (pr != null) {
				return Response.ok(pr).build();
			}
			return Response.status(Status.NOT_FOUND.getStatusCode(), "invalid request ID").build();
		} catch (Exception e) {
			logger.severe("PasswordRequestService Line 96 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
}
