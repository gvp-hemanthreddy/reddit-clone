import React from "react";
import { Link } from "react-router-dom";

function UserPageComment(props) {
  const { comment } = props;
  const { username, postTitle, postUrl, subreddit, body } = comment;

  return (
    <div className="flex mb-4 bg-white border border-gray-300 rounded hover:border-gray-500">
      {/* Comment Icon Section */}
      <div className="flex flex-col justify-center w-10 p-2 text-center text-gray-400 bg-gray-100">
        <i className="fas fa-comments"></i>
      </div>
      {/* Body */}
      <div className="w-full">
        <div className="p-2 text-sm text-gray-500">
          {username} commented on{" "}
          <Link to={postUrl} className="font-bold hover:underline">
            {postTitle}
          </Link>
          <span> • </span>
          <Link
            to={`/r/${subreddit}`}
            className="font-bold hover:underline"
          >{`r/${subreddit}`}</Link>
        </div>
        <hr />
        <div className="p-2">
          <span>{body}</span>
        </div>
      </div>
    </div>
  );
}

export default UserPageComment;
