package com.example.sirmabgemployeeproj.employees;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@JsonPropertyOrder({"employeeId", "projectId", "dateFrom", "dateTo"})
public class Employee {

    @Id
    private Integer employeeId;
    private Integer projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
