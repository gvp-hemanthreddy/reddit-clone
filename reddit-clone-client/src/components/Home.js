import React, { Fragment, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import NavBar from "./NavBar";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import axios from "axios";

dayjs.extend(relativeTime);

function Home() {
  const [posts, setPosts] = useState([]);

  // TODO: Mock until vote count and comments count is returned from response
  const mockVotesAndComments = (data) => {
    data.forEach((post) => {
      post.voteCount = 124;
      post.commentCount = 438;
    });
  };

  useEffect(async () => {
    try {
      const response = await axios.get("posts");
      mockVotesAndComments(response.data);
      setPosts(response.data);
    } catch (err) {
      console.error("Error while fetching posts");
    }
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
            return (
              <div
                key={post.identifier}
                className="flex mb-4 bg-white border border-gray-300 rounded hover:border-gray-500"
              >
                {/* Vote Section */}
                <div className="w-10 p-2 text-sm bg-gray-100">
                  {post.voteCount}
                </div>
                {/* Body */}
                <div className="w-full p-2">
                  <div className="flex items-center text-xs">
                    <Link
                      to={`r/${post.subreddit}`}
                      className="flex items-center"
                    >
                      <img
                        src="http://www.gravatar.com/avatar"
                        alt="gravatar"
                        className="w-6 h-6 rounded-full"
                      />
                      <span className="mx-1 font-bold hover:underline">
                        {"r/" + post.subreddit}
                      </span>
                    </Link>
                    <p className="flex items-center text-gray-500">
                      <span>• Posted by </span>
                      <Link
                        to={`u/${post.username}`}
                        className="mx-1 hover:underline"
                      >{`u/${post.username}`}</Link>
                      <Link
                        to={`r/${post.subreddit}/${post.identifier}/${post.slug}`}
                        className="hover:underline"
                      >
                        {dayjs(post.createdAt).fromNow()}
                      </Link>
                    </p>
                  </div>
                  <div className="py-1">
                    <Link
                      to={`r/${post.subreddit}/${post.identifier}/${post.slug}`}
                      className="my-1 text-lg font-medium"
                    >
                      {post.title}
                    </Link>
                    <p className="my-1 text-sm">{post.body}</p>
                  </div>
                  <div className="flex items-center text-xs font-bold text-gray-500">
                    <Link
                      to={`r/${post.subreddit}/${post.identifier}/${post.slug}`}
                      className="p-1 mr-1 rounded hover:bg-gray-200"
                    >
                      <i className="mr-1 fas fa-comment-alt"></i>
                      <span>{post.commentCount} Comments</span>
                    </Link>
                    <div className="p-1 mr-1">
                      <i className="mr-1 fas fa-share"></i>
                      <span>Share</span>
                    </div>
                    <div className="p-1 mr-1">
                      <i className="mr-1 fas fa-bookmark"></i>
                      <span>Save</span>
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
        {/* Sidebar */}
      </div>
    </Fragment>
  );
}

export default Home;
