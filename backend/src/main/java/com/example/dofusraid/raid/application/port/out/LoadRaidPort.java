package com.example.dofusraid.raid.application.port.out;

import com.example.dofusraid.raid.domain.Raid;

import java.util.List;
import java.util.Optional;

public interface LoadRaidPort {
    List<Raid> findAll();

    Optional<Raid> findById(Long raidId);
}
