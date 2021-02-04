import React, { useState } from "react";
import { Redirect, useHistory } from "react-router-dom";
import classnames from "classnames";
import axios from "axios";
import { useAuthState } from "../context/Auth";

function CreateSubreddit() {
  const [name, setName] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [errors, setErrors] = useState("");
  const { authenticated } = useAuthState();
  const history = useHistory();

  const errorsListToMap = (errorsList) => {
    const errorsMap = {};
    errorsList.forEach((error) => {
      errorsMap[error.field] = error.message;
    });
    setErrors(errorsMap);
  };

  const validateInput = () => {
    const errorsMap = {};
    if (name === "") errorsMap.name = "Name is Empty";
    if (title === "") errorsMap.title = "Title is Empty";
    setErrors(errorsMap);
    return Object.keys(errorsMap).length === 0;
  };

  const formSubmit = async (event) => {
    event.preventDefault();

    if (!validateInput()) return;

    try {
      const response = await axios.post("subreddit", {
        name,
        title,
        description,
      });
      history.push(`/r/${response.data.name}`);
    } catch (err) {
      if (err.response?.data?.subErrors) {
        errorsListToMap(err.response.data.subErrors);
      } else {
        setErrors({ global: err.message });
      }
    }
  };

  if (!authenticated) {
    return <Redirect to="/" />;
  }

  return (
    <div className="flex bg-white">
      <div
        className="hidden h-screen bg-center bg-cover w-36 lg:block"
        style={{ backgroundImage: "url('/images/bricks.jpg')" }}
      ></div>
      <div className="flex flex-col justify-center pl-6">
        <div className="w-full lg:w-96">
          <h1 className="my-2 text-lg font-medium">Create a Community</h1>
          <hr />
          <form onSubmit={formSubmit}>
            <div className="my-3">
              <p className="font-medium">Name</p>
              <p className="mb-2 text-xs text-gray-500">
                Community names including capitalization cannot be changed
              </p>
              <input
                type="text"
                className={classnames(
                  "w-full p-3 transition duration-200 border border-gray-300 rounded outline-none bg-gray-50 focus:bg-white hover:bg-white",
                  { "border-red-500": errors.name }
                )}
                placeholder="Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              ></input>
              <small className="text-sm text-red-500">{errors.name}</small>
            </div>
            <div className="mb-2">
              <p className="font-medium">Title</p>
              <p className="mb-2 text-xs text-gray-500">
                Community title represent the topic, you can change it any time
              </p>
              <input
                type="text"
                className={classnames(
                  "w-full p-3 transition duration-200 border border-gray-300 rounded outline-none bg-gray-50 focus:bg-white hover:bg-white",
                  { "border-red-500": errors.title }
                )}
                placeholder="Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              ></input>
              <small className="text-sm text-red-500">{errors.title}</small>
            </div>
            <div className="mb-2">
              <p className="font-medium">Description</p>
              <p className="mb-2 text-xs text-gray-500">
                This is how new members come to understand your community
              </p>
              <textarea
                type="text"
                className="w-full p-3 transition duration-200 border border-gray-300 rounded outline-none bg-gray-50 focus:bg-white hover:bg-white"
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                rows="6"
              ></textarea>
            </div>
            <small className="block mb-2 text-sm text-red-500">
              {errors.createSubredditError}
            </small>
            <div className="flex justify-end">
              <button className="p-3 mb-4 text-xs font-bold text-white uppercase bg-blue-500 rounded hover:bg-blue-400">
                Create Community
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default CreateSubreddit;
