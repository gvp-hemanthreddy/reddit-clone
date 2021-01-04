import React, { Fragment, useState, useEffect } from "react";
import NavBar from "./NavBar";
import axios from "axios";
import Post from "./Post";

function Home() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await axios.get("posts");
        setPosts(response.data);
      } catch (err) {
        console.error("Error while fetching posts");
      }
    }
    fetchData();
  }, []);

  useEffect(() => {
    document.title = "Reddit - Home Page";
  }, []);

  return (
    <Fragment>
      <NavBar />
      <div className="container flex pt-4">
        {/* Posts */}
        <div className="w-160">
          {posts.map((post) => {
            return <Post post={post} key={post.identifier} />;
          })}
        </div>
        {/* Sidebar */}
      </div>
    </Fragment>
  );
}

export default Home;
