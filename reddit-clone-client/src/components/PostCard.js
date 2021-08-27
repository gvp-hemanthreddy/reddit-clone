import React from "react";
import { Link, useHistory } from "react-router-dom";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import axios from "axios";
import classnames from "classnames";
import { useAuthState } from "../context/Auth";

dayjs.extend(relativeTime);

function PostCard(props) {
  const { post, dispatch } = props;
  const { authenticated } = useAuthState();
  const history = useHistory();
  const {
    identifier,
    voteScore,
    subreddit,
    username,
    createdAt,
    url,
    body,
    title,
    commentCount,
    userVote,
  } = post;

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
        postIdentifier: identifier,
        value,
      });
      const newVoteScore = voteScore - userVote + value;
      const payload = { ...post, userVote: value, voteScore: newVoteScore };
      dispatch({ type: "UPDATE_POST", payload });
    } catch (e) {
      console.error("Error while voting");
    }
  };

  return (
    <div
      key={identifier}
      className="flex mb-4 bg-white border border-gray-300 rounded hover:border-gray-500"
    >
      {/* Vote Section */}
      <div className="w-10 p-2 text-sm text-center text-gray-400 bg-gray-100">
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
        <div className="py-1 overflow-x-hidden max-h-32">
          <Link to={url}>
            <h1 className="my-1 text-lg font-medium">{title}</h1>
            <pre className="my-1 text-sm whitespace-pre-wrap">{body}</pre>
          </Link>
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
      </div>
    </div>
  );
}

export default PostCard;
