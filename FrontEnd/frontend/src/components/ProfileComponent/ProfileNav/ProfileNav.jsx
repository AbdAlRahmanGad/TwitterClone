import { NavLink } from "react-router-dom";

import { AppNav } from "./../../shared/AppNav";
export default function ProfileNav() {
  return (
    <AppNav classes="mt-2">
      <NavLink className="tab" to={"posts"}>
        posts
      </NavLink>
      <NavLink className="tab" to={"replies"}>
        Replies
      </NavLink>
    </AppNav>
  );
}

