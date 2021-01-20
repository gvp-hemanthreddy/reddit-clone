import React from "react";
import { Link, useHistory } from "react-router-dom";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import axios from "axios";
import classnames from "classnames";
import { useAuthState } from "../context/Auth";

dayjs.extend(relativeTime);

function CommentCard(props) {
  const { comment, postIdentifier, dispatch } = props;
  const { authenticated } = useAuthState();
  const history = useHistory();
  const {
    identifier,
    body,
    username,
    userVote,
    voteScore,
    createdAt,
  } = comment;

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
        commentIdentifier: identifier,
        value,
      });
      const newVoteScore = voteScore - userVote + value;
      const payload = { ...comment, userVote: value, voteScore: newVoteScore };
      dispatch({ type: "UPDATE_COMMENT", payload });
    } catch (e) {
      console.error("Error while voting");
    }
  };

  return (
    <div className="flex">
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
        <div className="text-xs">
          <Link to={`/u/${username}`} className="mr-1 font-bold hover:underline">
            {username}
          </Link>
          <span className="text-gray-500">{dayjs(createdAt).fromNow()}</span>
        </div>
        <div className="py-1">
          <p className="my-1 text-sm">{body}</p>
        </div>
      </div>
    </div>
  );
}

export default CommentCard;
