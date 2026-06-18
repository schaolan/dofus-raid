package com.example.dofusraid.raid.application.port.in;

import com.example.dofusraid.raid.domain.Raid;

public interface GetRaidUseCase {
    Raid getRaid(Long raidId);
}
