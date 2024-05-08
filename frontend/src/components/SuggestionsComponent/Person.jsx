import style from "./Suggestions.module.css";

export function Person() {
  return (
    <li className={`${style.person} flex juc-between`}>
      <div className="flex align-center gap-1">
        <img src="/src/assets/img-1.jpg" alt="" />
        <div className="">

          <p className={style.username}>AK</p>
          <p className={style.subTitle}>@AK</p>
        </div>
      </div>
      <button className="btn btn-light">follow</button>
    </li>
  );
}
