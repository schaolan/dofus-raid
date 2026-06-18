package com.example.dofusraid.raid.application.port.out;

import com.example.dofusraid.raid.domain.Palier;

import java.util.List;
import java.util.Optional;

public interface LoadPalierPort {

    List<Palier> findByRaidIdOrdered(Long raidId);

    Optional<Palier> findByRaidIdAndOrdre(Long raidId, int ordre);

    Optional<Palier> findById(Long palierId);
}
