package com.example.dofusraid.chat.application.port.out;

import com.example.dofusraid.chat.domain.ChatMessage;

public interface SaveChatMessagePort {
    ChatMessage save(ChatMessage message);
}
