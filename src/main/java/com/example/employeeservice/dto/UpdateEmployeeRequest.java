package com.example.employeeservice.dto;

import com.example.employeeservice.entity.Gender;
import com.example.employeeservice.util.AppConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO para actualizar empleados.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateEmployeeRequest {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String secondName;

    @Size(max = 100)
    private String paternalLastName;

    @Size(max = 100)
    private String maternalLastName;

    private Gender gender;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada.")
    @JsonFormat(pattern = AppConstants.DATE_PATTERN)
    private LocalDate birthDate;

    @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
    @Max(value = 130)
    private Integer age;

    @Size(max = 120)
    private String position;

    private Boolean estado;

}