import { Link } from "react-router-dom";
import style from "./../../pages/ProfilePage.module.css";
import style2 from "./ProfileHero.module.css";

export function ProfileHeader() {
  return (
    <header className={`${style.profileHeader} flex align-center gap-2`}>
      <Link to={"/"}>
        <svg
          viewBox="0 0 24 24"
          className="r-4qtqp9 r-yyyyoo r-dnmrzs r-bnwqim r-1plcrui r-lrvibr r-18yzcnr r-yc9v9c"
        >
          <g>
            <path d="M7.414 13l5.043 5.04-1.414 1.42L3.586 12l7.457-7.46 1.414 1.42L7.414 11H21v2H7.414z"></path>
          </g>
        </svg>
      </Link>
      <div>
        <h3 className={style.username}>Ahmed Kashkoush</h3>
        <p className={`${style2.subTitle} mt-10 mb-10`}>0 posts</p>
      </div>
    </header>
  );
}
