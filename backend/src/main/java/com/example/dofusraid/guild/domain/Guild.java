package com.example.dofusraid.guild.domain;

/**
 * Modele de domaine pur d'une guilde. Aucune dependance framework.
 */
public record Guild(Long id, String nom, String slug) {
}
