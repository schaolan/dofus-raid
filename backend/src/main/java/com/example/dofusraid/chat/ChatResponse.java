package com.example.dofusraid.chat;

import java.time.Instant;
import java.util.Map;

public record ChatResponse(
        String question,
        String answer,
        Map<String, Object> context,
        Instant createdAt
) {
}
