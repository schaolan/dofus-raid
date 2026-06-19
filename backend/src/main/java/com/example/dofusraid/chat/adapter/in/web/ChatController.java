package com.example.dofusraid.chat.adapter.in.web;

import com.example.dofusraid.chat.adapter.in.web.dto.ChatRequest;
import com.example.dofusraid.chat.adapter.in.web.dto.ChatResponse;
import com.example.dofusraid.chat.application.port.in.AskQuestionUseCase;
import com.example.dofusraid.chat.application.port.in.AskQuestionUseCase.ChatCommand;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final AskQuestionUseCase askQuestionUseCase;

    public ChatController(AskQuestionUseCase askQuestionUseCase) {
        this.askQuestionUseCase = askQuestionUseCase;
    }

    @PostMapping("/questions")
    public ChatResponse ask(@Valid @RequestBody ChatRequest request) {
        ChatCommand command = new ChatCommand(
                request.guildId(),
                request.raidId(),
                request.palierId(),
                request.roleJoueur(),
                request.question()
        );
        return ChatResponse.from(askQuestionUseCase.ask(command));
    }
}
