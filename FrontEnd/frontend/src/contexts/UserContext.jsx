import { createContext, useContext, useEffect, useState } from "react";

const userContext = createContext();

function UserProvider({ children }) {
  const [users, setUsers] = useState([]);
  const [curUser, setCurUser] = useState(null);
  useEffect(() => {
    async function fetchUserData() {
      try {
        const res = await fetch("/api/allUsers");
        if (!res.ok) throw new Error("Response Failed", res.status);

        const data = await res.json();
        // console.log(data);
        setUsers(data);
        setCurUser(data[0]);
        console.log(data[0]);
      } catch (error) {
        console.error("Fetch Users Error:", error);
      }
    }
    fetchUserData();
  }, []);
  return (
    <userContext.Provider
      value={{
        users,
        curUser,
      }}
    >
      {children}
    </userContext.Provider>
  );
}

const useUser = () => useContext(userContext);
export { useUser, UserProvider };
