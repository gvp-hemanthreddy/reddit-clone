import React, { Fragment, useState, useEffect } from "react";
import axios from "axios";
import dayjs from "dayjs";
import NavBar from "./NavBar";
import PostCard from "./PostCard";
import UserPageComment from "./utils/UserPageComment";

function UserPage(props) {
  const { username } = props.match.params;
  const [error, setError] = useState("");
  const [createdAt, setCreatedAt] = useState("");
  const [submissions, setSubmissions] = useState([]);

  useEffect(() => {
    const fetchUserSubmissions = async () => {
      try {
        const response = await axios.get(`users/${username}`);
        setCreatedAt(response.data.createdAt);
        setSubmissions(response.data.submissions);
      } catch (e) {
        setError(`${username} not found`);
        console.error("Error while fetching user submissions");
      }
    };

    fetchUserSubmissions();
  }, [username]);

  return (
    <Fragment>
      <NavBar />
      <div className="container flex py-4">
        {error !== "" ? (
          <div className="mx-auto text-3xl">
            <span>{error}</span>
          </div>
        ) : (
          <Fragment>
            <div className="w-160">
              {submissions.map((userSubmission) => {
                if (userSubmission.type === "post") {
                  const post = userSubmission.submission;
                  return <PostCard post={post} key={post.identifier} />;
                } else {
                  const comment = userSubmission.submission;
                  return (
                    <UserPageComment
                      comment={comment}
                      key={comment.identifier}
                    ></UserPageComment>
                  );
                }
              })}
            </div>
            <div className="ml-6 w-80">
              <div className="bg-white rounded">
                <div className="p-3 bg-blue-500 rounded-t">
                  <img
                    src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y"
                    alt="gravatar"
                    className="w-20 h-20 mx-auto rounded-full"
                  />
                </div>
                <div className="p-2 text-center">
                  <p className="mb-3">{username}</p>
                  <hr />
                  <p className="my-3 text-sm">
                    Joined {dayjs(createdAt).format("MMM YYYY")}
                  </p>
                </div>
              </div>
            </div>
          </Fragment>
        )}
      </div>
    </Fragment>
  );
}

export default UserPage;
