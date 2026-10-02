package org.acme.boardColumn;

import java.util.List;

import org.acme.boardColumn.dto.BoardColumnNamePatch;
import org.acme.boardColumn.dto.BoardColumnRequest;
import org.acme.boardColumn.entity.BoardColumn;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/board-columns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated 
public class BoardColumnResource {
    
    @Inject
    JsonWebToken jwt;
    
    @Inject
    BoardColumnService boardColumnService;

    private Long getUserIdFromToken() { // A réutiliser partout
        Long userId = Long.parseLong(jwt.getSubject());
        if (userId == null) {
            throw new NotAuthorizedException("Token invalide ou userId manquant");
        }
        return userId.longValue();
    }
    
    @GET
    @Path("/board/{boardId}")
    public Response getBoardColumns(@PathParam("boardId") Long boardId) {
        List<BoardColumn> columns = boardColumnService.getColumnsByBoard(boardId, getUserIdFromToken());
        return Response.ok(columns).build();
    }

    @POST
    public Response createColumn(@Valid BoardColumnRequest request) {
        BoardColumn column = boardColumnService.createColumn(request, getUserIdFromToken());
        return Response.status(Response.Status.CREATED).entity(column).build();
    }

    @DELETE
    @Path("/{columnId}") 
    public Response deleteColumn(@PathParam("columnId") Long columnId) {
        Long userId = Long.parseLong(jwt.getSubject());
        
        boardColumnService.deleteColumn(columnId, userId);
        
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{columnId}")
    public Response patchColumn(@PathParam("columnId") Long columnId, @Valid BoardColumnNamePatch request) {
        
        Long userId = Long.parseLong(jwt.getSubject());

        BoardColumn updatedColumn = boardColumnService.updateColumn(columnId, request, userId);
        
        return Response.ok(updatedColumn).build();
    }
}
