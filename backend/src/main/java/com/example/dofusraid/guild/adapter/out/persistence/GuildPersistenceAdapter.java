package com.example.dofusraid.guild.adapter.out.persistence;

import com.example.dofusraid.guild.application.port.out.LoadGuildPort;
import com.example.dofusraid.guild.domain.Guild;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GuildPersistenceAdapter implements LoadGuildPort {

    public static final String GUILDS_CACHE = "guilds";
    public static final String GUILD_BY_ID_CACHE = "guildById";

    private final GuildJpaRepository repository;

    public GuildPersistenceAdapter(GuildJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(GUILDS_CACHE)
    public List<Guild> findAll() {
        return repository.findAll().stream()
                .map(GuildPersistenceMapper::toDomain)
                .toList();
    }

    // Spring deballe l'Optional : #result est la Guild ou null. On ne met pas en cache
    // les ids inconnus pour qu'une guilde ajoutee plus tard soit visible immediatement.
    @Override
    @Cacheable(cacheNames = GUILD_BY_ID_CACHE, unless = "#result == null")
    public Optional<Guild> findById(Long guildId) {
        return repository.findById(guildId).map(GuildPersistenceMapper::toDomain);
    }
}
