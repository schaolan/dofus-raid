package com.example.dofusraid.progression.application.port.out;

import com.example.dofusraid.progression.domain.PalierProgression;
import com.example.dofusraid.progression.domain.PalierStatus;

import java.util.Optional;

public interface PalierProgressionPort {

    PalierProgression save(PalierProgression palierProgression);

    Optional<PalierProgression> findByProgressionIdAndPalierId(Long progressionId, Long palierId);

    Optional<PalierStatus> findStatus(Long guildId, Long raidId, Long palierId);
}
