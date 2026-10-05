package org.acme.card.entity;

import org.acme.boardColumn.entity.BoardColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Card extends PanacheEntity {
    
    @Column(nullable = false)
    public String title;
    
    @Column(length = 1000)
    public String description;

    // Fetch toutes les données, en meme temps, peut etre a éviter
    @JsonIgnore 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "column_id", nullable = false)
    public BoardColumn boardColumn;
}