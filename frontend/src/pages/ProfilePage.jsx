/* eslint-disable react/prop-types */
import PageNav from "../components/PageNav";
import Suggestions from "../components/SuggestionsComponent/Suggestions";
import { ProfileHeader } from "../components/ProfileComponent/ProfileHeader";
import { ProfileHero } from "../components/ProfileComponent/ProfileHero";
import  ProfileNav  from "../components/ProfileComponent/ProfileNav/ProfileNav";
import { Outlet } from "react-router-dom";

import style from "./ProfilePage.module.css";

export default function ProfilePage() {
  return (
    <main className="main">
      <PageNav />
      <section className={`${style.profile} border-rl`}>
        <ProfileHeader />
        <ProfileHero />
        <ProfileNav />
        <Outlet />
      </section>
      <Suggestions />
    </main>
  );
}
