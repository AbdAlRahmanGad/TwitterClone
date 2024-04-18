/* eslint-disable react/prop-types */
import PageNav from "../components/PageNavComponent/PageNav";
import Profile from "../components/ProfileComponent/Profile";
import Suggestions from "../components/SuggestionsComponent/Suggestions";

export default function ProfilePage({ showReplies }) {
  return (
    <main className="main">
      <PageNav />
      <Profile showReplies={showReplies} />
      <Suggestions />
    </main>
  );
}
