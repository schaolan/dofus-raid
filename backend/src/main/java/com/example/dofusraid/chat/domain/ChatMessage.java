package com.example.dofusraid.chat.domain;

import java.time.Instant;

/**
 * Echange de chat persiste (question + reponse contextualisee). Modele de domaine pur.
 */
public record ChatMessage(
        Long id,
        Long guildId,
        Long raidId,
        Long palierId,
        String roleJoueur,
        String question,
        String reponse,
        Instant createdAt
) {
    public static ChatMessage create(
            Long guildId,
            Long raidId,
            Long palierId,
            String roleJoueur,
            String question,
            String reponse,
            Instant createdAt
    ) {
        return new ChatMessage(null, guildId, raidId, palierId, roleJoueur, question, reponse, createdAt);
    }
}
