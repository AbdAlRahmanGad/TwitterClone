import { Search } from "./Search";
import style from "./Suggestions.module.css";
export default function Suggestions() {
  return (
    <div className={`${style.suggestions} pl-15 pr-15`}>
       {/* Search Bar */}
      <Search />
       {/* Some Peapole to follow */}
      <ul className={style.personList}>
        <Person />
        <Person />
        <Person />
        <Person />
      </ul>
      <ul className={style.personList}>
        <Person />
        <Person />
        <Person />
        <Person />
      </ul>
    </div>
  );
}
function Person() {
  return (
    <li className={`${style.person} flex`}>
      <div>
        <img src="/src/assets/img-1.jpg" alt="" />
        <div className="flex align-center gap-1">

        <p className={style.username}>AK</p>
        <p className={style.subTitle}>@AK</p>
        </div>
      </div>
      <button className="btn btn-light">follow</button>
    </li>
  );
}

