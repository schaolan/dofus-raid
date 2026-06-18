package com.example.dofusraid.chat.application.port.in;

import com.example.dofusraid.chat.domain.ChatExchange;

public interface AskQuestionUseCase {

    ChatExchange ask(ChatCommand command);

    record ChatCommand(
            Long guildId,
            Long raidId,
            Long palierId,
            String roleJoueur,
            String question
    ) {
    }
}
