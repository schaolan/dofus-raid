package com.example.dofusraid.progression;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guilds/{guildId}/raids/{raidId}")
public class ProgressionController {

    private final ProgressionService progressionService;

    public ProgressionController(ProgressionService progressionService) {
        this.progressionService = progressionService;
    }

    @PostMapping("/start")
    public ProgressionResponse start(
            @PathVariable Long guildId,
            @PathVariable Long raidId
    ) {
        return progressionService.start(guildId, raidId);
    }

    @GetMapping("/progression")
    public ProgressionResponse getProgression(
            @PathVariable Long guildId,
            @PathVariable Long raidId
    ) {
        return progressionService.getProgression(guildId, raidId);
    }

    @PostMapping("/paliers/{palierId}/validate")
    public ProgressionResponse validatePalier(
            @PathVariable Long guildId,
            @PathVariable Long raidId,
            @PathVariable Long palierId
    ) {
        return progressionService.validatePalier(guildId, raidId, palierId);
    }
}
