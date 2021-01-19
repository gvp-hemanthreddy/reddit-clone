import React, { useState, useEffect, Fragment } from "react";
import axios from "axios";
import NavBar from "./NavBar";
import PostCard from "./PostCard";

function Subreddit(props) {
  const { name } = props.match.params;
  const [subreddit, setSubreddit] = useState({});
  const [error, setError] = useState("");
  const { title, posts } = subreddit;

  useEffect(() => {
    const getSubreddit = async function () {
      try {
        const response = await axios.get(`subreddit/${name}`);
        setSubreddit(response.data);
      } catch (err) {
        setError(`Subreddit ${name} does not exist`);
        console.error("Error while fetching subreddit");
      }
    };
    getSubreddit();
  }, [name]);

  let postsMarkup;

  if (error !== "") {
    postsMarkup = error;
  } else if (posts && posts.length > 0) {
    postsMarkup = posts.map((post) => {
      return <PostCard post={post} key={post.identifier} />;
    });
  } else {
    postsMarkup = <p>No posts found</p>;
  }

  return (
    <Fragment>
      <NavBar />
      <div>
        <div className="w-full h-20 bg-blue-400"></div>
        <div className="w-full h-24 bg-white">
          <div className="container flex">
            <img
              src="http://www.gravatar.com/avatar"
              alt="gravatar"
              className="w-16 h-16 pt-1 mr-4 rounded-full"
            />
            <div className="flex flex-col pt-2 font-bold">
              <h1 className="text-3xl">{title}</h1>
              <p className="pt-3 text-xs text-gray-500">{`r/${name}`}</p>
            </div>
          </div>
        </div>
      </div>
      <div className="container flex pt-4">
        {/* Posts */}
        <div className="w-160">{postsMarkup}</div>
        {/* Sidebar */}
      </div>
    </Fragment>
  );
}

export default Subreddit;
