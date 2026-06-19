package com.example.dofusraid.raid.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "paliers")
public class PalierJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "raid_id", nullable = false)
    private Long raidId;

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

    protected PalierJpaEntity() {
    }

    public Long getId() {
        return id;
    }

    public Long getRaidId() {
        return raidId;
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
}
