package org.acme.auth.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity; // Id auto, écriture raccourcie
import jakarta.persistence.Column;
import jakarta.persistence.Entity; // @Entity
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table; // @Table

@Entity // référencer la table sur postgres et sur swagger
@Table(name = "users")
public class User extends PanacheEntity {
 // pas besoin de référencer l'id, Panache le fait tout seul 

    @Column(unique = true) // L'email ne pourra etre enregistré qu'une seule fois
    public String email;
    
    @JsonIgnore // pour ne pas renvoyer de mdp lors des @Get
    // Fonctionne a l'aide du packet quarkus-rest-jackson, pour renvoyer du json
    @Column(nullable = false)
    public String password; // truc spécial pour le mdp

    public String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Role role = Role.USER; // faudra créer l'entité Role

    @CreationTimestamp 
    @Column(updatable = false)
    public LocalDateTime createdAt; // Faudra trouver l'import datetimeLocal

    public enum Role { // Pour l'instant je garde l'enum car y'a que deux roles dansle sujet
        USER, ADMIN
    }
}