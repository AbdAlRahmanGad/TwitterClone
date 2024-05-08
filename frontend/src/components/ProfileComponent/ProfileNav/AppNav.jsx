/* eslint-disable react/prop-types */
import style from "./ProfileNav.module.css";

export function AppNav({ children, classes }) {
  return <nav className={`${style.nav} main-header ${classes}`}>{children}</nav>;
}
