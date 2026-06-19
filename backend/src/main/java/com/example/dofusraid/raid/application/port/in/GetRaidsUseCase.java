package com.example.dofusraid.raid.application.port.in;

import com.example.dofusraid.raid.domain.Raid;

import java.util.List;

public interface GetRaidsUseCase {
    List<Raid> getRaids();
}
