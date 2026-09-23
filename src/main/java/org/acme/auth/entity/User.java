package org.acme.auth.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class User extends PanacheEntity {
    public String name;
    public LocalDate birth;
    public Status status;

    public static User findByName(String name){
        return find("name", name).firstResult();
    }

    public static List<User> findAlive(){
        return list("status", Status.Alive);
    }

    public static void deleteStefs(){
        delete("name", "Stef");
    }
}