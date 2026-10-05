package org.acme.board;

import java.util.List;

import org.acme.board.dto.BoardRequest;
import org.acme.board.dto.BoardTitlePatch;
import org.acme.board.entity.Board;
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

@Path("/api/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated 
public class BoardResource {

    @Inject
    JsonWebToken jwt; // pour avoir accès aux méthodes

    @Inject
    BoardService boardService;

    private Long getUserIdFromToken() { 
        Number userId = jwt.getClaim("userId");
        
        if (userId == null) {
            throw new NotAuthorizedException("Token invalide ou userId manquant");
        }
        
        return userId.longValue();
    }

    @GET
    public Response getMyBoards() {
        Long userId = getUserIdFromToken();
        List<Board> boards = boardService.getBoardsByUser(userId);

        return Response.ok(boards).build();
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