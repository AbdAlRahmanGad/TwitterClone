import style from "./Suggestions.module.css";
export default function Suggestions() {
  return (
    <div className={style.suggestions}>
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
    <li className={style.person}>
      <div>
        <img src="/src/assets/img-1.jpg" alt="" />
        <div>

        <p className={style.username}>AK</p>
        <p className={style.subTitle}>@AK</p>
        </div>
      </div>
      <button className={style.btn}>follow</button>
    </li>
  );
}
function Search() {
  return <input className={style.search} type="search" placeholder="Search..." />;
}
