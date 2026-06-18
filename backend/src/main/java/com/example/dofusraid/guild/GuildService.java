package com.example.dofusraid.guild;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    private final GuildRepository guildRepository;

    public GuildService(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    public List<Guild> findAll() {
        return guildRepository.findAll();
    }
}
