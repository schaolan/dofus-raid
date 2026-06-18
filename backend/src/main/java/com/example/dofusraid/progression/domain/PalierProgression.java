package com.example.dofusraid.progression.domain;

import java.time.Instant;

/**
 * Etat d'un palier pour une progression donnee. Modele de domaine pur (immuable).
 */
public record PalierProgression(
        Long id,
        Long progressionRaidId,
        Long palierId,
        PalierStatus statut,
        Instant unlockedAt,
        Instant validatedAt
) {

    public static PalierProgression locked(Long progressionRaidId, Long palierId) {
        return new PalierProgression(null, progressionRaidId, palierId, PalierStatus.LOCKED, null, null);
    }

    public static PalierProgression unlocked(Long progressionRaidId, Long palierId, Instant now) {
        return new PalierProgression(null, progressionRaidId, palierId, PalierStatus.UNLOCKED, now, null);
    }

    public PalierProgression unlock(Instant now) {
        return new PalierProgression(id, progressionRaidId, palierId, PalierStatus.UNLOCKED, now, validatedAt);
    }

    public PalierProgression validate(Instant now) {
        return new PalierProgression(id, progressionRaidId, palierId, PalierStatus.VALIDATED, unlockedAt, now);
    }
}
