/* eslint-disable react/prop-types */
import { createContext, useContext, useEffect, useState } from "react";
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
        const res = await fetch("/api/tweets");

        // Check if the response is okay
        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }
        console.log(res);
        const data = await res.json();
        console.log(data);
        setTweets(data);
        setTweetsUpdated(true);
      } catch (e) {
        console.error("Fetch error:", e);
      }
    }

    fetchAllTweets();
  }, [tweetsUpdated]);

  async function handleAddTweet(tweetObj) {
    try {
      const res = await fetch("/api/tweets", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(tweetObj),
      });

      if (!res.ok) {
        const errorData = await res.json();
        console.error("Error response:", errorData);
        throw new Error(`Server error: ${res.status} - ${res.statusText}`);
      }

      const responseData = await res.json();
      console.log("Response data:", responseData);
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
