package com.example.dofusraid.progression.application.port.out;

import com.example.dofusraid.progression.domain.RaidProgression;

import java.util.Optional;

public interface RaidProgressionPort {

    RaidProgression save(RaidProgression progression);

    Optional<RaidProgression> findByGuildIdAndRaidId(Long guildId, Long raidId);
}
