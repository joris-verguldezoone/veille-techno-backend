package org.acme.card;

import java.util.Map;
import org.acme.boardColumn.entity.BoardColumn;
import org.acme.card.dto.CardRequest;
import org.acme.card.entity.Card;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CardService {

    @Transactional
    public Card createCard(CardRequest request, Long userId) {
        BoardColumn column = BoardColumn.findById(request.columnId);
        
        if (column == null) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Colonne introuvable")).build()
            );
        }

        if (!column.board.owner.id.equals(userId)) {
            throw new WebApplicationException(
                Response.status(Response.Status.FORBIDDEN).entity(Map.of("error", "Accès refusé")).build()
            );
        }

        Card card = new Card();
        card.title = request.title;
        card.description = request.description;
        card.column = column;
        
        card.persist();
        return card;
    }
}