package org.acme.auth;

import org.acme.auth.dto.RegisterRequest;
import org.acme.auth.dto.LoginRequest;
import org.acme.auth.entity.User;

import java.time.Duration;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthService {
    
    @Transactional // Permet de modifier la base de données
    
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

    public String login(LoginRequest request) { 
        
        User user = User.find("email", request.email).firstResult();

        if (user == null || !BcryptUtil.matches(request.password, user.password)) { // template de la doc
            throw new WebApplicationException("Identifiants invalides", Response.Status.UNAUTHORIZED);
        }

        return Jwt.
        // issuer("") // inutile pour le moment 
                upn(user.email) // Template en java 
                .subject(user.id.toString())
                .groups(user.role.name())
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}