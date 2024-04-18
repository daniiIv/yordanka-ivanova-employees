import React, { useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.css";

function EmployeeController() {
  const [employees, setEmployees] = useState([]);
  const [statistics, setStatistics] = useState([]);
  const [selectedFile, setSelectedFile] = useState(null);

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleUpload = async () => {
    try {
      const formData = new FormData();
      formData.append("file", selectedFile);
      await axios.post("http://localhost:8080/employees/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      alert("File uploaded successfully.");
    } catch (error) {
      console.error("Error uploading file:", error);
      alert("Error uploading file. Please try again.");
    }
  };

  const handleShowEmployeeData = () => {
    axios
      .get("http://localhost:8080/employeeData")
      .then((response) => {
        setEmployees(response.data);
      })
      .catch((error) => {
        console.error("Error loading employee data:", error);
      });
  };

  const handleShowStatistics = () => {
    axios
      .get("http://localhost:8080/statistics")
      .then((response) => {
        setStatistics(response.data);
      })
      .catch((error) => {
        console.error("Error loading statistics:", error);
      });
  };

  return (
    <div className="container mt-5">
      <div className="mb-3">
        <input
          type="file"
          className="form-control"
          onChange={handleFileChange}
        />
        <button
          className="btn btn-primary mt-2"
          onClick={handleUpload}
          disabled={!selectedFile}
        >
          Upload
        </button>
      </div>

      <button
        className="btn btn-secondary me-2"
        onClick={handleShowEmployeeData}
        disabled={!employees}
      >
        Show Employee Data
      </button>

      <table className="table mt-3">
        <thead>
          <tr>
            <th>Employee ID</th>
            <th>Project ID</th>
            <th>Date From</th>
            <th>Date To</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((employee) => (
            <tr key={employee.employeeId}>
              <td>{employee.employeeId}</td>
              <td>{employee.projectId}</td>
              <td>{employee.dateFrom}</td>
              <td>{employee.dateTo}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <button
        className="btn btn-secondary me-2"
        onClick={handleShowStatistics}
        disabled={!statistics}
      >
        Show Statistics
      </button>

      <table className="table mt-3">
        <thead>
          <tr>
            <th>Project ID</th>
            <th>Employee IDs</th>
            <th>Days Worked on the Project</th>
          </tr>
        </thead>
        <tbody>
          {statistics.map((stat) => (
            <tr key={stat.projectId}>
              <td>{stat.projectId}</td>
              <td>{stat.emplIDs ? stat.emplIDs.join(", ") : ""}</td>
              <td>{stat.timeWorkedOnTheProj}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default EmployeeController;
