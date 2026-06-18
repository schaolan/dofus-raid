package com.example.dofusraid.chat.adapter.out.persistence;

import com.example.dofusraid.chat.application.port.out.SaveChatMessagePort;
import com.example.dofusraid.chat.domain.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessagePersistenceAdapter implements SaveChatMessagePort {

    private final ChatMessageJpaRepository repository;

    public ChatMessagePersistenceAdapter(ChatMessageJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        ChatMessageJpaEntity entity = new ChatMessageJpaEntity(
                message.id(),
                message.guildId(),
                message.raidId(),
                message.palierId(),
                message.roleJoueur(),
                message.question(),
                message.reponse(),
                message.createdAt()
        );
        ChatMessageJpaEntity saved = repository.save(entity);
        return new ChatMessage(
                saved.getId(),
                saved.getGuildId(),
                saved.getRaidId(),
                saved.getPalierId(),
                saved.getRoleJoueur(),
                saved.getQuestion(),
                saved.getReponse(),
                saved.getCreatedAt()
        );
    }
}
