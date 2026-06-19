package com.example.dofusraid.progression.adapter.out.persistence;

import com.example.dofusraid.progression.domain.PalierProgression;
import com.example.dofusraid.progression.domain.RaidProgression;

final class ProgressionPersistenceMapper {

    private ProgressionPersistenceMapper() {
    }

    static RaidProgressionJpaEntity toEntity(RaidProgression domain) {
        return new RaidProgressionJpaEntity(
                domain.id(),
                domain.guildId(),
                domain.raidId(),
                domain.palierActuelOrdre(),
                domain.statut(),
                domain.startedAt(),
                domain.updatedAt()
        );
    }

    static RaidProgression toDomain(RaidProgressionJpaEntity entity) {
        return new RaidProgression(
                entity.getId(),
                entity.getGuildId(),
                entity.getRaidId(),
                entity.getPalierActuelOrdre(),
                entity.getStatut(),
                entity.getStartedAt(),
                entity.getUpdatedAt()
        );
    }

    static PalierProgressionJpaEntity toEntity(PalierProgression domain) {
        return new PalierProgressionJpaEntity(
                domain.id(),
                domain.progressionRaidId(),
                domain.palierId(),
                domain.statut(),
                domain.unlockedAt(),
                domain.validatedAt()
        );
    }

    static PalierProgression toDomain(PalierProgressionJpaEntity entity) {
        return new PalierProgression(
                entity.getId(),
                entity.getProgressionRaidId(),
                entity.getPalierId(),
                entity.getStatut(),
                entity.getUnlockedAt(),
                entity.getValidatedAt()
        );
    }
}
