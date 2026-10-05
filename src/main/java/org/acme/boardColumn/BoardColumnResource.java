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
import jakarta.ws.rs.PATCH;
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

    private Long getUserIdFromToken() {
        String subject = jwt.getSubject();
        
        if (subject == null) {
            throw new NotAuthorizedException("Token invalide : aucun subject (sub) trouvé");
        }
        
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new NotAuthorizedException("Token champs 'id' non valide : " + subject);
        }
    }
        
    @GET
    @Path("/board/{boardId}")
    public Response getBoardColumns(@PathParam("boardId") Long boardId) {
        List<BoardColumn> columns = boardColumnService.getColumnsByBoard(boardId, getUserIdFromToken());
        return Response.ok(columns).build();
    }

    @POST
    public Response createColumn(@Valid BoardColumnRequest request) {
        System.out.println("DEBUG QUARKUS - Reçu boardId: " + request.boardId + " pour le titre: " + request.title);
        BoardColumn column = boardColumnService.createColumn(request, getUserIdFromToken());
        return Response.status(Response.Status.CREATED).entity(column).build();
    }

    @DELETE
    @Path("/{columnId}") 
    public Response deleteColumn(@PathParam("columnId") Long columnId) {
        Long userId = getUserIdFromToken();
        
        boardColumnService.deleteColumn(columnId, userId);
        
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{columnId}")
    public Response patchColumn(@PathParam("columnId") Long columnId, @Valid BoardColumnNamePatch request) {
        
        Long userId = getUserIdFromToken();

        BoardColumn updatedColumn = boardColumnService.updateColumn(columnId, request, userId);
        
        return Response.ok(updatedColumn).build();
    }
}
