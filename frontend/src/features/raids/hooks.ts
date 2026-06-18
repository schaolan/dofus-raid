import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getProgression, getRaid, getRaids, startRaid, validatePalier } from "./api";

export function useRaids() {
  return useQuery({
    queryKey: ["raids"],
    queryFn: getRaids,
  });
}

export function useRaid(raidId: number) {
  return useQuery({
    queryKey: ["raid", raidId],
    queryFn: () => getRaid(raidId),
  });
}

export function useProgression(guildId: number, raidId: number) {
  return useQuery({
    queryKey: ["progression", guildId, raidId],
    queryFn: () => getProgression(guildId, raidId),
    retry: false,
  });
}

export function useStartRaid() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ guildId, raidId }: { guildId: number; raidId: number }) =>
      startRaid(guildId, raidId),
    onSuccess: (_data, variables) => {
      queryClient.invalidateQueries({
        queryKey: ["progression", variables.guildId, variables.raidId],
      });
    },
  });
}

export function useValidatePalier() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      guildId,
      raidId,
      palierId,
    }: {
      guildId: number;
      raidId: number;
      palierId: number;
    }) => validatePalier(guildId, raidId, palierId),
    onSuccess: (_data, variables) => {
      queryClient.invalidateQueries({
        queryKey: ["progression", variables.guildId, variables.raidId],
      });
    },
  });
}
