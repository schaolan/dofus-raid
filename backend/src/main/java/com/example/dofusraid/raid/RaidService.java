package com.example.dofusraid.raid;

import com.example.dofusraid.common.exception.NotFoundException;
import com.example.dofusraid.progression.PalierProgressionRepository;
import com.example.dofusraid.progression.PalierStatus;
import com.example.dofusraid.raid.dto.PalierResponse;
import com.example.dofusraid.raid.dto.RaidDetailResponse;
import com.example.dofusraid.raid.dto.RaidSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RaidService {

    private final RaidRepository raidRepository;
    private final PalierRepository palierRepository;
    private final PalierProgressionRepository palierProgressionRepository;

    public RaidService(
            RaidRepository raidRepository,
            PalierRepository palierRepository,
            PalierProgressionRepository palierProgressionRepository
    ) {
        this.raidRepository = raidRepository;
        this.palierRepository = palierRepository;
        this.palierProgressionRepository = palierProgressionRepository;
    }

    public List<RaidSummaryResponse> getRaids() {
        return raidRepository.findAll().stream()
                .map(raid -> new RaidSummaryResponse(
                        raid.getId(),
                        raid.getSlug(),
                        raid.getNom(),
                        raid.getDescription(),
                        raid.getNiveauRecommande(),
                        raid.getDureeEstimeeMinutes(),
                        raid.getDifficulte(),
                        raid.isActif()
                ))
                .toList();
    }

    public RaidDetailResponse getRaid(Long raidId) {
        Raid raid = raidRepository.findById(raidId)
                .orElseThrow(() -> new NotFoundException("Raid introuvable"));

        return new RaidDetailResponse(
                raid.getId(),
                raid.getSlug(),
                raid.getNom(),
                raid.getDescription(),
                raid.getNiveauRecommande(),
                raid.getDureeEstimeeMinutes(),
                raid.getDifficulte(),
                raid.isActif()
        );
    }

    public List<PalierResponse> getPaliers(Long raidId, Long guildId) {
        List<Palier> paliers = palierRepository.findByRaidIdOrderByOrdreAsc(raidId);

        return paliers.stream()
                .map(palier -> {
                    PalierStatus status = PalierStatus.LOCKED;

                    if (guildId != null) {
                        status = palierProgressionRepository
                                .findStatusByGuildIdAndRaidIdAndPalierId(guildId, raidId, palier.getId())
                                .orElse(PalierStatus.LOCKED);
                    }

                    return new PalierResponse(
                            palier.getId(),
                            palier.getOrdre(),
                            palier.getNom(),
                            palier.getResume(),
                            palier.getObjectif(),
                            splitPipeValues(palier.getActions()),
                            splitPipeValues(palier.getRoles()),
                            splitPipeValues(palier.getPieges()),
                            palier.getConditionsReussite(),
                            palier.getSuite(),
                            palier.getConseil(),
                            status
                    );
                })
                .toList();
    }

    private List<String> splitPipeValues(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }

        return Arrays.stream(value.split("\\|"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}
