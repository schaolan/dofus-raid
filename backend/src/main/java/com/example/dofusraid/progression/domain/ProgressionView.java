package com.example.dofusraid.progression.domain;

import java.util.List;

public record ProgressionView(
        Long guildId,
        Long raidId,
        RaidStatus statut,
        Integer palierActuelOrdre,
        List<PalierView> paliers
) {
}
