package com.example.dofusraid.progression.adapter.out.external;

import com.example.dofusraid.guild.application.port.in.GetGuildUseCase;
import com.example.dofusraid.guild.domain.Guild;
import com.example.dofusraid.progression.application.port.out.GuildDirectoryPort;
import org.springframework.stereotype.Component;

@Component
public class GuildDirectoryAdapter implements GuildDirectoryPort {

    private final GetGuildUseCase getGuildUseCase;

    public GuildDirectoryAdapter(GetGuildUseCase getGuildUseCase) {
        this.getGuildUseCase = getGuildUseCase;
    }

    @Override
    public Guild getGuild(Long guildId) {
        return getGuildUseCase.getGuild(guildId);
    }
}
