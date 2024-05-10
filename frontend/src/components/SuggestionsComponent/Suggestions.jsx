import { Person } from "./../shared/Person";
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
