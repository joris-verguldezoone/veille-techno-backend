package org.acme.auth;

import org.acme.auth.dto.RegisterRequest;
import org.acme.auth.dto.LoginRequest;
import org.acme.auth.entity.User;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse.Status;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @Inject
    JsonWebToken jwt; 

    private static final Logger LOG = Logger.getLogger(AuthResource.class);


    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest request) { 
        User user = authService.register(request);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        try {
            String token = authService.login(request);
            return Response.ok(Map.of("accessToken", token)).build();
            
        } catch (Exception e) {
            LOG.info("plop");
            LOG.info(e.getMessage());
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}