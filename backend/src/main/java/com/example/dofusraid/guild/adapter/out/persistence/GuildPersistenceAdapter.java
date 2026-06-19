package com.example.dofusraid.guild.adapter.out.persistence;

import com.example.dofusraid.guild.application.port.out.LoadGuildPort;
import com.example.dofusraid.guild.domain.Guild;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GuildPersistenceAdapter implements LoadGuildPort {

    private final GuildJpaRepository repository;

    public GuildPersistenceAdapter(GuildJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Guild> findAll() {
        return repository.findAll().stream()
                .map(GuildPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Guild> findById(Long guildId) {
        return repository.findById(guildId).map(GuildPersistenceMapper::toDomain);
    }
}
