package com.example.dofusraid.raid.application.port.in;

import com.example.dofusraid.raid.domain.Palier;

import java.util.List;
import java.util.Optional;

public interface GetPaliersUseCase {

    List<Palier> getPaliers(Long raidId);

    Palier getPalier(Long palierId);

    Optional<Palier> getNextPalier(Long raidId, int ordre);
}
