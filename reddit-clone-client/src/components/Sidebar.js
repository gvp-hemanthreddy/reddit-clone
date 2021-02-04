import React from "react";
import { Link } from "react-router-dom";
import dayjs from "dayjs";

import { useAuthState } from "../context/Auth";

function Sidebar(props) {
  const { subreddit, hideCreatePostButton } = props;
  const { authenticated } = useAuthState();

  return (
    <div className="hidden ml-6 w-80 lg:block">
      <div className="bg-white rounded">
        <div className="p-3 bg-blue-500 rounded-t">
          <p className="font-semibold text-white">About Community</p>
        </div>
        <div className="p-3">
          <p className="mb-3">{subreddit.description}</p>
          <hr />
          <p className="my-3 text-sm">
            <i className="mr-2 fas fa-birthday-cake"></i>
            Created {dayjs(subreddit.createdAt).format("d MMM YYYY")}
          </p>
          {authenticated && !hideCreatePostButton && (
            <Link to={`/r/${subreddit.name}/submit`}>
              <button className="w-full button blue">Create Post</button>
            </Link>
          )}
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
