package com.example.dofusraid.progression;

import com.example.dofusraid.raid.Palier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "palier_progressions")
public class PalierProgression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "progression_raid_id")
    private RaidProgression raidProgression;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "palier_id")
    private Palier palier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PalierStatus statut;

    @Column(name = "unlocked_at")
    private Instant unlockedAt;

    @Column(name = "validated_at")
    private Instant validatedAt;

    public Long getId() {
        return id;
    }

    public RaidProgression getRaidProgression() {
        return raidProgression;
    }

    public Palier getPalier() {
        return palier;
    }

    public PalierStatus getStatut() {
        return statut;
    }

    public Instant getUnlockedAt() {
        return unlockedAt;
    }

    public Instant getValidatedAt() {
        return validatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRaidProgression(RaidProgression raidProgression) {
        this.raidProgression = raidProgression;
    }

    public void setPalier(Palier palier) {
        this.palier = palier;
    }

    public void setStatut(PalierStatus statut) {
        this.statut = statut;
    }

    public void setUnlockedAt(Instant unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    public void setValidatedAt(Instant validatedAt) {
        this.validatedAt = validatedAt;
    }
}
