package com.example.employeeservice.dto;

import com.example.employeeservice.entity.Gender;
import com.example.employeeservice.util.AppConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de salida para responder al cliente.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeResponse {

    private Long id;

    private String firstName;

    private String secondName;

    private String paternalLastName;

    private String maternalLastName;

    private Integer age;

    private Gender gender;

    @JsonFormat(pattern = AppConstants.DATE_PATTERN)
    private LocalDate birthDate;

    private String position;

    @JsonFormat(pattern = AppConstants.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    private boolean estado;
}