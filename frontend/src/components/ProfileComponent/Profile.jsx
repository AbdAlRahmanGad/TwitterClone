import style from "./Profile.module.css";
import { ProfileHeader } from "./ProfileHeader";
import { ProfileHero } from "./ProfileHero";
import ProfileNav from "./ProfileNav/ProfileNav";
  return (
    <section className={style.profile}>
      <ProfileHeader />
      <ProfileHero />
      <ProfileNav />
    </section>
  );
}

