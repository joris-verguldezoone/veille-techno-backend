package org.acme.card.entity;

import org.acme.boardColumn.entity.BoardColumn; // Importe ta colonne
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cards")
public class Card extends PanacheEntity {

    public String title;

    @Column(nullable = true) // en fait c'est nullable par defaut
    public String description;

    @CreationTimestamp 
    @Column(updatable = false)
    public LocalDateTime createdAt; 

    @ManyToOne
    @JoinColumn(name = "column_id")
    @JsonIgnore // Casse la boucle JSON // dep circulaire
    public BoardColumn column;
}