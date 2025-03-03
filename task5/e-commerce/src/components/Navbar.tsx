import React, { useState } from "react";

import "./Navbar.css";
import { NavLink } from "react-router-dom";

const Navbar = () => {
  const [menuOpen, setMenuOpen] = useState(false);

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <nav className="navbar">
      {/* Left - Logo & Menu */}
      <div className="navbar-left">
        <span className="menu-icon" onClick={toggleMenu}>☰</span>
        <NavLink to = "/" className="logo">Amazon</NavLink>
      </div>

      {/* Center - Search Bar */}
      <div className="navbar-search">
        <input type="text" placeholder="Search Amazon" />
        <button className="search-btn">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20" fill="black">
            <path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5 6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0A4.5 4.5 0 1 1 14 9.5a4.5 4.5 0 0 1-4.5 4.5z"/>
          </svg>
        </button>
      </div>

      {/* Right - Cart & Account */}
      <div className="navbar-right">
        {/* <button className="sign-in">Sign In</button> */}
        <NavLink to = "/cart" className="cart-icon">🛒</NavLink>
      </div>

      {/* Sidebar Menu (for mobile) */}
      <div className={`sidebar ${menuOpen ? "open" : ""}`}>
        <button className="close-btn" onClick={toggleMenu}>✖</button>
        <a href="#">Home</a>
        <a href="#">Today's Deals</a>
        <a href="#">Customer Service</a>
        <a href="#">Electronics</a>
        <a href="#">Fashion</a>
      </div>
    </nav>
  );
};

export default Navbar;
