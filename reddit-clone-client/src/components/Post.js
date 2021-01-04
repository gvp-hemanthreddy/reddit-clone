import React from "react";
import { Link } from "react-router-dom";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import axios from "axios";
import classnames from "classnames";

dayjs.extend(relativeTime);

function Post(props) {
  const { post } = props;
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
    if (userVote === value) {
      value = 0;
    }
    try {
      await axios.post("votes", {
        postIdentifier: identifier,
        value,
      });
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
          <Link to={`r/${subreddit}`} className="flex items-center">
            <img
              src="http://www.gravatar.com/avatar"
              alt="gravatar"
              className="w-6 h-6 rounded-full"
            />
            <span className="mx-1 font-bold hover:underline">
              {"r/" + subreddit}
            </span>
          </Link>
          <p className="flex items-center text-gray-500">
            <span>• Posted by </span>
            <Link
              to={`u/${username}`}
              className="mx-1 hover:underline"
            >{`u/${username}`}</Link>
            <Link to={`r/${url}`} className="hover:underline">
              {dayjs(createdAt).fromNow()}
            </Link>
          </p>
        </div>
        <div className="py-1">
          <Link to={`r/${url}`} className="my-1 text-lg font-medium">
            {title}
          </Link>
          <p className="my-1 text-sm">{body}</p>
        </div>
        <div className="flex items-center text-xs font-bold text-gray-500">
          <Link to={`r/${url}`} className="p-1 mr-1 rounded hover:bg-gray-200">
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

export default Post;
