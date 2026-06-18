package com.example.dofusraid.guild.adapter.in.web;

import com.example.dofusraid.guild.domain.Guild;

public record GuildResponse(Long id, String nom, String slug) {

    public static GuildResponse from(Guild guild) {
        return new GuildResponse(guild.id(), guild.nom(), guild.slug());
    }
}
