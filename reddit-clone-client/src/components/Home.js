import React, { Fragment, useEffect, useReducer } from "react";
import NavBar from "./NavBar";
import axios from "axios";
import PostCard from "./PostCard";

const initialState = { posts: [] };

const updatePost = (posts, payload) => {
  return posts.map((post) => {
    if (post.identifier !== payload.identifier) {
      return post;
    }
    return {
      ...post,
      ...payload,
    };
  });
};

const postReducer = (state, action) => {
  const { type, payload } = action;
  switch (type) {
    case "SET_POSTS":
      return { ...state, ...{ posts: payload } };
    case "UPDATE_POST":
      return { ...state, ...{ posts: updatePost(state.posts, payload) } };
    default:
      return state;
  }
};

function Home() {
  const [state, dispatch] = useReducer(postReducer, initialState);
  const { posts } = state;

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await axios.get("posts");
        dispatch({ type: "SET_POSTS", payload: response.data });
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
      <div className="container flex py-4">
        {/* Posts */}
        <div className="w-160">
          {posts.map((post) => {
            return (
              <PostCard post={post} key={post.identifier} dispatch={dispatch} />
            );
          })}
        </div>
        {/* Sidebar */}
      </div>
    </Fragment>
  );
}

export default Home;
