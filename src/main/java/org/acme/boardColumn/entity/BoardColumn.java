package org.acme.boardColumn.entity;
import org.acme.auth.entity.User;
import org.acme.board.entity.Board;
import org.acme.card.entity.Card;


import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    name = "board_columns", 
    uniqueConstraints = { // Chaque boardColumn sera aura un titre unique en fonction du board_id
        @UniqueConstraint(columnNames = {"board_id", "title"}) 
    }
)


public class BoardColumn extends PanacheEntity {

    public String title;
 
    @CreationTimestamp 
    @Column(updatable = false)
    public LocalDateTime createdAt; 

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonIgnore // sans ça je boucle sur la relation du parents, et le parent sur la relation de l'enfant
    public Board board;

    @OneToMany(mappedBy = "boardColumn", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Card> cards = new ArrayList<>();
}