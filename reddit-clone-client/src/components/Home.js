import React, { Fragment, useEffect, useReducer } from "react";
import { Link } from "react-router-dom";
import NavBar from "./NavBar";
import axios from "axios";
import PostCard from "./PostCard";
import { useAuthState } from "../context/Auth";

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
  const { authenticated } = useAuthState();

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
        <div className="w-full p-3 lg:w-160 lg:p-0">
          {posts.map((post) => {
            return (
              <PostCard post={post} key={post.identifier} dispatch={dispatch} />
            );
          })}
        </div>
        {/* Sidebar */}
        <div className="hidden ml-6 w-80 lg:block">
          <div className="bg-white rounded">
            <div className="p-3 text-center bg-blue-500 rounded-t">
              <p className="font-semibold text-white">Home</p>
            </div>
            <div className="p-3">
              <p className="mb-3 text-sm">
                Your personal Reddit frontpage. Come here to check in with your
                favorite communities.
              </p>
              {authenticated && (
                <Link to={`/subs/create`}>
                  <button className="w-full button blue">
                    Create Community
                  </button>
                </Link>
              )}
            </div>
          </div>
        </div>
      </div>
    </Fragment>
  );
}

export default Home;
