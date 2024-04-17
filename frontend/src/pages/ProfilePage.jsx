import PageNav from "../components/PageNavComponent/PageNav";
import Profile from "../components/ProfileComponent/Profile";
import Suggestions from "../components/SuggestionsComponent/Suggestions";

export default function ProfilePage() {
  return (
    <main className="main">
      <PageNav />
      <Profile />
      <Suggestions />
    </main>
  );
}
