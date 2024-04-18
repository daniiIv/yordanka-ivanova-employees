package com.example.sirmabgemployeeproj.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // choose a file
    @PostMapping("/employees/upload")
    public String uploadCSVFile(@RequestParam("file") MultipartFile  file) {
        try {
            // Call the method to map employees from the CSV
            employeeService.mapEmployeesFromCsv(file);
            return "File uploaded successfully.";
        } catch (Exception e) {
            throw new RuntimeException("Failed to process the uploaded file.", e);
        }
    }
    // import CSV
    @GetMapping("/employeeData")
    public List<Employee> showEmployeeData() {
        return employeeService.loadEmployeeData();
    }
    // Show statistics
    @GetMapping("/statistics")
    public List<TeamsStats> showStatistics() {
        return employeeService.showStatistics();
    }
}
