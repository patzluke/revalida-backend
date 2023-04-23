package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.userTokenRepositoryImpl;

import org.ssglobal.training.codes.cors.Secured;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usertoken")
public class UserTokenService {
	
	@DELETE
	@Path("/delete/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response destroyUserToken(@PathParam("id") Integer id) {
		try {
			Boolean result = userTokenRepositoryImpl().deleteUserTokenImpl(id);
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
}
