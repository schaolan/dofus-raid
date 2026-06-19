package com.example.dofusraid.progression.adapter.in.web;

import com.example.dofusraid.progression.adapter.in.web.dto.ProgressionResponse;
import com.example.dofusraid.progression.application.port.in.ProgressionUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guilds/{guildId}/raids/{raidId}")
public class ProgressionController {

    private final ProgressionUseCase progressionUseCase;

    public ProgressionController(ProgressionUseCase progressionUseCase) {
        this.progressionUseCase = progressionUseCase;
    }

    @PostMapping("/start")
    public ProgressionResponse start(
            @PathVariable Long guildId,
            @PathVariable Long raidId
    ) {
        return ProgressionWebMapper.toProgressionResponse(progressionUseCase.start(guildId, raidId));
    }

    @GetMapping("/progression")
    public ProgressionResponse getProgression(
            @PathVariable Long guildId,
            @PathVariable Long raidId
    ) {
        return ProgressionWebMapper.toProgressionResponse(progressionUseCase.getProgression(guildId, raidId));
    }

    @PostMapping("/paliers/{palierId}/validate")
    public ProgressionResponse validatePalier(
            @PathVariable Long guildId,
            @PathVariable Long raidId,
            @PathVariable Long palierId
    ) {
        return ProgressionWebMapper.toProgressionResponse(
                progressionUseCase.validatePalier(guildId, raidId, palierId));
    }
}
