package com.example.dofusraid.progression.adapter.in.web.dto;

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
        String statut
) {
}
