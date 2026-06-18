import { apiFetch } from "../../lib/http";
import type { Progression, RaidDetail, RaidSummary } from "../../types/api";

export function getRaids() {
  return apiFetch<RaidSummary[]>("/api/raids");
}

export function getRaid(raidId: number) {
  return apiFetch<RaidDetail>(`/api/raids/${raidId}`);
}

export function startRaid(guildId: number, raidId: number) {
  return apiFetch<Progression>(`/api/guilds/${guildId}/raids/${raidId}/start`, {
    method: "POST",
  });
}

export function getProgression(guildId: number, raidId: number) {
  return apiFetch<Progression>(`/api/guilds/${guildId}/raids/${raidId}/progression`);
}

export function validatePalier(guildId: number, raidId: number, palierId: number) {
  return apiFetch<Progression>(
    `/api/guilds/${guildId}/raids/${raidId}/paliers/${palierId}/validate`,
    {
      method: "POST",
    }
  );
}
