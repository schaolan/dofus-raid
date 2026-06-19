package com.example.dofusraid.guild.adapter.out.persistence;

import com.example.dofusraid.guild.domain.Guild;

final class GuildPersistenceMapper {

    private GuildPersistenceMapper() {
    }

    static Guild toDomain(GuildJpaEntity entity) {
        return new Guild(entity.getId(), entity.getNom(), entity.getSlug());
    }
}
