package com.example.dofusraid.progression.application.port.in;

import com.example.dofusraid.progression.domain.ProgressionView;

public interface ProgressionUseCase {

    ProgressionView start(Long guildId, Long raidId);

    ProgressionView getProgression(Long guildId, Long raidId);

    ProgressionView validatePalier(Long guildId, Long raidId, Long palierId);
}
