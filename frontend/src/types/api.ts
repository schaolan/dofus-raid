export type Difficulty = "EASY" | "MEDIUM" | "HARD";
export type PalierStatus = "LOCKED" | "UNLOCKED" | "VALIDATED";
export type RaidStatus = "NOT_STARTED" | "IN_PROGRESS" | "COMPLETED";

export type RaidSummary = {
  id: number;
  slug: string;
  nom: string;
  description: string;
  niveauRecommande: string;
  dureeEstimeeMinutes: number;
  difficulte: Difficulty;
  actif: boolean;
};

export type RaidDetail = RaidSummary;

export type Palier = {
  id: number;
  ordre: number;
  nom: string;
  resume: string;
  objectif: string;
  actions: string[];
  roles: string[];
  pieges: string[];
  conditionsReussite: string;
  suite: string;
  conseil: string;
  statut: PalierStatus;
};

export type Progression = {
  guildId: number;
  raidId: number;
  statut: RaidStatus;
  palierActuelOrdre: number | null;
  paliers: Palier[];
};

export type ChatResponse = {
  question: string;
  answer: string;
  context: {
    raidId: number;
    palierId: number;
  };
  createdAt: string;
};
