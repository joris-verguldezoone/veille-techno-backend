package org.acme.card;

import org.acme.boardColumn.entity.BoardColumn;
import org.acme.card.dto.CardRequest;
import org.acme.card.entity.Card;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class CardService {

    public List<Card> getCardsByColumn(Long columnId, Long userId) {
        return Card.find("column.id = ?1", columnId).list();
    }

    @Transactional
    public Card createCard(Long columnId, CardRequest request, Long userId) {
        BoardColumn column = BoardColumn.findById(columnId);
        if (column == null) {
            throw new NotFoundException("La colonne n'existe pas");
        }

        Card card = new Card();
        card.title = request.title;
        card.description = request.description;
        
        card.column = column; 
        
        card.persist();
        return card;
    }

    @Transactional
    public void deleteCard(Long cardId, Long userId) {
        Card card = Card.findById(cardId);
        
        if (card == null) {
            throw new NotFoundException("Carte introuvable");
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