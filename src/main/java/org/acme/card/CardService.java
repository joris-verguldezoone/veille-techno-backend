package org.acme.card;

import java.util.List;

import org.acme.boardColumn.entity.BoardColumn;
import org.acme.card.dto.CardRequest;
import org.acme.card.entity.Card;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CardService {

    public List<Card> getCardsByColumn(Long columnId, Long userId) {
        BoardColumn column = BoardColumn.findById(columnId);
        
        if (column == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity("Colonne introuvable").build()
            );
        }
        
        if (!column.board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN).entity("Accès refusé à cette colonne").build()
            );
        }
        
        return Card.list("boardColumn.id", columnId);
    }


    @Transactional
    public Card createCard(Long columnId, CardRequest request, Long userId) {
        BoardColumn column = BoardColumn.findById(columnId);
        
        if (column == null || !column.board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN).entity("Vous n'êtes pas propriétaire de ce tableau").build()
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
        
        if (card == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity("Carte introuvable").build()
            );
        }

        if (!card.boardColumn.board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN).entity("Accès refusé pour supprimer cette carte").build()
            );
        }

        card.delete();
    }

    @Transactional
    public Card updateCard(Long cardId, CardRequest request, Long userId) {
        Card card = Card.findById(cardId);
        
        if (card == null) {
            throw new NotFoundException("Carte introuvable");
        }
        
        if (request.title != null && !request.title.trim().isEmpty()) {
            card.title = request.title;
        }
        
        if (request.description != null) {
            card.description = request.description;
        }


        return card;
    }
}
