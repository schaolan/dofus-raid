package com.example.dofusraid.chat.application.port.out;

import com.example.dofusraid.raid.domain.Palier;

public interface PalierLookupPort {
    Palier getPalier(Long palierId);
}
