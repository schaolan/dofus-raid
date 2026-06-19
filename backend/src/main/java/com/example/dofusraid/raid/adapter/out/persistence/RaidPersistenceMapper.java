package com.example.dofusraid.raid.adapter.out.persistence;

import com.example.dofusraid.raid.domain.Raid;

final class RaidPersistenceMapper {

    private RaidPersistenceMapper() {
    }

    static Raid toDomain(RaidJpaEntity entity) {
        return new Raid(
                entity.getId(),
                entity.getSlug(),
                entity.getNom(),
                entity.getDescription(),
                entity.getNiveauRecommande(),
                entity.getDureeEstimeeMinutes(),
                entity.getDifficulte(),
                entity.isActif()
        );
    }
}
