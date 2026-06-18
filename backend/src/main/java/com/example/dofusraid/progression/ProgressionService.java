package com.example.dofusraid.progression;

import com.example.dofusraid.common.exception.BusinessException;
import com.example.dofusraid.common.exception.NotFoundException;
import com.example.dofusraid.guild.Guild;
import com.example.dofusraid.guild.GuildRepository;
import com.example.dofusraid.raid.Palier;
import com.example.dofusraid.raid.PalierRepository;
import com.example.dofusraid.raid.Raid;
import com.example.dofusraid.raid.RaidRepository;
import com.example.dofusraid.raid.RaidService;
import com.example.dofusraid.raid.dto.PalierResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ProgressionService {

    private final RaidProgressionRepository raidProgressionRepository;
    private final PalierProgressionRepository palierProgressionRepository;
    private final RaidRepository raidRepository;
    private final GuildRepository guildRepository;
    private final PalierRepository palierRepository;
    private final RaidService raidService;

    public ProgressionService(
            RaidProgressionRepository raidProgressionRepository,
            PalierProgressionRepository palierProgressionRepository,
            RaidRepository raidRepository,
            GuildRepository guildRepository,
            PalierRepository palierRepository,
            RaidService raidService
    ) {
        this.raidProgressionRepository = raidProgressionRepository;
        this.palierProgressionRepository = palierProgressionRepository;
        this.raidRepository = raidRepository;
        this.guildRepository = guildRepository;
        this.palierRepository = palierRepository;
        this.raidService = raidService;
    }

    @Transactional
    public ProgressionResponse start(Long guildId, Long raidId) {
        raidProgressionRepository.findByGuildIdAndRaidId(guildId, raidId).ifPresent(existing -> {
            throw new BusinessException("Le raid est deja demarre pour cette guilde");
        });

        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new NotFoundException("Guilde introuvable"));

        Raid raid = raidRepository.findById(raidId)
                .orElseThrow(() -> new NotFoundException("Raid introuvable"));

        List<Palier> paliers = palierRepository.findByRaidIdOrderByOrdreAsc(raidId);
        if (paliers.isEmpty()) {
            throw new BusinessException("Aucun palier disponible pour ce raid");
        }

        Instant now = Instant.now();

        RaidProgression progression = new RaidProgression();
        progression.setGuild(guild);
        progression.setRaid(raid);
        progression.setStatut(RaidStatus.IN_PROGRESS);
        progression.setPalierActuelOrdre(paliers.getFirst().getOrdre());
        progression.setStartedAt(now);
        progression.setUpdatedAt(now);
        raidProgressionRepository.save(progression);

        for (int i = 0; i < paliers.size(); i++) {
            Palier palier = paliers.get(i);
            PalierProgression palierProgression = new PalierProgression();
            palierProgression.setRaidProgression(progression);
            palierProgression.setPalier(palier);

            if (i == 0) {
                palierProgression.setStatut(PalierStatus.UNLOCKED);
                palierProgression.setUnlockedAt(now);
            } else {
                palierProgression.setStatut(PalierStatus.LOCKED);
            }

            palierProgressionRepository.save(palierProgression);
        }

        return getProgression(guildId, raidId);
    }

    @Transactional(readOnly = true)
    public ProgressionResponse getProgression(Long guildId, Long raidId) {
        RaidProgression progression = raidProgressionRepository.findByGuildIdAndRaidId(guildId, raidId)
                .orElseThrow(() -> new NotFoundException("Progression introuvable"));

        List<PalierResponse> paliers = raidService.getPaliers(raidId, guildId);

        return new ProgressionResponse(
                guildId,
                raidId,
                progression.getStatut(),
                progression.getPalierActuelOrdre(),
                paliers
        );
    }

    @Transactional
    public ProgressionResponse validatePalier(Long guildId, Long raidId, Long palierId) {
        RaidProgression progression = raidProgressionRepository.findByGuildIdAndRaidId(guildId, raidId)
                .orElseThrow(() -> new NotFoundException("Progression introuvable"));

        PalierProgression current = palierProgressionRepository
                .findByProgressionIdAndPalierId(progression.getId(), palierId)
                .orElseThrow(() -> new NotFoundException("Palier introuvable"));

        if (current.getStatut() != PalierStatus.UNLOCKED) {
            throw new BusinessException("Seul un palier debloque peut etre valide");
        }

        Instant now = Instant.now();

        current.setStatut(PalierStatus.VALIDATED);
        current.setValidatedAt(now);

        Integer nextOrdre = current.getPalier().getOrdre() + 1;
        Palier nextPalier = palierRepository.findByRaidIdAndOrdre(raidId, nextOrdre).orElse(null);

        if (nextPalier == null) {
            progression.setStatut(RaidStatus.COMPLETED);
            progression.setPalierActuelOrdre(null);
            progression.setUpdatedAt(now);
            return getProgression(guildId, raidId);
        }

        PalierProgression next = palierProgressionRepository
                .findByProgressionIdAndPalierId(progression.getId(), nextPalier.getId())
                .orElseThrow(() -> new NotFoundException("Progression du palier suivant introuvable"));

        next.setStatut(PalierStatus.UNLOCKED);
        next.setUnlockedAt(now);

        progression.setPalierActuelOrdre(nextOrdre);
        progression.setUpdatedAt(now);

        return getProgression(guildId, raidId);
    }
}
