import { Link } from "@tanstack/react-router";
import { useRaids } from "../features/raids/hooks";

export function HomePage() {
  const { data, isLoading, error } = useRaids();

  if (isLoading) {
    return <div>Chargement des raids...</div>;
  }

  if (error) {
    return <div>Erreur de chargement des raids.</div>;
  }

  return (
    <div className="grid">
      {data?.map((raid) => (
        <div key={raid.id} className="card stack">
          <div className="header">
            <h2 style={{ margin: 0 }}>{raid.nom}</h2>
            <span className="badge">{raid.difficulte}</span>
          </div>

          <div>{raid.description}</div>
          <div>Niveau recommande : {raid.niveauRecommande}</div>
          <div>Duree estimee : {raid.dureeEstimeeMinutes} min</div>

          <div>
            <Link to="/raids/$raidId" params={{ raidId: String(raid.id) }} className="button">
              Ouvrir
            </Link>
          </div>
        </div>
      ))}
    </div>
  );
}
