package com.example.dofusraid.progression.application.port.out;

import com.example.dofusraid.guild.domain.Guild;

public interface GuildDirectoryPort {
    Guild getGuild(Long guildId);
}
