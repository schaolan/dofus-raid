package com.example.dofusraid.raid.dto;

import com.example.dofusraid.progression.PalierStatus;

import java.util.List;

public record PalierResponse(
        Long id,
        Integer ordre,
        String nom,
        String resume,
        String objectif,
        List<String> actions,
        List<String> roles,
        List<String> pieges,
        String conditionsReussite,
        String suite,
        String conseil,
        PalierStatus statut
) {
}
