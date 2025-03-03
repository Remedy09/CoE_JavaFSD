import React from "react";
import Navbar from "./components/Navbar";
import Products from "./components/Products";

function App() {
  return (
    <div>
      <Navbar />
      <Products/>
      <main style={{ padding: "20px" }}>Amazon Clone Content Here</main>
    </div>
  );
}

export default App;
