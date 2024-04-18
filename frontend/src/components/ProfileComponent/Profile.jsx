/* eslint-disable react/prop-types */
import { Posts } from "../Posts/Posts";
import style from "./Profile.module.css";
import { ProfileHeader } from "./ProfileHeader";
import { ProfileHero } from "./ProfileHero";
import ProfileNav from "./ProfileNav/ProfileNav";
import { Replies } from "./Replies";
export default function Profile({ showReplies = false }) {
  console.log(showReplies);
  return (
    <section className={style.profile}>
      <ProfileHeader />
      <ProfileHero />
      <ProfileNav />
      {showReplies && <Replies /> }
      {!showReplies && <Posts />}
    </section>
  );
}


