import PageNav from "../components/PageNavComponent/PageNav";
import Suggestions from "../components/SuggestionsComponent/Suggestions";

export default function Home() {
  return (
    <main className="main">
      <PageNav />
      <h1>Hello world</h1>
      <Suggestions />
    </main>
  );
}
