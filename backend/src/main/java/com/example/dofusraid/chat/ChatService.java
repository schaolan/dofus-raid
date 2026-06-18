package com.example.dofusraid.chat;

import com.example.dofusraid.common.exception.NotFoundException;
import com.example.dofusraid.raid.Palier;
import com.example.dofusraid.raid.PalierRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Locale;
import java.util.Map;

@Service
public class ChatService {

    private final PalierRepository palierRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(
            PalierRepository palierRepository,
            ChatMessageRepository chatMessageRepository
    ) {
        this.palierRepository = palierRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatResponse ask(ChatRequest request) {
        Palier palier = palierRepository.findById(request.palierId())
                .orElseThrow(() -> new NotFoundException("Palier introuvable"));

        String answer = buildAnswer(request.question(), request.roleJoueur(), palier);
        Instant now = Instant.now();

        ChatMessage message = new ChatMessage();
        message.setGuildId(request.guildId());
        message.setRaidId(request.raidId());
        message.setPalierId(request.palierId());
        message.setRoleJoueur(request.roleJoueur());
        message.setQuestion(request.question());
        message.setReponse(answer);
        message.setCreatedAt(now);
        chatMessageRepository.save(message);

        return new ChatResponse(
                request.question(),
                answer,
                Map.of(
                        "raidId", request.raidId(),
                        "palierId", request.palierId()
                ),
                now
        );
    }

    private String buildAnswer(String question, String roleJoueur, Palier palier) {
        String q = question.toLowerCase(Locale.ROOT);

        if (q.contains("objectif")) {
            return safe(palier.getObjectif());
        }

        if (q.contains("piege") || q.contains("erreur")) {
            return "Pieges a eviter : " + safe(palier.getPieges()) + ".";
        }

        if (q.contains("suite") || q.contains("apres")) {
            return "Apres ce palier : " + safe(palier.getSuite()) + ".";
        }

        if (q.contains("role") || q.contains("soigneur") || q.contains("tank") || q.contains("eniripsa")) {
            String role = (roleJoueur == null || roleJoueur.isBlank()) ? "ton role" : roleJoueur;
            return "Pour " + role + ", les roles concernes sont : " + safe(palier.getRoles())
                    + ". Actions utiles : " + safe(palier.getActions()) + ".";
        }

        return "Resume : " + safe(palier.getResume())
                + ". Objectif : " + safe(palier.getObjectif())
                + ". Actions : " + safe(palier.getActions())
                + ". Conseil : " + safe(palier.getConseil()) + ".";
    }

    private String safe(String value) {
        if (value == null || value.isBlank()) {
            return "aucune information disponible";
        }
        return value.replace("|", ", ");
    }
}
