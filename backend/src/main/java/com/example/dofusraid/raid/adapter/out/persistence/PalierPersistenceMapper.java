package com.example.dofusraid.raid.adapter.out.persistence;

import com.example.dofusraid.raid.domain.Palier;

import java.util.Arrays;
import java.util.List;

/**
 * Mappe l'entite JPA vers le domaine. L'encodage "a|b|c" est un detail de persistance :
 * il est decode ici en List<String> pour le domaine.
 */
final class PalierPersistenceMapper {

    private PalierPersistenceMapper() {
    }

    static Palier toDomain(PalierJpaEntity entity) {
        return new Palier(
                entity.getId(),
                entity.getRaidId(),
                entity.getOrdre(),
                entity.getCode(),
                entity.getNom(),
                entity.getResume(),
                entity.getObjectif(),
                splitPipe(entity.getActions()),
                splitPipe(entity.getRoles()),
                splitPipe(entity.getPieges()),
                entity.getConditionsReussite(),
                entity.getSuite(),
                entity.getConseil(),
                entity.getFaq(),
                entity.isActif()
        );
    }

    private static List<String> splitPipe(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split("\\|"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}
