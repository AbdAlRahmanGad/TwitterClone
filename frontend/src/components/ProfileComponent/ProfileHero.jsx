import { useParams } from "react-router-dom";
import { formatDate } from "../../functions/formatDate";
import style from "./ProfileHero.module.css";
import { useUser } from "../../contexts/UserContext";
export function ProfileHero() {
  const { id } = useParams();
  const { users } = useUser();
  console.log(id);
  const user = users.find((user) => user.userName === id);

  if (!user || id === "user1") {
    return (
      <section className={style.hero}>
        <div className={style.backgroundCover}></div>
        <div className={style.userInfo}>
          <img
            src="/src/assets/img-1.jpg"
            alt=""
            className={style.profilePicture}
          />
          <h3 className={`${style.username} mb-10 mt-10`}>Ahmed Kashkoush</h3>
          <p className={style.subTitle}>@AhmedKashkoush68</p>

          <p className={`${style.subTitle} ${style.date} mt-10 mb-10`}>
            Joined April 2024{" "}
          </p>

          <div className={`${style.connections}`}>
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
  const { userName, firstName, lastName, dateJoined, bio } = user;
  return (
    <section className={style.hero}>
      <div className={style.backgroundCover}></div>
      <div className={style.userInfo}>
        <img src={`/${userName}.png`} alt="" className={style.profilePicture} />
        <h3 className={`${style.username} mb-10 mt-10`}>
          {firstName} {lastName}
        </h3>
        <p className={style.subTitle}>@{userName}</p>
        <p className={`${style.subTitle} mt-1`}>{bio}</p>

        <p className={`${style.subTitle} ${style.date} mt-10 mb-10`}>
          Joined {formatDate(dateJoined)}{" "}
        </p>

        <div className={`${style.connections}`}>
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
