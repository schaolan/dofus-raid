package com.example.dofusraid.raid.adapter.in.web.dto;

public record RaidDetailResponse(
        Long id,
        String slug,
        String nom,
        String description,
        String niveauRecommande,
        Integer dureeEstimeeMinutes,
        String difficulte,
        boolean actif
) {
}
