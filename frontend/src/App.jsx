import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/Home";
import ProfilePage from "./pages/ProfilePage";

// import PageNav from "./components/PageNav";
function App() {
  return (
    <>
      {/* <PageNav /> */}
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="profile" element={<ProfilePage />}></Route>
          {/* <Route path="pricing" element={<Pricing />}></Route> */}
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
