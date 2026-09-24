package org.acme.auth;

import org.acme.auth.dto.RegisterRequest;
import org.acme.auth.entity.User;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthService {
    // mettre a jour les DTO
    @Transactional // Permet de modifier la base de données
    // On ne fait pas réellement de transaction
    public User register(RegisterRequest request) {
        
        // Estce que l'user existe deja
        if (User.count("email", request.email) > 0) {
            // throw new IllegalArgumentException("Cet email est déjà utilisé");
        throw new WebApplicationException("Cet email est déjà utilisé", 
            Response.Status.CONFLICT); 
            // Exception de quarkus plutot que java natif
        }

        // basé sur l'entité
        User user = new User();
        user.email = request.email;
        user.name = request.name;
        
        // hachage
        user.password = BcryptUtil.bcryptHash(request.password);

        user.persist();

        return user;
    }
}