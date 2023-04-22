package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.positionRepoImpl;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.Position;

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

@Path("/positions")
public class PositionService {
		
	@GET
	@Secured
	@Path("/get")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getAllpos() {
		List<Position> pos = new ArrayList<>();
		GenericEntity<List<Position>> listpos = null;
		try {
			pos = positionRepoImpl().selectAllpositionImpl();
			listpos = new GenericEntity<>(pos) {};
			return Response.ok(listpos).build();
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
			Position pos = positionRepoImpl().getPosByIdImpl(id);
			return Response.ok(pos).build();
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
	public Response deletePosition(@PathParam("id") Integer id) {
		try {
			Boolean result = positionRepoImpl().deletePosByIdImpl(id);
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
	public Response updatePosition(Position pos) {
		try {
			positionRepoImpl().updatePosImpl(pos);
			return Response.ok(pos).build();
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
	public Response createPosition(Position pos) {
		try {
			positionRepoImpl().insertPosImpl(pos.getPositionName());
			return Response.ok(pos).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

}
