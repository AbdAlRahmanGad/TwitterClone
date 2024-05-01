import style from './Search.module.css'
export function Search() {
  return <input className={style.search} type="search" placeholder="Search..." />;
}
