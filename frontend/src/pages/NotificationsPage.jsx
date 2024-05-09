import { NavLink, Outlet } from "react-router-dom";
import PageNav from "../components/PageNavComponent/PageNav";
import PageWhatsHappening from "../components/PageWhat'sHappening/PageWhatsHappening";
import { AppNav } from "../components/ProfileComponent/ProfileNav/AppNav";
import Suggestions from "../components/SuggestionsComponent/Suggestions";
import { Posts } from "../components/Posts/Posts";
import NotificationsComponent from "../components/NotificationComponent/NotificationComponent";

export default function NotificationsPage() {
  return (
    <main className="main">
      <PageNav />
      <NotificationsComponent />
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
