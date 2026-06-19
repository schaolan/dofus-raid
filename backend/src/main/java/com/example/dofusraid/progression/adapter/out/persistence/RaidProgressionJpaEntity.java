package com.example.dofusraid.progression.adapter.out.persistence;

import com.example.dofusraid.progression.domain.RaidStatus;
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
@Table(name = "raid_progressions")
public class RaidProgressionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guild_id", nullable = false)
    private Long guildId;

    @Column(name = "raid_id", nullable = false)
    private Long raidId;

    @Column(name = "palier_actuel_ordre")
    private Integer palierActuelOrdre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RaidStatus statut;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected RaidProgressionJpaEntity() {
    }

    RaidProgressionJpaEntity(
            Long id,
            Long guildId,
            Long raidId,
            Integer palierActuelOrdre,
            RaidStatus statut,
            Instant startedAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.guildId = guildId;
        this.raidId = raidId;
        this.palierActuelOrdre = palierActuelOrdre;
        this.statut = statut;
        this.startedAt = startedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getGuildId() {
        return guildId;
    }

    public Long getRaidId() {
        return raidId;
    }

    public Integer getPalierActuelOrdre() {
        return palierActuelOrdre;
    }

    public RaidStatus getStatut() {
        return statut;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
