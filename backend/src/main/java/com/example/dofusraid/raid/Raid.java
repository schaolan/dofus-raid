package com.example.dofusraid.raid;

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
public class Raid {

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNiveauRecommande(String niveauRecommande) {
        this.niveauRecommande = niveauRecommande;
    }

    public void setDureeEstimeeMinutes(Integer dureeEstimeeMinutes) {
        this.dureeEstimeeMinutes = dureeEstimeeMinutes;
    }

    public void setDifficulte(Difficulty difficulte) {
        this.difficulte = difficulte;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
