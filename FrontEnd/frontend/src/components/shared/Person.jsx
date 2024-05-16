import { Link } from "react-router-dom";
import style from "./Person.module.css";
export function Person({ user }) {
  if (!user) {
    return (
      <Link to={"/profile/user1"} className={`${style.person} flex juc-between`}>
        <div className="flex align-center gap-1">
          <img src={`/user1.png`} alt="" />
          <div className="">
            <p className={style.username}>AK</p>
            <p className={style.subTitle}>user1</p>
          </div>
        </div>
        <button className="btn btn-light">follow</button>
      </Link>
    );
  }
  const { userName, firstName, lastName } = user;

  return (
    <Link to={`/profile/${userName}`} className={`${style.person} flex juc-between`}>
      <div className="flex align-center gap-1">
        <img src={`/${userName}.png`} alt="" />
        <div className="">
          <p className={style.username}>
            {firstName} {lastName}
          </p>
          <p className={style.subTitle}>@{userName}</p>
        </div>
      </div>
      <button className="btn btn-light">follow</button>
    </Link>
  );
}
