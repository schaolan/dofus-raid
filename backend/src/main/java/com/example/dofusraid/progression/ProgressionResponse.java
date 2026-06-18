package com.example.dofusraid.progression;

import com.example.dofusraid.raid.dto.PalierResponse;

import java.util.List;

public record ProgressionResponse(
        Long guildId,
        Long raidId,
        RaidStatus statut,
        Integer palierActuelOrdre,
        List<PalierResponse> paliers
) {
}
