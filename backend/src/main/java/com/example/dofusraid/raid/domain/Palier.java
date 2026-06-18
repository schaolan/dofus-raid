package com.example.dofusraid.raid.domain;

import java.util.List;

/**
 * Contenu d'un palier (etape de raid). Modele de domaine pur : les listes d'actions,
 * roles et pieges sont des types metier, l'encodage de stockage est un detail d'adapter.
 */
public record Palier(
        Long id,
        Long raidId,
        Integer ordre,
        String code,
        String nom,
        String resume,
        String objectif,
        List<String> actions,
        List<String> roles,
        List<String> pieges,
        String conditionsReussite,
        String suite,
        String conseil,
        String faq,
        boolean actif
) {
}
