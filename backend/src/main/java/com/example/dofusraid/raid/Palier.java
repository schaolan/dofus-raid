package com.example.dofusraid.raid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paliers")
public class Palier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "raid_id")
    private Raid raid;

    @Column(nullable = false)
    private Integer ordre;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(columnDefinition = "text")
    private String resume;

    @Column(columnDefinition = "text")
    private String objectif;

    @Column(columnDefinition = "text")
    private String actions;

    @Column(columnDefinition = "text")
    private String roles;

    @Column(columnDefinition = "text")
    private String pieges;

    @Column(name = "conditions_reussite", columnDefinition = "text")
    private String conditionsReussite;

    @Column(columnDefinition = "text")
    private String suite;

    @Column(columnDefinition = "text")
    private String conseil;

    @Column(columnDefinition = "text")
    private String faq;

    @Column(nullable = false)
    private boolean actif;

    public Long getId() {
        return id;
    }

    public Raid getRaid() {
        return raid;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public String getResume() {
        return resume;
    }

    public String getObjectif() {
        return objectif;
    }

    public String getActions() {
        return actions;
    }

    public String getRoles() {
        return roles;
    }

    public String getPieges() {
        return pieges;
    }

    public String getConditionsReussite() {
        return conditionsReussite;
    }

    public String getSuite() {
        return suite;
    }

    public String getConseil() {
        return conseil;
    }

    public String getFaq() {
        return faq;
    }

    public boolean isActif() {
        return actif;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setPieges(String pieges) {
        this.pieges = pieges;
    }

    public void setConditionsReussite(String conditionsReussite) {
        this.conditionsReussite = conditionsReussite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public void setConseil(String conseil) {
        this.conseil = conseil;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
