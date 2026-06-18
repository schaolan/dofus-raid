package com.example.dofusraid.raid.adapter.out.persistence;

import com.example.dofusraid.raid.application.port.out.LoadRaidPort;
import com.example.dofusraid.raid.domain.Raid;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RaidPersistenceAdapter implements LoadRaidPort {

    private final RaidJpaRepository repository;

    public RaidPersistenceAdapter(RaidJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Raid> findAll() {
        return repository.findAll().stream()
                .map(RaidPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Raid> findById(Long raidId) {
        return repository.findById(raidId).map(RaidPersistenceMapper::toDomain);
    }
}
