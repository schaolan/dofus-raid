package com.example.dofusraid.guild.application.port.in;

import com.example.dofusraid.guild.domain.Guild;

import java.util.List;

public interface ListGuildsUseCase {
    List<Guild> listGuilds();
}
