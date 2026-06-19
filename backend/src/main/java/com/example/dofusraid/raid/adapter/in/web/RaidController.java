package com.example.dofusraid.raid.adapter.in.web;

import com.example.dofusraid.progression.adapter.in.web.ProgressionWebMapper;
import com.example.dofusraid.progression.adapter.in.web.dto.PalierResponse;
import com.example.dofusraid.progression.application.port.in.PalierViewQuery;
import com.example.dofusraid.raid.adapter.in.web.dto.RaidDetailResponse;
import com.example.dofusraid.raid.adapter.in.web.dto.RaidSummaryResponse;
import com.example.dofusraid.raid.application.port.in.GetRaidUseCase;
import com.example.dofusraid.raid.application.port.in.GetRaidsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/raids")
public class RaidController {

    private final GetRaidsUseCase getRaidsUseCase;
    private final GetRaidUseCase getRaidUseCase;
    private final PalierViewQuery palierViewQuery;

    public RaidController(
            GetRaidsUseCase getRaidsUseCase,
            GetRaidUseCase getRaidUseCase,
            PalierViewQuery palierViewQuery
    ) {
        this.getRaidsUseCase = getRaidsUseCase;
        this.getRaidUseCase = getRaidUseCase;
        this.palierViewQuery = palierViewQuery;
    }

    @GetMapping
    public List<RaidSummaryResponse> getRaids() {
        return getRaidsUseCase.getRaids().stream()
                .map(RaidWebMapper::toSummary)
                .toList();
    }

    @GetMapping("/{raidId}")
    public RaidDetailResponse getRaid(@PathVariable Long raidId) {
        return RaidWebMapper.toDetail(getRaidUseCase.getRaid(raidId));
    }

    /**
     * Paliers d'un raid avec leur statut pour une guilde. Le statut etant un concept du
     * contexte progression, l'enrichissement est delegue a son port d'entree.
     */
    @GetMapping("/{raidId}/paliers")
    public List<PalierResponse> getPaliers(
            @PathVariable Long raidId,
            @RequestParam(required = false) Long guildId
    ) {
        return palierViewQuery.getPaliers(guildId, raidId).stream()
                .map(ProgressionWebMapper::toPalierResponse)
                .toList();
    }
}
