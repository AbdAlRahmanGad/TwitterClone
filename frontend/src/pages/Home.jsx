import { NavLink, Outlet } from "react-router-dom";
import PageNav from "../components/PageNav";
import { AppNav } from "../components/shared/AppNav";
import Suggestions from "../components/SuggestionsComponent/Suggestions";
import { Posts } from "../components/shared/Posts";
import PageWhatsHappening from "../components/PageWhat'sHappening/PageWhatsHappening";

export default function Home() {
 
  return (
    <main className="main">
      <PageNav />

      <div className="flex-1 border-rl pl-15 pr-15">
        <AppNav>
          <NavLink to={"for_you"} className="tab">
            For You
          </NavLink>
          <NavLink to={"following"} className="tab">
            Following
          </NavLink>
        </AppNav>
        <Outlet />
        <Posts />
      </div>
      <div
        className="sidebar"
        style={{ display: "flex", flexDirection: "column" }}
      >
        <Suggestions />
        <div style={{ marginLeft: "15px" }}>
          <PageWhatsHappening />
        </div>
      </div>
    </main>
  );
}
