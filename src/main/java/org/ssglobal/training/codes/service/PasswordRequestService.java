package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.passwordRequestRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ssglobal.training.codes.cors.MyCorsFilter;
import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.PasswordRequest;
import org.ssglobal.training.codes.model.User;

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
	private static Logger logger = Logger.getLogger(MyCorsFilter.class.getName());

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
			e.getMessage();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
}
