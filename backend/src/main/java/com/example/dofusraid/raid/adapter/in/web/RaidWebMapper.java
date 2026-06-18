package com.example.dofusraid.raid.adapter.in.web;

import com.example.dofusraid.raid.adapter.in.web.dto.RaidDetailResponse;
import com.example.dofusraid.raid.adapter.in.web.dto.RaidSummaryResponse;
import com.example.dofusraid.raid.domain.Raid;

final class RaidWebMapper {

    private RaidWebMapper() {
    }

    static RaidSummaryResponse toSummary(Raid raid) {
        return new RaidSummaryResponse(
                raid.id(),
                raid.slug(),
                raid.nom(),
                raid.description(),
                raid.niveauRecommande(),
                raid.dureeEstimeeMinutes(),
                raid.difficulte().name(),
                raid.actif()
        );
    }

    static RaidDetailResponse toDetail(Raid raid) {
        return new RaidDetailResponse(
                raid.id(),
                raid.slug(),
                raid.nom(),
                raid.description(),
                raid.niveauRecommande(),
                raid.dureeEstimeeMinutes(),
                raid.difficulte().name(),
                raid.actif()
        );
    }
}
