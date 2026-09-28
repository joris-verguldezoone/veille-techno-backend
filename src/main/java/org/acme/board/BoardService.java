package org.acme.board;

import org.acme.auth.entity.User;
import org.acme.board.dto.BoardRequest;
import org.acme.board.entity.Board;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
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
        User user = User.findById(userId);
        
        if (user == null) {
            throw new NotFoundException("Utilisateur introuvable");
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
}