import style from "./Profile.module.css";
import { ProfileHeader } from "./ProfileHeader";
import { ProfileHero } from "./ProfileHero";
export default function Profile() {
  return (
    <section className={style.profile}>
      <ProfileHeader />
      <ProfileHero />
      {/* <ProfileNav /> */}
    </section>
  );
}

