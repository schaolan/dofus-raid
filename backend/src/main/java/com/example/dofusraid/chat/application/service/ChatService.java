package com.example.dofusraid.chat.application.service;

import com.example.dofusraid.chat.application.port.in.AskQuestionUseCase;
import com.example.dofusraid.chat.application.port.out.PalierLookupPort;
import com.example.dofusraid.chat.application.port.out.SaveChatMessagePort;
import com.example.dofusraid.chat.domain.ChatExchange;
import com.example.dofusraid.chat.domain.ChatMessage;
import com.example.dofusraid.raid.domain.Palier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Service
public class ChatService implements AskQuestionUseCase {

    private final PalierLookupPort palierLookupPort;
    private final SaveChatMessagePort saveChatMessagePort;

    public ChatService(PalierLookupPort palierLookupPort, SaveChatMessagePort saveChatMessagePort) {
        this.palierLookupPort = palierLookupPort;
        this.saveChatMessagePort = saveChatMessagePort;
    }

    @Override
    public ChatExchange ask(ChatCommand command) {
        Palier palier = palierLookupPort.getPalier(command.palierId());

        String answer = buildAnswer(command.question(), command.roleJoueur(), palier);
        Instant now = Instant.now();

        saveChatMessagePort.save(ChatMessage.create(
                command.guildId(),
                command.raidId(),
                command.palierId(),
                command.roleJoueur(),
                command.question(),
                answer,
                now
        ));

        return new ChatExchange(command.question(), answer, command.raidId(), command.palierId(), now);
    }

    private String buildAnswer(String question, String roleJoueur, Palier palier) {
        String q = question.toLowerCase(Locale.ROOT);

        if (q.contains("objectif")) {
            return text(palier.objectif());
        }

        if (q.contains("piege") || q.contains("erreur")) {
            return "Pieges a eviter : " + list(palier.pieges()) + ".";
        }

        if (q.contains("suite") || q.contains("apres")) {
            return "Apres ce palier : " + text(palier.suite()) + ".";
        }

        if (q.contains("role") || q.contains("soigneur") || q.contains("tank") || q.contains("eniripsa")) {
            String role = (roleJoueur == null || roleJoueur.isBlank()) ? "ton role" : roleJoueur;
            return "Pour " + role + ", les roles concernes sont : " + list(palier.roles())
                    + ". Actions utiles : " + list(palier.actions()) + ".";
        }

        return "Resume : " + text(palier.resume())
                + ". Objectif : " + text(palier.objectif())
                + ". Actions : " + list(palier.actions())
                + ". Conseil : " + text(palier.conseil()) + ".";
    }

    private String text(String value) {
        return (value == null || value.isBlank()) ? "aucune information disponible" : value;
    }

    private String list(List<String> values) {
        return (values == null || values.isEmpty()) ? "aucune information disponible" : String.join(", ", values);
    }
}
