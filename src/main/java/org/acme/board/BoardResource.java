package org.acme.board;

import org.acme.board.dto.BoardRequest;
import org.acme.board.dto.BoardTitlePatch;
import org.acme.board.entity.Board;
import org.acme.boardColumn.dto.BoardColumnNamePatch;
import org.acme.boardColumn.entity.BoardColumn;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/boards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated 
public class BoardResource {

    @Inject
    JsonWebToken jwt; // pour avoir accès aux méthodes

    @Inject
    BoardService boardService;

    @GET
    public Response getMyBoards() {
        Long userId = Long.parseLong(jwt.getSubject());
        List<Board> boards = boardService.getBoardsByUser(userId);

        return Response.ok(boards).build();
    }

    @POST
    public Response createBoard(@Valid BoardRequest request) {
        Long userId = Long.parseLong(jwt.getSubject());
        Board board = boardService.createBoard(request, userId);

        return Response.status(Response.Status.CREATED).entity(board).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBoard(@PathParam("id") Long boardId) {
        Long userId = Long.parseLong(jwt.getSubject());
        boardService.deleteBoard(boardId, userId);

        return Response.noContent().build(); 
    } 

    @PATCH
    @Path("/{boardId}")
    public Response patchBoard(@PathParam("boardId") Long boardId, @Valid BoardTitlePatch request) {
        
        Long userId = Long.parseLong(jwt.getSubject());

        Board updatedBoard = boardService.updateBoard(boardId, request, userId);
        
        return Response.ok(updatedBoard).build();
    }
    
}