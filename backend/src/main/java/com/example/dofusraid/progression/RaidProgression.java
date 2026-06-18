package com.example.dofusraid.progression;

import com.example.dofusraid.guild.Guild;
import com.example.dofusraid.raid.Raid;
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
@Table(name = "raid_progressions")
public class RaidProgression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "raid_id")
    private Raid raid;

    @Column(name = "palier_actuel_ordre")
    private Integer palierActuelOrdre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RaidStatus statut;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public Guild getGuild() {
        return guild;
    }

    public Raid getRaid() {
        return raid;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
    }

    public void setPalierActuelOrdre(Integer palierActuelOrdre) {
        this.palierActuelOrdre = palierActuelOrdre;
    }

    public void setStatut(RaidStatus statut) {
        this.statut = statut;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
