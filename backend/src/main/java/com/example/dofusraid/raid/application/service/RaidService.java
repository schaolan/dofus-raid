package com.example.dofusraid.raid.application.service;

import com.example.dofusraid.common.exception.NotFoundException;
import com.example.dofusraid.raid.application.port.in.GetPaliersUseCase;
import com.example.dofusraid.raid.application.port.in.GetRaidUseCase;
import com.example.dofusraid.raid.application.port.in.GetRaidsUseCase;
import com.example.dofusraid.raid.application.port.out.LoadPalierPort;
import com.example.dofusraid.raid.application.port.out.LoadRaidPort;
import com.example.dofusraid.raid.domain.Palier;
import com.example.dofusraid.raid.domain.Raid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RaidService implements GetRaidsUseCase, GetRaidUseCase, GetPaliersUseCase {

    private final LoadRaidPort loadRaidPort;
    private final LoadPalierPort loadPalierPort;

    public RaidService(LoadRaidPort loadRaidPort, LoadPalierPort loadPalierPort) {
        this.loadRaidPort = loadRaidPort;
        this.loadPalierPort = loadPalierPort;
    }

    @Override
    public List<Raid> getRaids() {
        return loadRaidPort.findAll();
    }

    @Override
    public Raid getRaid(Long raidId) {
        return loadRaidPort.findById(raidId)
                .orElseThrow(() -> new NotFoundException("Raid introuvable"));
    }

    @Override
    public List<Palier> getPaliers(Long raidId) {
        return loadPalierPort.findByRaidIdOrdered(raidId);
    }

    @Override
    public Palier getPalier(Long palierId) {
        return loadPalierPort.findById(palierId)
                .orElseThrow(() -> new NotFoundException("Palier introuvable"));
    }

    @Override
    public Optional<Palier> getNextPalier(Long raidId, int ordre) {
        return loadPalierPort.findByRaidIdAndOrdre(raidId, ordre + 1);
    }
}
