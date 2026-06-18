package com.example.dofusraid.progression.adapter.out.persistence;

import com.example.dofusraid.progression.application.port.out.PalierProgressionPort;
import com.example.dofusraid.progression.application.port.out.RaidProgressionPort;
import com.example.dofusraid.progression.domain.PalierProgression;
import com.example.dofusraid.progression.domain.PalierStatus;
import com.example.dofusraid.progression.domain.RaidProgression;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProgressionPersistenceAdapter implements RaidProgressionPort, PalierProgressionPort {

    private final RaidProgressionJpaRepository raidProgressionRepository;
    private final PalierProgressionJpaRepository palierProgressionRepository;

    public ProgressionPersistenceAdapter(
            RaidProgressionJpaRepository raidProgressionRepository,
            PalierProgressionJpaRepository palierProgressionRepository
    ) {
        this.raidProgressionRepository = raidProgressionRepository;
        this.palierProgressionRepository = palierProgressionRepository;
    }

    @Override
    public RaidProgression save(RaidProgression progression) {
        RaidProgressionJpaEntity saved =
                raidProgressionRepository.save(ProgressionPersistenceMapper.toEntity(progression));
        return ProgressionPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<RaidProgression> findByGuildIdAndRaidId(Long guildId, Long raidId) {
        return raidProgressionRepository.findByGuildIdAndRaidId(guildId, raidId)
                .map(ProgressionPersistenceMapper::toDomain);
    }

    @Override
    public PalierProgression save(PalierProgression palierProgression) {
        PalierProgressionJpaEntity saved =
                palierProgressionRepository.save(ProgressionPersistenceMapper.toEntity(palierProgression));
        return ProgressionPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<PalierProgression> findByProgressionIdAndPalierId(Long progressionId, Long palierId) {
        return palierProgressionRepository.findByProgressionRaidIdAndPalierId(progressionId, palierId)
                .map(ProgressionPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PalierStatus> findStatus(Long guildId, Long raidId, Long palierId) {
        return palierProgressionRepository.findStatus(guildId, raidId, palierId);
    }
}
