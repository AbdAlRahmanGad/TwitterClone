import style from "./Person.module.css";
export function Person({ user }) {
  if (!user) {
    return (
      <li className={`${style.person} flex juc-between`}>
        <div className="flex align-center gap-1">
          <img src={`/user1.png`} alt="" />
          <div className="">
            <p className={style.username}>AK</p>
            <p className={style.subTitle}>user1</p>
          </div>
        </div>
        <button className="btn btn-light">follow</button>
      </li>
    );
  }
  const { userName, firstName, lastName } = user;

  return (
    <li className={`${style.person} flex juc-between`}>
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
    </li>
  );
}
