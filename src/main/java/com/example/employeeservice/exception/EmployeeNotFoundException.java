package com.example.employeeservice.exception;

/**
 * Excepción personalizada cuando no existe un empleado.
 */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Empleado no encontrado con la identificación: " + id);
    }
}