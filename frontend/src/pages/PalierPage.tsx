import { Link, useParams } from "@tanstack/react-router";
import { useMutation } from "@tanstack/react-query";
import { useMemo, useState } from "react";
import type { PalierStatus } from "../types/api";
import { askQuestion } from "../features/chat/api";
import { useProgression, useValidatePalier } from "../features/raids/hooks";

const GUILD_ID = 1;

function badgeClass(statut: PalierStatus) {
  if (statut === "LOCKED") return "badge locked";
  if (statut === "UNLOCKED") return "badge unlocked";
  return "badge validated";
}

export function PalierPage() {
  const { raidId, palierId } = useParams({ strict: false });

  const raidIdNum = Number(raidId);
  const palierIdNum = Number(palierId);

  const { data: progression, isLoading } = useProgression(GUILD_ID, raidIdNum);
  const validateMutation = useValidatePalier();

  const palier = useMemo(
    () => progression?.paliers.find((item) => item.id === palierIdNum),
    [progression, palierIdNum]
  );

  const [question, setQuestion] = useState("");
  const [roleJoueur, setRoleJoueur] = useState("eniripsa");

  const chatMutation = useMutation({
    mutationFn: askQuestion,
  });

  if (isLoading) {
    return <div>Chargement du palier...</div>;
  }

  if (!palier) {
    return <div>Palier introuvable.</div>;
  }

  return (
    <div className="stack">
      <Link
        to="/raids/$raidId"
        params={{ raidId: String(raidIdNum) }}
        className="button secondary"
        style={{ width: "fit-content" }}
      >
        Retour au raid
      </Link>

      <div className="card stack">
        <div className="header">
          <h2 style={{ margin: 0 }}>
            {palier.ordre}. {palier.nom}
          </h2>
          <span className={badgeClass(palier.statut)}>{palier.statut}</span>
        </div>

        <div>
          <strong>Resume :</strong> {palier.resume}
        </div>

        <div>
          <strong>Objectif :</strong> {palier.objectif}
        </div>
      </div>

      <div className="grid-2">
        <div className="card stack">
          <h3 style={{ margin: 0 }}>Actions a faire</h3>
          <ul className="list">
            {palier.actions.map((item) => (
              <li key={item}>{item}</li>
            ))}
          </ul>

          <h3 style={{ margin: 0 }}>Roles concernes</h3>
          <ul className="list">
            {palier.roles.map((item) => (
              <li key={item}>{item}</li>
            ))}
          </ul>
        </div>

        <div className="card stack">
          <h3 style={{ margin: 0 }}>Attention</h3>
          <ul className="list">
            {palier.pieges.map((item) => (
              <li key={item}>{item}</li>
            ))}
          </ul>

          <div>
            <strong>Condition de reussite :</strong> {palier.conditionsReussite}
          </div>

          <div>
            <strong>Suite :</strong> {palier.suite}
          </div>

          <div>
            <strong>Conseil :</strong> {palier.conseil}
          </div>
        </div>
      </div>

      <div className="card stack">
        <h3 style={{ margin: 0 }}>Chat contextuel</h3>

        <label>
          Role joueur
          <input
            className="input"
            value={roleJoueur}
            onChange={(e) => setRoleJoueur(e.target.value)}
          />
        </label>

        <label>
          Question
          <textarea
            className="textarea"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            placeholder="Ex: Que doit faire le soigneur sur cette etape ?"
          />
        </label>

        <div>
          <button
            className="button"
            onClick={() =>
              chatMutation.mutate({
                guildId: GUILD_ID,
                raidId: raidIdNum,
                palierId: palierIdNum,
                roleJoueur,
                question,
              })
            }
            disabled={chatMutation.isPending || question.trim().length === 0}
          >
            Poser la question
          </button>
        </div>

        {chatMutation.data && (
          <div className="card">
            <strong>Reponse :</strong>
            <div style={{ marginTop: 8 }}>{chatMutation.data.answer}</div>
          </div>
        )}
      </div>

      <div>
        <button
          className="button"
          disabled={palier.statut !== "UNLOCKED" || validateMutation.isPending}
          onClick={() =>
            validateMutation.mutate({
              guildId: GUILD_ID,
              raidId: raidIdNum,
              palierId: palierIdNum,
            })
          }
        >
          Valider ce palier
        </button>
      </div>
    </div>
  );
}
