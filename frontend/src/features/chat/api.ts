import { apiFetch } from "../../lib/http";
import type { ChatResponse } from "../../types/api";

export function askQuestion(payload: {
  guildId: number;
  raidId: number;
  palierId: number;
  roleJoueur?: string;
  question: string;
}) {
  return apiFetch<ChatResponse>("/api/chat/questions", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}
