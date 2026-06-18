package com.example.dofusraid.raid.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PalierJpaRepository extends JpaRepository<PalierJpaEntity, Long> {
    List<PalierJpaEntity> findByRaidIdOrderByOrdreAsc(Long raidId);

    Optional<PalierJpaEntity> findByRaidIdAndOrdre(Long raidId, Integer ordre);
}
