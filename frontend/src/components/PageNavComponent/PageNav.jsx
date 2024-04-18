import { Link, NavLink } from "react-router-dom";
import style from "./PageNav.module.css";
export default function PageNav() {
  return (
    <nav className={style.nav}>
      <Link className={style.logo} to={"/"}>
        <svg viewBox="0 0 24 24">
          <g>
            <path d="M18.244 2.25h3.308l-7.227 8.26 8.502 11.24H16.17l-5.214-6.817L4.99 21.75H1.68l7.73-8.835L1.254 2.25H8.08l4.713 6.231zm-  1.161 17.52h1.833L7.084 4.126H5.117z"></path>
          </g>
        </svg>
      </Link>
      <ul>
        <li>
          <NavLink to={"/"}>
            <svg viewBox="0 0 24 24">
              <g>
                <path d="M21.591 7.146L12.52 1.157c-.316-.21-.724-.21-1.04 0l-9.071 5.99c-.26.173-.409.456-.409.757v13.183c0 .502.418.913.929.913h6.638c.511 0 .929-.41.929-.913v-7.075h3.008v7.075c0 .502.418.913.929.913h6.639c.51 0 .928-.41.928-.913V7.904c0-.301-.158-.584-.408-.758zM20 20l-4.5.01.011-7.097c0-.502-.418-.913-.928-.913H9.44c-.511 0-.929.41-.929.913L8.5 20H4V8.773l8.011-5.342L20 8.764z"></path>
              </g>
            </svg>
            <span>Home</span>
          </NavLink>
        </li>
        <li>
          <NavLink to={"/profile"}>
            <svg viewBox="0 0 24 24">
              <g>
                <path d="M17.863 13.44c1.477 1.58 2.366 3.8 2.632 6.46l.11 1.1H3.395l.11-1.1c.266-2.66 1.155-4.88 2.632-6.46C7.627 11.85 9.648 11 12 11s4.373.85 5.863 2.44zM12 2C9.791 2 8 3.79 8 6s1.791 4 4 4 4-1.79 4-4-1.791-4-4-4z"></path>
              </g>
            </svg>
            <span>Profile</span>
          </NavLink>
        </li>
        <li>
          <NavLink to={"/"}>
            <svg viewBox="0 0 24 24">
              <g>
                <path d="M4 4.5C4 3.12 5.119 2 6.5 2h11C18.881 2 20 3.12 20 4.5v18.44l-8-5.71-8 5.71V4.5zM6.5 4c-.276 0-.5.22-.5.5v14.56l6-4.29 6 4.29V4.5c0-.28-.224-.5-.5-.5h-11z"></path>
              </g>
            </svg>
            <span>Bookmarks</span>
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}
