import { useUser } from "../../contexts/UserContext";
import { Person } from "./../shared/Person";
import { Search } from "./Search";
import style from "./Suggestions.module.css";
export default function Suggestions() {
  const { users, curUser } = useUser();
  // console.log(users);
  return (
    <div className={`${style.suggestions} pl-15 pr-15`}>
      {/* Search Bar */}
      <Search />
      {/* Some Peapole to follow */}
      <ul className={style.personList}>
        {users.map((user) => {
          if (curUser === user) return;
          return <Person key={user.userName} user={user} />;
        })}
      </ul>
      <ul className={style.personList}>
        {users.map((user) => {
          if (curUser === user) return;
          return <Person key={user.userName} user={user} />;
        })}
      </ul>
    </div>
  );
}
