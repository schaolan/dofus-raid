package com.example.dofusraid.progression.adapter.in.web;

import com.example.dofusraid.progression.adapter.in.web.dto.PalierResponse;
import com.example.dofusraid.progression.adapter.in.web.dto.ProgressionResponse;
import com.example.dofusraid.progression.domain.PalierView;
import com.example.dofusraid.progression.domain.ProgressionView;
import com.example.dofusraid.raid.domain.Palier;

public final class ProgressionWebMapper {

    private ProgressionWebMapper() {
    }

    public static PalierResponse toPalierResponse(PalierView view) {
        Palier palier = view.palier();
        return new PalierResponse(
                palier.id(),
                palier.ordre(),
                palier.nom(),
                palier.resume(),
                palier.objectif(),
                palier.actions(),
                palier.roles(),
                palier.pieges(),
                palier.conditionsReussite(),
                palier.suite(),
                palier.conseil(),
                view.statut().name()
        );
    }

    public static ProgressionResponse toProgressionResponse(ProgressionView view) {
        return new ProgressionResponse(
                view.guildId(),
                view.raidId(),
                view.statut().name(),
                view.palierActuelOrdre(),
                view.paliers().stream().map(ProgressionWebMapper::toPalierResponse).toList()
        );
    }
}
