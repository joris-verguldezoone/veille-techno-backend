package org.acme.card;

import java.util.List;

import org.acme.card.dto.CardRequest;
import org.acme.card.entity.Card;
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

@Path("/api/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated 
public class CardResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    CardService cardService;

    private Long getUserIdFromToken() {
        Long userId = Long.parseLong(jwt.getSubject());
        if (userId == null) {
            throw new NotAuthorizedException("Token invalide ou userId manquant");
        }
        return userId.longValue();
    }

    @GET
    @Path("/column/{columnId}")
    public Response getCards(@PathParam("columnId") Long columnId) {
        List<Card> cards = cardService.getCardsByColumn(columnId, getUserIdFromToken());
        return Response.ok(cards).build();
    }

    @POST
    @Path("/column/{columnId}")
    public Response createCard(@PathParam("columnId") Long columnId, @Valid CardRequest request) {
        Card card = cardService.createCard(columnId, request, getUserIdFromToken());
        return Response.status(Response.Status.CREATED).entity(card).build();
    }

    @DELETE
    @Path("/{cardId}")
    public Response deleteCard(@PathParam("cardId") Long cardId) {
        cardService.deleteCard(cardId, getUserIdFromToken());
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{cardId}")
    public Response updateCard(@PathParam("cardId") Long cardId, CardRequest request) {
        Card updatedCard = cardService.updateCard(cardId, request, getUserIdFromToken());
        return Response.ok(updatedCard).build();
    }
}