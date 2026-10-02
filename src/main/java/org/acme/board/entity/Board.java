package org.acme.board.entity;
import org.acme.auth.entity.User;
import org.acme.boardColumn.entity.BoardColumn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table; 
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "boards", 
    uniqueConstraints = { // Chaque boards sera aura un titre unique en fonction de l'owner_id
        @UniqueConstraint(columnNames = {"owner_id", "title"}) 
    }
)


public class Board extends PanacheEntity {

    @Column(length = 100)
    public String title;
 
    @CreationTimestamp 
    @Column(updatable = false)
    public LocalDateTime createdAt; 

    @ManyToOne
    @JoinColumn(name = "owner_id") // nom du champs
    public User owner;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true) 
    // @JsonIgnore 
    public List<BoardColumn> columns = new ArrayList<>();
}