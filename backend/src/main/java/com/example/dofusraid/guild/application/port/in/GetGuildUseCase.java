package com.example.dofusraid.guild.application.port.in;

import com.example.dofusraid.guild.domain.Guild;

public interface GetGuildUseCase {
    Guild getGuild(Long guildId);
}
