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
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getAllpos() {
		List<Position> pos = new ArrayList<>();
		GenericEntity<List<Position>> listpos = null;
		try {
			pos = positionRepoImpl().selectAllPosInnerJoinDepartmentImpl();
			if (pos != null) {
				listpos = new GenericEntity<>(pos) {
				};
				return Response.ok(listpos).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getPosById(@PathParam("id") Integer id) {
		try {
			Position pos = positionRepoImpl().getPosByIdImpl(id);
			if (pos != null) {
				return Response.ok(pos).build();
			}
			return Response.status(Status.NOT_FOUND.getStatusCode(), "invalid employee ID").build();
		} catch (Exception e) {
			e.getMessage();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@GET
	@Secured
	@Path("/dept/get/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getPosByDepartmentId(@PathParam("id") Integer id) {
		List<Position> pos = new ArrayList<>();
		GenericEntity<List<Position>> listpos = null;
		try {
			pos = positionRepoImpl().getPosByDepartmentIdImpl(id);
			if (pos != null) {
				listpos = new GenericEntity<>(pos) {
				};
				return Response.ok(listpos).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@DELETE
	@Secured
	@Path("/delete/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response deletePosition(@PathParam("id") Integer id) {
		try {
			Boolean result = positionRepoImpl().deletePosByIdImpl(id);
			if (result) {
				return Response.ok().build();
			}
			return Response.status(404, "invalid department ID").build();
		} catch (Exception e) {
			e.getMessage();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@PUT
	@Secured
	@Path("/update")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response updatePosition(Position pos) {
		try {
			if (positionRepoImpl().updatePosImpl(pos)) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			e.getMessage();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@POST
	@Secured
	@Path("/insert")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response createPosition(Position pos) {
		try {
			boolean result = positionRepoImpl().insertPosImpl(pos.getDepartmentId(), 
															  pos.getPositionName());
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
