package com.example.dofusraid.progression.application.port.in;

import com.example.dofusraid.progression.domain.PalierView;

import java.util.List;

/**
 * Lecture des paliers d'un raid avec leur statut pour une guilde. Si la guilde n'a pas de
 * progression (ou guildId null), tous les paliers sont consideres LOCKED.
 */
public interface PalierViewQuery {
    List<PalierView> getPaliers(Long guildId, Long raidId);
}
