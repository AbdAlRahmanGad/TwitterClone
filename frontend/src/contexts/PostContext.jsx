/* eslint-disable react/prop-types */
import { createContext, useContext, useEffect } from "react";
const temp = {
  userName: "user1",
  firstName: "User",
  lastName: "One",
  bio: "",
  profilePic: null,
  coverPic: null,
  dateJoined: "2024-05-11",
};

const postContext = createContext();

function PostProvider({ children }) {
  const [tweetsUpdated, setTweetsUpdated] = useState(false);
  const [tweets, setTweets] = useState([]);
  useEffect(() => {
    async function fetchAllTweets() {
      try {
        if (tweetsUpdated) return;
        const res = await fetch("/api/home/allTweets");

        // Check if the response is okay
        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }

        const data = await res.json();
        console.log(data);
        setTweetsUpdated(true);
      } catch (e) {
        console.error("Fetch error:", e);
      }
    }

    fetchAllTweets();
  }, [tweetsUpdated]);

  async function handleAddTweet(tweetObj) {
    try {
      const res = await fetch("/api/status/createTweet", {
        method: "POST",
        body: JSON.stringify({
          tweetObj,
        }),
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json",
        },
      });
      console.log(res);
    } catch (error) {
      console.error("add Tweet error:", error);
    }
  }
  function handleUpdateTweet(tweetObj) {
    throw new Error("not Implemented");
  }
  function handleDeleteTweet(tweetId) {
    throw new Error("not Implemented");
  }
  return (
    <postContext.Provider
      value={{
        tweets,
        handleAddTweet,
      }}
    >
      {children}
    </postContext.Provider>
  );
}

const usePosts = () => useContext(postContext);

export { usePosts, PostProvider };
