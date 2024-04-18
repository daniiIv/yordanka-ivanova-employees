package com.example.sirmabgemployeeproj.employees;

import com.opencsv.CSVReader;
import jakarta.annotation.Nullable;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Getter
public class EmployeeService {
  private List<Employee> employees;
  List<TeamsStats> teamsStatistics;

  public EmployeeService(){
      this.employees = new ArrayList<>();
      this.teamsStatistics = new ArrayList<>();
  }
    // choose a file  (open file dialog)
public void mapEmployeesFromCsv(MultipartFile csvFile) {
    try {
        if (!employees.isEmpty()){employees.clear();}
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
        reader.readLine();
        CSVReader csvReader = new CSVReader(reader);

        String[] line;
        while ((line = csvReader.readNext()) != null) {
            Employee employee = new Employee();
            employee.setEmployeeId(Integer.parseInt(line[0]));
            employee.setProjectId(Integer.parseInt(line[1]));
            employee.setDateFrom(dataParseAllFormats(line[2]));
            employee.setDateTo(dataParseAllFormats(line[3]));

            employees.add(employee);
        }
    }catch (Exception e){
        throw new RuntimeException(e);
    }

}
    // import CSV (show data grid with data extracted from the file)
    //EmpID, ProjectID, DateFrom, DateTo
    public List<Employee> loadEmployeeData(){
        return  employees;
    }

    // Show statistics (show data grid with processed data)
    // Employee ID #1, Employee ID #2, Project ID, time worked on the proj sum, time worked as a team
    public List<TeamsStats> showStatistics(){
      if (!teamsStatistics.isEmpty()){teamsStatistics.clear();}
    Map<Integer, List<Employee>> map = new HashMap<>();
         map =  employees.stream().collect(Collectors.groupingBy(Employee::getProjectId, Collectors.toList()));

         map.forEach(
             (k,v) -> {
                 if (v.size()==2){
                     var emplIDs = (v.stream().map(Employee::getEmployeeId).toList());
                     long timeWorkedOnProj = getDateBetween(v);
                     var stats =  new TeamsStats(k, emplIDs, timeWorkedOnProj);
                     teamsStatistics.add(stats);
                 }
             });

     return teamsStatistics;
}

public long getDateBetween(List<Employee> team){
    if (team.size()==2) {
        Employee e1 = team.get(0);
        Employee e2 = team.get(1);

        LocalDate d1Start = e1.getDateFrom();
        LocalDate d1End = e1.getDateTo();
        LocalDate d2Start = e2.getDateFrom();
        LocalDate d2End = e2.getDateTo();

        long timeWorkedOnTheProjAtAll;
        if (d2End == null || d1End == null) {
            if (d1Start.isBefore(d2Start)) {
                timeWorkedOnTheProjAtAll = ChronoUnit.DAYS.between(d1Start, LocalDate.now());
            } else {
                timeWorkedOnTheProjAtAll = ChronoUnit.DAYS.between(d2Start, LocalDate.now());
            }
        } else {
            if (d1Start.isBefore(d2Start)) {
                if (d1End.isBefore(d2End)) {
                    timeWorkedOnTheProjAtAll = ChronoUnit.DAYS.between(d1Start, d2End);
                } else {
                    timeWorkedOnTheProjAtAll = ChronoUnit.DAYS.between(d2Start, d1End);
                }
            } else {
                if (d1End.isBefore(d2End)) {
                    timeWorkedOnTheProjAtAll = ChronoUnit.DAYS.between(d1Start, d2End);
                } else {
                    timeWorkedOnTheProjAtAll = ChronoUnit.DAYS.between(d2Start, d1End);
                }
            }
        }
        return timeWorkedOnTheProjAtAll;
    }else return 0;
}

// Data parse from different formats
 public LocalDate dataParseAllFormats(@Nullable String dataString){
     try {
         if (dataString == null){
             return null;
         } else {
             DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                     .append(DateTimeFormatter.ofPattern("[dd/MM/yyyy]" + "[MM/dd/yyyy]" + "[dd-MM-yyyy]" + "[yyyy-MM-dd]"));
             DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();
             return LocalDate.parse(dataString, dateTimeFormatter);
         }
     }catch (DateTimeParseException e){
         return null;
     }
 }
}
