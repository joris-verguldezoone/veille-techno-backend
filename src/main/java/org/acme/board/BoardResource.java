package org.acme.board;

import java.util.List;

import org.acme.board.dto.BoardRequest;
import org.acme.board.dto.BoardTitlePatch;
import org.acme.board.entity.Board;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

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
import jakarta.ws.rs.core.Response.Status;


@Path("/api/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated 
public class BoardResource {

    @Inject
    JsonWebToken jwt; // pour avoir accès aux méthodes

    @Inject
    BoardService boardService;

    private static final Logger LOG = Logger.getLogger(BoardResource.class);

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
    public Response getMyBoards() {
        try {
            Long userId = getUserIdFromToken();

            List<Board> boards = boardService.getBoardsByUser(userId);
    
            return Response.ok(boards).build();
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response createBoard(@Valid BoardRequest request) {
        Long userId = getUserIdFromToken();
        Board board = boardService.createBoard(request, userId);

        return Response.status(Response.Status.CREATED).entity(board).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBoard(@PathParam("id") Long boardId) {
        Long userId = getUserIdFromToken();
        boardService.deleteBoard(boardId, userId);

        return Response.noContent().build(); 
    } 

    @PATCH
    @Path("/{boardId}")
    public Response patchBoard(@PathParam("boardId") Long boardId, @Valid BoardTitlePatch request) {
        
        Long userId = getUserIdFromToken();

        Board updatedBoard = boardService.updateBoard(boardId, request, userId);
        
        return Response.ok(updatedBoard).build();
    }
    
}