import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/Home";
import ProfilePage from "./pages/ProfilePage";
import { Posts } from "./components/Posts/Posts";
import { Replies } from "./components/ProfileComponent/Replies";
import NotificationsPage from "./pages/NotificationsPage";
import BookmarksPage from "./pages/BookmarksPage";

// import PageNav from "./components/PageNav";
function App() {
  return (
    <>
      {/* <PageNav /> */}
      <BrowserRouter>
        <Routes>
          <Route index path="/" element={<Navigate to={"home"} />} />
          <Route path="home" element={<Home />}>
            <Route index element={<Navigate to={"for_you"} />} />
            <Route index path="for_you" element={<h1>For Your Page</h1>} />
            <Route path="following" element={<h1>Follwoing Page</h1>} />
          </Route>
          <Route path="profile" element={<ProfilePage />}>
            <Route index element={<Navigate to={"posts"} />} />
            <Route index path="posts" element={<Posts />} />
            <Route path="replies" element={<Replies />} />
          </Route>
          <Route path="bookmarks" element={<BookmarksPage />} />
          <Route path="notifications" element={<NotificationsPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
