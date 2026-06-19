package com.example.dofusraid.guild.adapter.in.web;

import com.example.dofusraid.guild.application.port.in.ListGuildsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/guilds")
public class GuildController {

    private final ListGuildsUseCase listGuildsUseCase;

    public GuildController(ListGuildsUseCase listGuildsUseCase) {
        this.listGuildsUseCase = listGuildsUseCase;
    }

    @GetMapping
    public List<GuildResponse> getGuilds() {
        return listGuildsUseCase.listGuilds().stream()
                .map(GuildResponse::from)
                .toList();
    }
}
