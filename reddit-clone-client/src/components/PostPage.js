import React, { Fragment, useEffect, useState, useReducer } from "react";
import { Link, useHistory } from "react-router-dom";
import axios from "axios";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import classnames from "classnames";
import { useAuthState } from "../context/Auth";
import NavBar from "./NavBar";
import CommentCard from "./CommentCard";

dayjs.extend(relativeTime);

const updateComment = (comments, payload) => {
  if (!comments || !payload) return;

  return comments.map((comment) => {
    if (comment.identifier !== payload.identifier) {
      return comment;
    }

    return {
      ...comment,
      ...payload,
    };
  });
};

const postReducer = (state, action) => {
  const { type, payload } = action;

  switch (type) {
    case "SET_POST":
      return { ...state, ...payload };
    case "SET_COMMENTS":
      return { ...state, comments: payload };
    case "UPDATE_VOTE":
      return { ...state, ...payload };
    case "UPDATE_COMMENT":
      const newComments = updateComment(state.comments, payload);
      return { ...state, comments: newComments };
    default:
      return state;
  }
};

function PostPage(props) {
  const { postIdentifier, slug } = props.match.params;
  const [comment, setComment] = useState("");
  const { authenticated, username: loggedInUser } = useAuthState();
  const history = useHistory();
  const [state, dispatch] = useReducer(postReducer, {});
  const {
    identifier,
    subreddit,
    username,
    createdAt,
    url,
    body,
    title,
    commentCount,
    userVote,
    voteScore,
    comments,
  } = state;

  useEffect(() => {
    async function fetchPost() {
      try {
        const response = await axios.get(
          `posts/${postIdentifier}/${slug}/comments`
        );
        dispatch({ type: "SET_POST", payload: response.data });
      } catch (err) {
        console.error("Error while fetching post");
      }
    }

    fetchPost();
  }, [postIdentifier, slug]);

  const vote = async (value) => {
    if (!authenticated) {
      history.push("/login");
      return;
    }
    if (userVote === value) {
      value = 0;
    }
    try {
      await axios.post("votes", {
        postIdentifier,
        value,
      });
      const newVoteScore = voteScore - userVote + value;
      const newVote = {
        userVote: value,
        voteScore: newVoteScore,
      };
      dispatch({ type: "UPDATE_VOTE", payload: newVote });
    } catch (e) {
      console.error("Error while voting");
    }
  };

  const commentOnPost = async () => {
    if (!authenticated || comment.trim() === "") return;
    try {
      const response = await axios.post(
        `posts/${postIdentifier}/${slug}/comments`,
        {
          body: comment,
        }
      );
      const newComments = [response.data, ...comments];
      dispatch({ type: "SET_COMMENTS", payload: newComments });
    } catch (e) {
      console.error("Error while commenting");
    } finally {
      setComment("");
    }
  };

  if (!url) {
    return <div>Loading...</div>;
  }

  return (
    <Fragment>
      <NavBar />
      <div className="container flex px-2 py-4 lg:px-0">
        <div className="w-full bg-white border border-gray-300 rounded lg:w-160 hover:border-gray-500">
          {/* Post Info */}
          <div key={identifier} className="flex">
            {/* Vote Section */}
            <div className="w-10 p-2 text-sm text-center text-gray-400">
              <div
                className="w-6 mx-auto rounded cursor-pointer hover:text-red-500 hover:bg-gray-300"
                onClick={() => vote(1)}
              >
                <i
                  className={classnames("icon-arrow-up", {
                    "text-red-500": userVote === 1,
                  })}
                ></i>
              </div>
              <span
                className={classnames("w-6 mx-auto font-bold text-xs", {
                  "text-red-500": userVote === 1,
                  "text-blue-600": userVote === -1,
                })}
              >
                {voteScore}
              </span>
              <div
                className="w-6 mx-auto rounded cursor-pointer hover:text-blue-600 hover:bg-gray-300"
                onClick={() => vote(-1)}
              >
                <i
                  className={classnames("icon-arrow-down", {
                    "text-blue-600": userVote === -1,
                  })}
                ></i>
              </div>
            </div>
            {/* Body */}
            <div className="w-full p-2">
              <div className="flex items-center text-xs">
                <Link to={`/r/${subreddit}`} className="flex items-center">
                  <img
                    src="http://www.gravatar.com/avatar"
                    alt="gravatar"
                    className="w-6 h-6 rounded-full"
                  />
                  <span className="mx-1 font-bold hover:underline">
                    {"r/" + subreddit}
                  </span>
                </Link>
                <p className="items-center hidden text-gray-500 md:flex">
                  <span>• Posted by </span>
                  <Link
                    to={`/u/${username}`}
                    className="mx-1 hover:underline"
                  >{`u/${username}`}</Link>
                  <Link to={url} className="hover:underline">
                    {dayjs(createdAt).fromNow()}
                  </Link>
                </p>
              </div>
              <div className="py-1">
                <h1 className="my-1 text-lg font-medium">{title}</h1>
                <pre className="my-1 text-sm">{body}</pre>
              </div>
              <div className="flex items-center text-xs font-bold text-gray-500">
                <Link to={url} className="p-1 mr-1 rounded hover:bg-gray-200">
                  <i className="mr-1 fas fa-comment-alt"></i>
                  <span>{commentCount} Comments</span>
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
              {/* Create comment */}
              <div className="py-3 pr-3 text-sm">
                {authenticated ? (
                  <Fragment>
                    {" "}
                    <p>
                      Comment as{" "}
                      <span className="font-bold text-blue-500">
                        {loggedInUser}
                      </span>
                    </p>
                    <textarea
                      className="w-full h-24 p-2 border rounded outline-none"
                      onChange={(e) => setComment(e.target.value)}
                      value={comment}
                    ></textarea>
                    <div className="flex justify-end">
                      <button
                        className="button blue hover:bg-blue-500"
                        onClick={commentOnPost}
                        disabled={comment.trim() === ""}
                      >
                        Comment
                      </button>
                    </div>
                  </Fragment>
                ) : (
                  <div className="flex items-center justify-between p-2 border rounded">
                    <span className="text-gray-500">
                      Log in or sign up to leave a comment
                    </span>
                    <div className="flex">
                      <button
                        className="mr-1 button hollow"
                        onClick={() => history.push("/login")}
                      >
                        Log In
                      </button>
                      <button
                        className="button blue"
                        onClick={() => history.push("/register")}
                      >
                        Sign Up
                      </button>
                    </div>
                  </div>
                )}
              </div>
            </div>
          </div>
          <hr />
          {/* Comments */}
          <div className="flex flex-col">
            {comments &&
              comments.map((comment) => {
                return (
                  <CommentCard
                    comment={comment}
                    key={comment.identifier}
                    postIdentifier={postIdentifier}
                    dispatch={dispatch}
                  />
                );
              })}
          </div>
        </div>
      </div>
    </Fragment>
  );
}

export default PostPage;
