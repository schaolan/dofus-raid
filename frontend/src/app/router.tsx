import { createRootRoute, createRoute, createRouter, Outlet } from "@tanstack/react-router";
import { HomePage } from "../pages/HomePage";
import { RaidPage } from "../pages/RaidPage";
import { PalierPage } from "../pages/PalierPage";

const rootRoute = createRootRoute({
  component: () => (
    <div className="container">
      <div className="header" style={{ marginBottom: 24 }}>
        <h1>Dofus Raid MVP</h1>
        <span className="badge">Guilde 1</span>
      </div>
      <Outlet />
    </div>
  ),
});

const homeRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/",
  component: HomePage,
});

const raidRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/raids/$raidId",
  component: RaidPage,
});

const palierRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/raids/$raidId/paliers/$palierId",
  component: PalierPage,
});

const routeTree = rootRoute.addChildren([homeRoute, raidRoute, palierRoute]);

export const router = createRouter({
  routeTree,
});

declare module "@tanstack/react-router" {
  interface Register {
    router: typeof router;
  }
}
