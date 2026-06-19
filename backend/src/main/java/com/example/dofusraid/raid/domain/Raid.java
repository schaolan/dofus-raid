package com.example.dofusraid.raid.domain;

/**
 * Contenu statique d'un raid. Modele de domaine pur.
 */
public record Raid(
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
