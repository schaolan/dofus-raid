package com.example.dofusraid.progression;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PalierProgressionRepository extends JpaRepository<PalierProgression, Long> {

    @Query("""
            select pp from PalierProgression pp
            where pp.raidProgression.id = :progressionId
              and pp.palier.id = :palierId
            """)
    Optional<PalierProgression> findByProgressionIdAndPalierId(
            @Param("progressionId") Long progressionId,
            @Param("palierId") Long palierId
    );

    @Query("""
            select pp.statut from PalierProgression pp
            where pp.raidProgression.guild.id = :guildId
              and pp.raidProgression.raid.id = :raidId
              and pp.palier.id = :palierId
            """)
    Optional<PalierStatus> findStatusByGuildIdAndRaidIdAndPalierId(
            @Param("guildId") Long guildId,
            @Param("raidId") Long raidId,
            @Param("palierId") Long palierId
    );
}
