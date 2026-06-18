package com.example.dofusraid.progression.adapter.out.external;

import com.example.dofusraid.progression.application.port.out.RaidCatalogPort;
import com.example.dofusraid.raid.application.port.in.GetPaliersUseCase;
import com.example.dofusraid.raid.application.port.in.GetRaidUseCase;
import com.example.dofusraid.raid.domain.Palier;
import com.example.dofusraid.raid.domain.Raid;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter sortant qui relie la progression au contexte raid en s'appuyant uniquement sur
 * ses use cases entrants (pas d'acces direct a la persistance de raid).
 */
@Component
public class RaidCatalogAdapter implements RaidCatalogPort {

    private final GetRaidUseCase getRaidUseCase;
    private final GetPaliersUseCase getPaliersUseCase;

    public RaidCatalogAdapter(GetRaidUseCase getRaidUseCase, GetPaliersUseCase getPaliersUseCase) {
        this.getRaidUseCase = getRaidUseCase;
        this.getPaliersUseCase = getPaliersUseCase;
    }

    @Override
    public Optional<Raid> findRaid(Long raidId) {
        try {
            return Optional.of(getRaidUseCase.getRaid(raidId));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Palier> findPaliers(Long raidId) {
        return getPaliersUseCase.getPaliers(raidId);
    }

    @Override
    public Palier getPalier(Long palierId) {
        return getPaliersUseCase.getPalier(palierId);
    }

    @Override
    public Optional<Palier> findNextPalier(Long raidId, int ordre) {
        return getPaliersUseCase.getNextPalier(raidId, ordre);
    }
}
