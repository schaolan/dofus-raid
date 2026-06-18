package com.example.dofusraid.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guild_id", nullable = false)
    private Long guildId;

    @Column(name = "raid_id", nullable = false)
    private Long raidId;

    @Column(name = "palier_id", nullable = false)
    private Long palierId;

    @Column(name = "role_joueur", length = 50)
    private String roleJoueur;

    @Column(nullable = false, columnDefinition = "text")
    private String question;

    @Column(nullable = false, columnDefinition = "text")
    private String reponse;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public Long getGuildId() {
        return guildId;
    }

    public Long getRaidId() {
        return raidId;
    }

    public Long getPalierId() {
        return palierId;
    }

    public String getRoleJoueur() {
        return roleJoueur;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponse() {
        return reponse;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public void setRaidId(Long raidId) {
        this.raidId = raidId;
    }

    public void setPalierId(Long palierId) {
        this.palierId = palierId;
    }

    public void setRoleJoueur(String roleJoueur) {
        this.roleJoueur = roleJoueur;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
