package com.example.dofusraid.chat.domain;

import java.time.Instant;

/**
 * Resultat d'une question : la reponse contextualisee et son contexte (raid + palier).
 */
public record ChatExchange(
        String question,
        String answer,
        Long raidId,
        Long palierId,
        Instant createdAt
) {
}
