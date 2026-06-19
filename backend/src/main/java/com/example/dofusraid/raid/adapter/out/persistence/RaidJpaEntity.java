package com.example.dofusraid.raid.adapter.out.persistence;

import com.example.dofusraid.raid.domain.Difficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "raids")
public class RaidJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "niveau_recommande", length = 50)
    private String niveauRecommande;

    @Column(name = "duree_estimee_minutes")
    private Integer dureeEstimeeMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Difficulty difficulte;

    @Column(nullable = false)
    private boolean actif;

    protected RaidJpaEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getNiveauRecommande() {
        return niveauRecommande;
    }

    public Integer getDureeEstimeeMinutes() {
        return dureeEstimeeMinutes;
    }

    public Difficulty getDifficulte() {
        return difficulte;
    }

    public boolean isActif() {
        return actif;
    }
}
