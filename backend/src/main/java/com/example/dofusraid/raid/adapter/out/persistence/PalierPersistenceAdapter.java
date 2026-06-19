package com.example.dofusraid.raid.adapter.out.persistence;

import com.example.dofusraid.raid.application.port.out.LoadPalierPort;
import com.example.dofusraid.raid.domain.Palier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PalierPersistenceAdapter implements LoadPalierPort {

    private final PalierJpaRepository repository;

    public PalierPersistenceAdapter(PalierJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Palier> findByRaidIdOrdered(Long raidId) {
        return repository.findByRaidIdOrderByOrdreAsc(raidId).stream()
                .map(PalierPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Palier> findByRaidIdAndOrdre(Long raidId, int ordre) {
        return repository.findByRaidIdAndOrdre(raidId, ordre).map(PalierPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Palier> findById(Long palierId) {
        return repository.findById(palierId).map(PalierPersistenceMapper::toDomain);
    }
}
