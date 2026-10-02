package org.acme.boardColumn;

import java.util.List;
import java.util.Map;

import org.acme.board.entity.Board;
import org.acme.boardColumn.dto.BoardColumnNamePatch;
import org.acme.boardColumn.dto.BoardColumnRequest;
import org.acme.boardColumn.entity.BoardColumn;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class BoardColumnService {

    public List<BoardColumn> getColumnsByBoard(Long boardId, Long userId) {
        Board board = Board.findById(boardId);
        
        validateBoardAccess(board, userId);

        return BoardColumn.list("board.id = ?1", boardId);
    }

    @Transactional
    public BoardColumn createColumn(BoardColumnRequest request, Long userId) {
        Board board = Board.findById(request.boardId);
        
        validateBoardAccess(board, userId);

        // On vérifie le doublon 
        long count = BoardColumn.count("board.id = ?1 and title = ?2", request.boardId, request.title);
        if (count > 0) {
            throw new WebApplicationException(
                Response.status(Response.Status.CONFLICT)
                        .entity(Map.of("error", "Une colonne avec ce nom existe déjà dans ce tableau"))
                        .build()
            );
        }

        BoardColumn column = new BoardColumn();
        column.title = request.title;
        column.board = board;
        
        column.persist();
        return column;
    }

    // Vérifier l'id user avec son board
    private void validateBoardAccess(Board board, Long userId) {
        if (board == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Tableau introuvable"))
                        .build()
            );
        }
        if (!board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN)
                        .entity(Map.of("error", "Vous n'avez pas accès à ce tableau"))
                        .build()
            );
        }
    }
    @Transactional
    public void deleteColumn(Long columnId, Long userId) {

        BoardColumn column = BoardColumn.findById(columnId);
        if (column == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Colonne introuvable")).build()
            );
        }
        // a le droit de supprimer une colonne de ce tableau
        if (!column.board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN).entity(Map.of("error", "Vous n'avez pas le droit de supprimer cette colonne")).build()
            );
        }

        column.delete();
    }

    @Transactional
    public BoardColumn updateColumn(Long columnId, BoardColumnNamePatch request, Long userId) {
        
        BoardColumn column = BoardColumn.findById(columnId);
        if (column == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity("Colonne introuvable").build()
            );
        }

        if (!column.board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN).entity("Accès refusé: vous ne possédez pas ce tableau").build()
            );
        }

        if (request.title != null && !request.title.trim().isEmpty()) {
            column.title = request.title;
        }

        return column;
    }
}
