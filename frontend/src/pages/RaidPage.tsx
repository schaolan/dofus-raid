import { Link, useParams } from "@tanstack/react-router";
import type { PalierStatus } from "../types/api";
import { useProgression, useRaid, useStartRaid } from "../features/raids/hooks";

const GUILD_ID = 1;

function badgeClass(statut: PalierStatus) {
  if (statut === "LOCKED") return "badge locked";
  if (statut === "UNLOCKED") return "badge unlocked";
  return "badge validated";
}

export function RaidPage() {
  const { raidId } = useParams({ strict: false });
  const raidIdNum = Number(raidId);

  const raidQuery = useRaid(raidIdNum);
  const progressionQuery = useProgression(GUILD_ID, raidIdNum);
  const startRaidMutation = useStartRaid();

  const raid = raidQuery.data;
  const progression = progressionQuery.data;

  return (
    <div className="stack">
      <Link to="/" className="button secondary" style={{ width: "fit-content" }}>
        Retour
      </Link>

      {raid && (
        <div className="card stack">
          <h2 style={{ margin: 0 }}>{raid.nom}</h2>
          <div>{raid.description}</div>
          <div>Difficulte : {raid.difficulte}</div>
          <div>Niveau recommande : {raid.niveauRecommande}</div>
        </div>
      )}

      {!progression && (
        <div className="card stack">
          <div>Aucune progression trouvee pour cette guilde.</div>
          <button
            className="button"
            onClick={() => startRaidMutation.mutate({ guildId: GUILD_ID, raidId: raidIdNum })}
            disabled={startRaidMutation.isPending}
          >
            Demarrer le raid
          </button>
        </div>
      )}

      {progression && (
        <div className="stack">
          <div className="card">
            <strong>Statut :</strong> {progression.statut}
          </div>

          <div className="grid">
            {progression.paliers.map((palier) => (
              <div key={palier.id} className="card stack">
                <div className="header">
                  <strong>
                    {palier.ordre}. {palier.nom}
                  </strong>
                  <span className={badgeClass(palier.statut)}>{palier.statut}</span>
                </div>

                <div>{palier.resume}</div>

                {palier.statut !== "LOCKED" ? (
                  <Link
                    to="/raids/$raidId/paliers/$palierId"
                    params={{ raidId: String(raidIdNum), palierId: String(palier.id) }}
                    className="button"
                  >
                    Voir le palier
                  </Link>
                ) : (
                  <button className="button secondary" disabled>
                    Verrouille
                  </button>
                )}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
