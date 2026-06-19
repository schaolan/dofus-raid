package com.example.dofusraid.progression.application.service;

import com.example.dofusraid.common.exception.BusinessException;
import com.example.dofusraid.common.exception.NotFoundException;
import com.example.dofusraid.progression.application.port.in.PalierViewQuery;
import com.example.dofusraid.progression.application.port.in.ProgressionUseCase;
import com.example.dofusraid.progression.application.port.out.GuildDirectoryPort;
import com.example.dofusraid.progression.application.port.out.PalierProgressionPort;
import com.example.dofusraid.progression.application.port.out.RaidCatalogPort;
import com.example.dofusraid.progression.application.port.out.RaidProgressionPort;
import com.example.dofusraid.progression.domain.PalierProgression;
import com.example.dofusraid.progression.domain.PalierStatus;
import com.example.dofusraid.progression.domain.PalierView;
import com.example.dofusraid.progression.domain.ProgressionView;
import com.example.dofusraid.progression.domain.RaidProgression;
import com.example.dofusraid.raid.domain.Palier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ProgressionService implements ProgressionUseCase, PalierViewQuery {

    private final RaidProgressionPort raidProgressionPort;
    private final PalierProgressionPort palierProgressionPort;
    private final RaidCatalogPort raidCatalogPort;
    private final GuildDirectoryPort guildDirectoryPort;

    public ProgressionService(
            RaidProgressionPort raidProgressionPort,
            PalierProgressionPort palierProgressionPort,
            RaidCatalogPort raidCatalogPort,
            GuildDirectoryPort guildDirectoryPort
    ) {
        this.raidProgressionPort = raidProgressionPort;
        this.palierProgressionPort = palierProgressionPort;
        this.raidCatalogPort = raidCatalogPort;
        this.guildDirectoryPort = guildDirectoryPort;
    }

    @Override
    @Transactional
    public ProgressionView start(Long guildId, Long raidId) {
        raidProgressionPort.findByGuildIdAndRaidId(guildId, raidId).ifPresent(existing -> {
            throw new BusinessException("Le raid est deja demarre pour cette guilde");
        });

        guildDirectoryPort.getGuild(guildId);
        raidCatalogPort.findRaid(raidId)
                .orElseThrow(() -> new NotFoundException("Raid introuvable"));

        List<Palier> paliers = raidCatalogPort.findPaliers(raidId);
        if (paliers.isEmpty()) {
            throw new BusinessException("Aucun palier disponible pour ce raid");
        }

        Instant now = Instant.now();

        RaidProgression progression = raidProgressionPort.save(
                RaidProgression.start(guildId, raidId, paliers.getFirst().ordre(), now)
        );

        for (int i = 0; i < paliers.size(); i++) {
            Palier palier = paliers.get(i);
            PalierProgression pp = (i == 0)
                    ? PalierProgression.unlocked(progression.id(), palier.id(), now)
                    : PalierProgression.locked(progression.id(), palier.id());
            palierProgressionPort.save(pp);
        }

        return getProgression(guildId, raidId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgressionView getProgression(Long guildId, Long raidId) {
        RaidProgression progression = raidProgressionPort.findByGuildIdAndRaidId(guildId, raidId)
                .orElseThrow(() -> new NotFoundException("Progression introuvable"));

        return new ProgressionView(
                guildId,
                raidId,
                progression.statut(),
                progression.palierActuelOrdre(),
                getPaliers(guildId, raidId)
        );
    }

    @Override
    @Transactional
    public ProgressionView validatePalier(Long guildId, Long raidId, Long palierId) {
        RaidProgression progression = raidProgressionPort.findByGuildIdAndRaidId(guildId, raidId)
                .orElseThrow(() -> new NotFoundException("Progression introuvable"));

        PalierProgression current = palierProgressionPort
                .findByProgressionIdAndPalierId(progression.id(), palierId)
                .orElseThrow(() -> new NotFoundException("Palier introuvable"));

        if (current.statut() != PalierStatus.UNLOCKED) {
            throw new BusinessException("Seul un palier debloque peut etre valide");
        }

        Instant now = Instant.now();
        palierProgressionPort.save(current.validate(now));

        Palier currentPalier = raidCatalogPort.getPalier(palierId);
        Palier nextPalier = raidCatalogPort.findNextPalier(raidId, currentPalier.ordre()).orElse(null);

        if (nextPalier == null) {
            raidProgressionPort.save(progression.complete(now));
            return getProgression(guildId, raidId);
        }

        PalierProgression next = palierProgressionPort
                .findByProgressionIdAndPalierId(progression.id(), nextPalier.id())
                .orElseThrow(() -> new NotFoundException("Progression du palier suivant introuvable"));

        palierProgressionPort.save(next.unlock(now));
        raidProgressionPort.save(progression.moveTo(nextPalier.ordre(), now));

        return getProgression(guildId, raidId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PalierView> getPaliers(Long guildId, Long raidId) {
        return raidCatalogPort.findPaliers(raidId).stream()
                .map(palier -> {
                    PalierStatus statut = PalierStatus.LOCKED;
                    if (guildId != null) {
                        statut = palierProgressionPort.findStatus(guildId, raidId, palier.id())
                                .orElse(PalierStatus.LOCKED);
                    }
                    return new PalierView(palier, statut);
                })
                .toList();
    }
}
