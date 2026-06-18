package com.example.dofusraid.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChatRequest(
        @NotNull Long guildId,
        @NotNull Long raidId,
        @NotNull Long palierId,
        String roleJoueur,
        @NotBlank String question
) {
}
