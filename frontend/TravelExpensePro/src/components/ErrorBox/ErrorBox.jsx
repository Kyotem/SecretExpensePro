import React from "react";
import "./ErrorBox.css";

const ErrorBox = ({ message }) => {
  return (
    <div className="error-box">
      <span>{message}</span>
    </div>
  );
};

export default ErrorBox;


