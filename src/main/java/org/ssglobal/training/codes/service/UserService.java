package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.generateToken;
import static org.ssglobal.training.codes.RepositoryImplConn.userRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ssglobal.training.codes.cors.MyCorsFilter;
import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.User;
import org.ssglobal.training.codes.model.UserLogin;

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

@Path("/users")
public class UserService {
	private static Logger logger = Logger.getLogger(MyCorsFilter.class.getName());
	
	@GET
	@Secured
	@Path("/get")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getAllUsersInnerJoinOnPositionAndDepartment() {
		List<User> users = new ArrayList<>();
		GenericEntity<List<User>> listUsers = null;
		try {
			users = userRepositoryImpl().selectUsersInnerJoinDepartmentAndPositionImpl();
			listUsers = new GenericEntity<>(users) {};
			return Response.ok(listUsers).build();
		} catch(Exception e) {
			logger.severe("IT WENT HERE IN THE EXCEPTION");
			e.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getUserById(@PathParam("id") Integer id) {
		try {
			User user = userRepositoryImpl().getUserByIdImpl(id);
			return Response.ok(user).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.noContent().build();
	}

	@POST
	@Secured
	@Path("/authenticate")
	@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response authenticate(UserLogin payload) {
		List<Object> userDetail = new ArrayList<>();
		try {
			User user = userRepositoryImpl().searchUserByEmailAndPassImpl(payload.getUsername(), payload.getPassword());
			String token = generateToken(user.getEmployeeId(), user.getEmail());
			if (user != null) {
				userDetail.add(token);
				userDetail.add(user.getEmployeeId());
				userDetail.add(user.getEmail());
				userDetail.add(user.getUserType());
				return Response.ok( new GenericEntity<>(userDetail) {}).build();
			}
		} catch(Exception e) {
			e.getMessage();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@DELETE
	@Secured
	@Path("/delete/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response deleteUser(@PathParam("id") Integer id) {
		try {
			Boolean result = userRepositoryImpl().deleteUserByIdImpl(id);
			if(result) {
				return Response.ok().build();
			} else {
				return Response.status(404, "invalid employee ID").build();
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
	public Response updateUser(User user) {
		try {
			userRepositoryImpl().updateUserImpl(user);
			return Response.ok(user).build();
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
	public Response createUser(User user) {
		try {
			userRepositoryImpl().insertUser(user.getEmail(), user.getMobileNumber(),
									  user.getPassword(), user.getUserType(), user.getFirstName(),
									  user.getMiddleName(), user.getLastName(), user.getDepartmentId(),
									  user.getBirthDate(), user.getGender(), user.getPositionId());
			return Response.ok(user).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
