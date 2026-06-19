package com.example.dofusraid.progression.application.port.out;

import com.example.dofusraid.raid.domain.Palier;
import com.example.dofusraid.raid.domain.Raid;

import java.util.List;
import java.util.Optional;

/**
 * Acces au catalogue de raids (contexte raid) depuis la progression, via les use cases
 * entrants de raid.
 */
public interface RaidCatalogPort {

    Optional<Raid> findRaid(Long raidId);

    List<Palier> findPaliers(Long raidId);

    Palier getPalier(Long palierId);

    Optional<Palier> findNextPalier(Long raidId, int ordre);
}
