package com.example.dofusraid.progression.adapter.in.web.dto;

import java.util.List;

public record ProgressionResponse(
        Long guildId,
        Long raidId,
        String statut,
        Integer palierActuelOrdre,
        List<PalierResponse> paliers
) {
}
