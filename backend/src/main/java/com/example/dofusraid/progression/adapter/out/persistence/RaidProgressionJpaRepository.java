package com.example.dofusraid.progression.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaidProgressionJpaRepository extends JpaRepository<RaidProgressionJpaEntity, Long> {
    Optional<RaidProgressionJpaEntity> findByGuildIdAndRaidId(Long guildId, Long raidId);
}
