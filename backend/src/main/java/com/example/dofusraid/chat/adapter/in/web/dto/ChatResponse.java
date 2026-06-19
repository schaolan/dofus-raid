package com.example.dofusraid.chat.adapter.in.web.dto;

import com.example.dofusraid.chat.domain.ChatExchange;

import java.time.Instant;
import java.util.Map;

public record ChatResponse(
        String question,
        String answer,
        Map<String, Object> context,
        Instant createdAt
) {

    public static ChatResponse from(ChatExchange exchange) {
        return new ChatResponse(
                exchange.question(),
                exchange.answer(),
                Map.of("raidId", exchange.raidId(), "palierId", exchange.palierId()),
                exchange.createdAt()
        );
    }
}
