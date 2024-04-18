import React from "react";
import EmployeeController from "./EmployeeController"; // Assuming EmployeeController.js is in the same directory
import "bootstrap/dist/css/bootstrap.css";

function App() {
  return (
    <div className="App container mt-5">
      <h1>Employee Management System</h1>
      <EmployeeController />
    </div>
  );
}

export default App;
