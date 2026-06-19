package com.example.dofusraid.progression.adapter.out.persistence;

import com.example.dofusraid.progression.domain.PalierStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "palier_progressions")
public class PalierProgressionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "progression_raid_id", nullable = false)
    private Long progressionRaidId;

    @Column(name = "palier_id", nullable = false)
    private Long palierId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PalierStatus statut;

    @Column(name = "unlocked_at")
    private Instant unlockedAt;

    @Column(name = "validated_at")
    private Instant validatedAt;

    protected PalierProgressionJpaEntity() {
    }

    PalierProgressionJpaEntity(
            Long id,
            Long progressionRaidId,
            Long palierId,
            PalierStatus statut,
            Instant unlockedAt,
            Instant validatedAt
    ) {
        this.id = id;
        this.progressionRaidId = progressionRaidId;
        this.palierId = palierId;
        this.statut = statut;
        this.unlockedAt = unlockedAt;
        this.validatedAt = validatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProgressionRaidId() {
        return progressionRaidId;
    }

    public Long getPalierId() {
        return palierId;
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
}
