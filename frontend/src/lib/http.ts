// URL de base de l'API cote navigateur :
//   - en prod : chaine vide => appels relatifs same-origin, nginx proxifie /api/
//     vers l'ingress interne du backend (pas de CORS, pas d'URL figee au build) ;
//   - en dev  : http://localhost:8080 par defaut, surchargeable via .env.
//
// La valeur arrive d'un build arg Docker : on la normalise (guillemets residuels,
// slash final). Une valeur mal quotee comme '""' produirait des URLs du type
// /""/api/raids, servies en 200 + index.html par le fallback SPA de nginx.
const rawApiUrl =
  import.meta.env.VITE_API_URL ?? (import.meta.env.DEV ? "http://localhost:8080" : "");

const API_URL = rawApiUrl
  .trim()
  .replace(/^(["'])(.*)\1$/, "$2")
  .replace(/\/+$/, "");

export async function apiFetch<T>(path: string, init?: RequestInit): Promise<T> {
  const url = `${API_URL}${path}`;
  const response = await fetch(url, {
    headers: {
      "Content-Type": "application/json",
    },
    ...init,
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || "API error");
  }

  // Une reponse non-JSON en 200 signifie que l'appel n'a pas atteint l'API
  // (VITE_API_URL incorrecte, proxy /api/ absent) et a ete servi par le fallback
  // SPA. Sans ce garde-fou, l'echec se manifeste par un obscur SyntaxError.
  const contentType = response.headers.get("content-type") ?? "";
  if (!contentType.includes("application/json")) {
    throw new Error(
      `Reponse non-JSON depuis ${url} (content-type: ${contentType || "absent"}) : ` +
        `l'appel n'a pas atteint l'API.`
    );
  }

  return response.json() as Promise<T>;
}
