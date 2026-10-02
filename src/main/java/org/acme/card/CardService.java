package org.acme.card;

import org.acme.boardColumn.entity.BoardColumn;
import org.acme.card.dto.CardRequest;
import org.acme.card.entity.Card;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class CardService {

    public List<Card> getCardsByColumn(Long columnId, Long userId) {
        BoardColumn column = BoardColumn.findById(columnId);
        
        if (column == null || !column.board.owner.id.equals(userId)) {
            throw new WebApplicationException("Colonne introuvable ou accès refusé", Response.Status.NOT_FOUND);
        }
        
        return Card.list("boardColumn.id", columnId);
    }

    @Transactional
    public Card createCard(Long columnId, CardRequest request, Long userId) {
        BoardColumn column = BoardColumn.findById(columnId);
        
        if (column == null || !column.board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity("Colonne introuvable").build()
            );
        }

        Card card = new Card();
        card.title = request.title;
        card.description = request.description;
        card.boardColumn = column;
        
        card.persist();
        return card;
    }

    @Transactional
    public void deleteCard(Long cardId, Long userId) {
        Card card = Card.findById(cardId);
        
        if (card == null || !card.boardColumn.board.owner.id.equals(userId)) {
            throw new WebApplicationException("Carte introuvable ou accès refusé", Response.Status.NOT_FOUND);
        }

        card.delete();
    }
}