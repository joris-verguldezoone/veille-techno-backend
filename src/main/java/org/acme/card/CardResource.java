package org.acme.card;

import org.acme.card.dto.CardRequest;
import org.acme.card.entity.Card;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated 
public class CardResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    CardService cardService;

    @GET
    @Path("/column/{columnId}")
    public Response getCards(@PathParam("columnId") Long columnId) {
        Long userId = Long.parseLong(jwt.getSubject());
        
        List<Card> cards = cardService.getCardsByColumn(columnId, userId);
        return Response.ok(cards).build();
    }

    @POST
    @Path("/column/{columnId}")
    public Response createCard(@PathParam("columnId") Long columnId, @Valid CardRequest request) {
        Long userId = Long.parseLong(jwt.getSubject());
        
        Card card = cardService.createCard(columnId, request, userId);
        return Response.status(Response.Status.CREATED).entity(card).build();
    }

    @DELETE
    @Path("/{cardId}")
    public Response deleteCard(@PathParam("cardId") Long cardId) {
        Long userId = Long.parseLong(jwt.getSubject());
        
        cardService.deleteCard(cardId, userId);
        return Response.noContent().build();
    }
}