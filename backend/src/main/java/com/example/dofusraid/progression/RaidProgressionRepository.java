package com.example.dofusraid.progression;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaidProgressionRepository extends JpaRepository<RaidProgression, Long> {
    Optional<RaidProgression> findByGuildIdAndRaidId(Long guildId, Long raidId);
}
