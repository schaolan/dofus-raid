package com.example.dofusraid.raid;

import com.example.dofusraid.raid.dto.PalierResponse;
import com.example.dofusraid.raid.dto.RaidDetailResponse;
import com.example.dofusraid.raid.dto.RaidSummaryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/raids")
public class RaidController {

    private final RaidService raidService;

    public RaidController(RaidService raidService) {
        this.raidService = raidService;
    }

    @GetMapping
    public List<RaidSummaryResponse> getRaids() {
        return raidService.getRaids();
    }

    @GetMapping("/{raidId}")
    public RaidDetailResponse getRaid(@PathVariable Long raidId) {
        return raidService.getRaid(raidId);
    }

    @GetMapping("/{raidId}/paliers")
    public List<PalierResponse> getPaliers(
            @PathVariable Long raidId,
            @RequestParam(required = false) Long guildId
    ) {
        return raidService.getPaliers(raidId, guildId);
    }
}
