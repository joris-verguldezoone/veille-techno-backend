package org.acme.card;

import org.acme.card.dto.CardRequest;
import org.acme.card.entity.Card;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*; // cheat code pour aller vite
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

    @POST
    public Response createCard(@Valid CardRequest request) {
        Long userId = Long.parseLong(jwt.getSubject());
        Card card = cardService.createCard(request, userId);
        
        return Response.status(Response.Status.CREATED).entity(card).build();
    }
}