import React from "react";
import "./header.css";

const Header = ({ children }) => {
  return (
    <header className="header">
      <div className="header-content">
        {children}
      </div>
      <div className="logout">
        <span>Logout</span>
      </div>
    </header>
  );
};

export default Header;
