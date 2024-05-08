import { NavLink, Outlet } from "react-router-dom";
import PageNav from "../components/PageNavComponent/PageNav";
import { AppNav } from "../components/ProfileComponent/ProfileNav/AppNav";
import Suggestions from "../components/SuggestionsComponent/Suggestions";
import { Posts } from "../components/Posts/Posts";
// import style from './../components/ProfileComponent/ProfileNav/ProfileNav.jsx';
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
      <Suggestions />
    </main>
  );
}
