import style from "./Profile.module.css";

export function ProfileHero() {
  return (
    <section className={style.hero}>
      <div className={style.backgroundCover}></div>
      <div className={style.userInfo}>
        <img
          src="/src/assets/img-1.jpg"
          alt=""
          className={style.profilePicture} />
        <h3 className={style.username}>Ahmed Kashkoush</h3>
        <p className={style.subTitle}>@AhmedKashkoush68</p>

        <p className={`${style.subTitle} ${style.date}`}>Joined April 2024 </p>

        <div className={style.connections}>
          <span>
            <strong>10</strong> Followers
          </span>
          <span>
            <strong>10</strong> Following
          </span>
        </div>
      </div>
    </section>
  );
}
