package org.acme.board;

import org.acme.auth.entity.User;
import org.acme.board.dto.BoardRequest;
import org.acme.board.dto.BoardTitlePatch;
import org.acme.board.entity.Board;
import org.acme.boardColumn.dto.BoardColumnNamePatch;
import org.hibernate.DuplicateMappingException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class BoardService {

    public List<Board> getBoardsByUser(Long userId) {

        return Board.list("owner.id", userId);
    }

    @Transactional
    public Board createBoard(BoardRequest request, Long userId) {        
        // if (user == null) {
        //     throw new NotFoundException("Utilisateur introuvable");
        // }

        // long count = Board.count("owner.id = ?1 and title = ?2", userId, request.title);
       
        // if (count > 0) {
        //     throw new WebApplicationException("Vous avez déjà un tableau portant ce nom", Response.Status.CONFLICT);
        // }

        User user = User.findById(userId);
    
        if (user == null) {
            // Response plutôt que Exception 
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                        .entity("Utilisateur introuvable")
                        .build()
            );
        }

        long count = Board.count("owner.id = ?1 and title = ?2", userId, request.title);
    
        if (count > 0) {
            throw new WebApplicationException(
                Response.status(Response.Status.CONFLICT)
                        .entity("Ce tableau existe déjà")
                        .build()
            );
        }

        Board board = new Board();
        board.title = request.title;
        board.owner = user;
        
    
        board.persist();
       
        return board;
    }

    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = Board.findById(boardId);
        
        if (board == null) {
            throw new NotFoundException("Tableau introuvable");
        }
        
        // userid = ownerid
        if (!board.owner.id.equals(userId)) {
            throw new WebApplicationException("Accès refusé", Response.Status.FORBIDDEN);
        }

        board.delete();
    }

    @Transactional 
    public Board updateBoard(Long boardId, BoardTitlePatch request, Long userId) {

        Board currentBoard = Board.findById(boardId);

        if (currentBoard == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity("Tableau introuvable").build()
            );
        }

        if (!currentBoard.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN).entity("Accès refusé").build()
            );
        }

        if (request.title != null && !request.title.trim().isEmpty()) {
            currentBoard.title = request.title;
        }

        return currentBoard;
    }

}