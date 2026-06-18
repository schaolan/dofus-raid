package com.example.dofusraid.guild.application.service;

import com.example.dofusraid.common.exception.NotFoundException;
import com.example.dofusraid.guild.application.port.in.GetGuildUseCase;
import com.example.dofusraid.guild.application.port.in.ListGuildsUseCase;
import com.example.dofusraid.guild.application.port.out.LoadGuildPort;
import com.example.dofusraid.guild.domain.Guild;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService implements ListGuildsUseCase, GetGuildUseCase {

    private final LoadGuildPort loadGuildPort;

    public GuildService(LoadGuildPort loadGuildPort) {
        this.loadGuildPort = loadGuildPort;
    }

    @Override
    public List<Guild> listGuilds() {
        return loadGuildPort.findAll();
    }

    @Override
    public Guild getGuild(Long guildId) {
        return loadGuildPort.findById(guildId)
                .orElseThrow(() -> new NotFoundException("Guilde introuvable"));
    }
}
