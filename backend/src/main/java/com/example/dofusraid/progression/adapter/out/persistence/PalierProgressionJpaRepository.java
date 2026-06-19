package com.example.dofusraid.progression.adapter.out.persistence;

import com.example.dofusraid.progression.domain.PalierStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PalierProgressionJpaRepository extends JpaRepository<PalierProgressionJpaEntity, Long> {

    Optional<PalierProgressionJpaEntity> findByProgressionRaidIdAndPalierId(
            Long progressionRaidId,
            Long palierId
    );

    @Query("""
            select pp.statut from PalierProgressionJpaEntity pp, RaidProgressionJpaEntity rp
            where pp.progressionRaidId = rp.id
              and rp.guildId = :guildId
              and rp.raidId = :raidId
              and pp.palierId = :palierId
            """)
    Optional<PalierStatus> findStatus(
            @Param("guildId") Long guildId,
            @Param("raidId") Long raidId,
            @Param("palierId") Long palierId
    );
}
