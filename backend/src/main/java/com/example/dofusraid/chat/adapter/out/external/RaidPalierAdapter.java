package com.example.dofusraid.chat.adapter.out.external;

import com.example.dofusraid.chat.application.port.out.PalierLookupPort;
import com.example.dofusraid.raid.application.port.in.GetPaliersUseCase;
import com.example.dofusraid.raid.domain.Palier;
import org.springframework.stereotype.Component;

/**
 * Le chat lit le contenu d'un palier via le use case entrant du contexte raid.
 */
@Component
public class RaidPalierAdapter implements PalierLookupPort {

    private final GetPaliersUseCase getPaliersUseCase;

    public RaidPalierAdapter(GetPaliersUseCase getPaliersUseCase) {
        this.getPaliersUseCase = getPaliersUseCase;
    }

    @Override
    public Palier getPalier(Long palierId) {
        return getPaliersUseCase.getPalier(palierId);
    }
}
