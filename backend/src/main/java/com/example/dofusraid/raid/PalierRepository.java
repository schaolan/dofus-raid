package com.example.dofusraid.raid;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PalierRepository extends JpaRepository<Palier, Long> {
    List<Palier> findByRaidIdOrderByOrdreAsc(Long raidId);

    Optional<Palier> findByRaidIdAndOrdre(Long raidId, Integer ordre);
}
