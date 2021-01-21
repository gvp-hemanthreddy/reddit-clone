import React, { Fragment, useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import NavBar from "./NavBar";
import Sidebar from "./Sidebar";

function CreatePost(props) {
  const { subredditname } = props.match.params;
  const [title, setTitle] = useState("");
  const [body, setBody] = useState("");
  const [subreddit, setSubreddit] = useState({});
  const [error, setError] = useState("");
  const history = useHistory();

  useEffect(() => {
    const getSubreddit = async function () {
      try {
        const response = await axios.get(`subreddit/${subredditname}`);
        setSubreddit(response.data);
      } catch (err) {
        console.error("Error while fetching subreddit");
        history.push("/");
      }
    };
    getSubreddit();
  }, [subredditname, history]);

  const submitPost = async (event) => {
    event.preventDefault();
    if (title.trim() === "") return;

    try {
      const response = await axios.post("posts", {
        title,
        body,
        subredditname,
      });
      const post = response.data;
      history.push(`/r/${subredditname}/${post.identifier}/${post.slug}`);
    } catch (e) {
      setError("Error while creating post");
      console.error("Error while creating post");
    }
  };

  return (
    <Fragment>
      <NavBar />
      <div className="container flex py-4">
        <div className="px-3 py-4 bg-white border border-gray-300 rounded w-160">
          <h1>Submit a post to {`/r/${subredditname}`}</h1>
          {error && <span className="my-2">{error}</span>}
          <form className="my-3 text-sm" onSubmit={submitPost}>
            <div className="relative">
              <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Title"
                maxLength="180"
                className="w-full p-2 mb-3 border border-gray-400 rounded focus:outline-none focus:border-gray-500"
              ></input>
              <span
                className="absolute text-gray-400"
                style={{ top: 10, right: 10 }}
              >
                {title.trim().length}/180
              </span>
            </div>
            <textarea
              value={body}
              onChange={(e) => setBody(e.target.value)}
              placeholder="Text (Optional)"
              className="w-full p-2 border border-gray-400 rounded focus:outline-none focus:border-gray-500"
              rows="6"
            ></textarea>
            <div className="flex justify-end mt-2">
              <button
                className="button blue"
                type="submit"
                disabled={title.trim().length === 0}
              >
                Post
              </button>
            </div>
          </form>
        </div>
        <Sidebar subreddit={subreddit} hideCreatePostButton={true} />
      </div>
    </Fragment>
  );
}

export default CreatePost;
