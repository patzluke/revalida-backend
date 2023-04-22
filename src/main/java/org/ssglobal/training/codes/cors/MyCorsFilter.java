package org.ssglobal.training.codes.cors;

import static org.ssglobal.training.codes.RepositoryImplConn.userTokenRepositoryImpl;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class MyCorsFilter implements ContainerResponseFilter, ContainerRequestFilter {
	private static Logger logger = Logger.getLogger(MyCorsFilter.class.getName());
	
	@Secured
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			UriInfo uriInfo = requestContext.getUriInfo();
			String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
			if (!uriInfo.getPath().endsWith("users/authenticate")) {
				if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				}
				String token = authorizationHeader.substring("Bearer".length()).trim();
				
				if (!validateToken(token)) {
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8000");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	}

	@SuppressWarnings("unchecked")
	private boolean validateToken(String token) {
		try {
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();			
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
			
			Date tokenExpiresAt = new Date(Long.parseLong(result.get("exp").toString()) * 1000L);
			int userId = Integer.parseInt(result.get("userId").toString());
			
			if (new Date().getTime() < tokenExpiresAt.getTime() && userTokenRepositoryImpl().isUserTokenExistsImpl(token)) {
				return true;
			} else if (new Date().getTime() < tokenExpiresAt.getTime()){
				userTokenRepositoryImpl().deleteUserTokenImpl(userId);
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
