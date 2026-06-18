package com.example.dofusraid.progression.domain;

import com.example.dofusraid.raid.domain.Palier;

/**
 * Vue de lecture : contenu d'un palier (contexte raid) enrichi de son statut (contexte
 * progression) pour une guilde.
 */
public record PalierView(Palier palier, PalierStatus statut) {
}
