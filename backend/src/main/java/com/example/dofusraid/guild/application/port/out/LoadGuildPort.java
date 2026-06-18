package com.example.dofusraid.guild.application.port.out;

import com.example.dofusraid.guild.domain.Guild;

import java.util.List;
import java.util.Optional;

public interface LoadGuildPort {
    List<Guild> findAll();

    Optional<Guild> findById(Long guildId);
}
