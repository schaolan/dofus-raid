package com.example.dofusraid.raid.dto;

import com.example.dofusraid.raid.Difficulty;

public record RaidSummaryResponse(
        Long id,
        String slug,
        String nom,
        String description,
        String niveauRecommande,
        Integer dureeEstimeeMinutes,
        Difficulty difficulte,
        boolean actif
) {
}
