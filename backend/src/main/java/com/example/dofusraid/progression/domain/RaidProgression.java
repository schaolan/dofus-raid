package com.example.dofusraid.progression.domain;

import java.time.Instant;

/**
 * Aggregat de progression d'une guilde sur un raid. Modele de domaine pur (immuable) :
 * les transitions retournent une nouvelle instance.
 */
public record RaidProgression(
        Long id,
        Long guildId,
        Long raidId,
        Integer palierActuelOrdre,
        RaidStatus statut,
        Instant startedAt,
        Instant updatedAt
) {

    public static RaidProgression start(Long guildId, Long raidId, int premierOrdre, Instant now) {
        return new RaidProgression(null, guildId, raidId, premierOrdre, RaidStatus.IN_PROGRESS, now, now);
    }

    public RaidProgression moveTo(int ordre, Instant now) {
        return new RaidProgression(id, guildId, raidId, ordre, RaidStatus.IN_PROGRESS, startedAt, now);
    }

    public RaidProgression complete(Instant now) {
        return new RaidProgression(id, guildId, raidId, null, RaidStatus.COMPLETED, startedAt, now);
    }
}
