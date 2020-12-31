import React from "react";
import classnames from "classnames";

function InputGroup(props) {
  const { type, placeholder, value, setValue, error } = props;

  const className = classnames(
    "w-full p-3 transition duration-200 border border-gray-300 rounded outline-none bg-gray-50 focus:bg-white hover:bg-white",
    { "border-red-500": error }
  );

  return (
    <div className="mb-2">
      <input
        type={type}
        className={className}
        placeholder={placeholder}
        value={value}
        onChange={(e) => setValue(e.target.value)}
      ></input>
      <small className="text-sm text-red-500">{error}</small>
    </div>
  );
}

export default InputGroup;
