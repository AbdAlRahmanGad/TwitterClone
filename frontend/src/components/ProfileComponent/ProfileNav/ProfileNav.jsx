import { NavLink } from "react-router-dom";
import style from "./ProfileNav.module.css";
export default function ProfileNav() {
  return (
    <nav className={style.nav}>
      <NavLink className={style.tab} to={"/profile"}>posts</NavLink>
      <NavLink className={style.tab} to={"/profile_with_replies"}>Replies</NavLink>
    </nav>
  );
}
