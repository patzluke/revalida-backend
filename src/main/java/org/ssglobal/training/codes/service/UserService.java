package org.ssglobal.training.codes.service;

import static org.ssglobal.training.codes.RepositoryImplConn.generateToken;
import static org.ssglobal.training.codes.RepositoryImplConn.userRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.User;
import org.ssglobal.training.codes.model.UserLogin;
import org.ssglobal.training.codes.model.UserPasswordChange;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/users")
public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class.getName());

	@GET
	@Secured
	@Path("/get")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getAllUsersInnerJoinOnPositionAndDepartment() {
		List<User> users = new ArrayList<>();
		GenericEntity<List<User>> listUsers = null;
		try {
			users = userRepositoryImpl().selectUsersInnerJoinDepartmentAndPositionImpl();
			if (users != null) {
				listUsers = new GenericEntity<>(users) {
				};
				return Response.ok(listUsers).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			logger.severe("UserService Line 49 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@GET
	@Secured
	@Path("/get/query")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response searchUsersInnerJoinDepartmentAndPositionUsingLike(@QueryParam(value = "search") String search) {
		List<User> users = new ArrayList<>();
		GenericEntity<List<User>> listUsers = null;
		try {
			users = userRepositoryImpl().searchUsersInnerJoinDepartmentAndPositionUsingLikeImpl(search);
			if (users != null) {
				listUsers = new GenericEntity<>(users) {
				};
				return Response.ok(listUsers).build();
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			logger.severe("UserService Line 70 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getUserById(@PathParam("id") Integer id) {
		try {
			User user = userRepositoryImpl().getUserByIdImpl(id);
			if (user != null) {
				return Response.ok(user).build();
			}
			return Response.status(Status.NOT_FOUND.getStatusCode(), "invalid employee ID").build();
		} catch (Exception e) {
			logger.severe("UserService Line 87 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@POST
	@Secured
	@Path("/get/password")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response searchPasswordandId(Integer empId) {
		try {
			User user = userRepositoryImpl().searchPasswordByIdImpl(empId);
			if (user != null) {
				return Response.ok(user).build();
			}
			return Response.status(Status.NOT_FOUND).build();
		} catch (Exception e) {
			logger.severe("UserService Line 105 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@POST
	@Secured
	@Path("/authenticate")
	@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response authenticate(UserLogin payload) {
		List<Object> userDetail = new ArrayList<>();
		try {
			User user = userRepositoryImpl().searchUserByEmailAndPassImpl(payload.getUsername(), payload.getPassword());
			if (user != null) {
				String token = generateToken(user.getEmployeeId(), user.getEmail(), user.getUserType());
				userDetail.add(token);
				return Response.ok(new GenericEntity<>(userDetail) {
				}).build();
			}
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (Exception e) {
			logger.severe("UserService Line 127 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@DELETE
	@Secured
	@Path("/delete/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response deleteUser(@PathParam("id") Integer id) {
		try {
			Boolean result = userRepositoryImpl().deleteUserByIdImpl(id);
			if (result) {
				return Response.ok().build();
			}
			return Response.status(Status.NOT_FOUND.getStatusCode(), "invalid employee ID").build();
		} catch (Exception e) {
			logger.severe("UserService Line 145 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@PUT
	@Secured
	@Path("/update")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response updateUser(User user) {
		try {
			if (userRepositoryImpl().updateUserImpl(user)) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.severe("UserService Line 162 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@POST
	@Secured
	@Path("/update/password")
	@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED })
	public Response changeUserPassword(UserPasswordChange user) {
		try {
			if (userRepositoryImpl().changePasswordImpl(user)) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.severe("UserService Line 179 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@POST
	@Secured
	@Path("/insert")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response createUser(User user) {
		try {
			boolean result = userRepositoryImpl().insertUser(user.getEmail(), user.getMobileNumber(),
					user.getPassword(), user.getUserType(), user.getFirstName(), user.getMiddleName(),
					user.getLastName(), user.getDepartmentId(), user.getBirthDate(), user.getGender(),
					user.getPositionId());
			if (result) {
				return Response.ok().build();
			}
			return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.severe("UserService Line 200 exception: %s".formatted(e.getMessage()));
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
}
