package com.example.employeeservice.mapper;

import com.example.employeeservice.dto.CreateEmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.entity.Employee;
import org.springframework.stereotype.Component;

/**
 * Mapper manual.
 */
@Component
public class EmployeeMapper {

    /**
     * Convierte el DTO de creación a entidad.
     */
    public Employee toEntity(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setSecondName(request.getSecondName());
        employee.setPaternalLastName(request.getPaternalLastName());
        employee.setMaternalLastName(request.getMaternalLastName());
        employee.setGender(request.getGender());
        employee.setBirthDate(request.getBirthDate());
        employee.setPosition(request.getPosition());
        if (request.getEstado() != null) {
            employee.setEstado(request.getEstado());
        }

        return employee;
    }

    /**
     * Convierte la entidad a DTO de respuesta.
     */
    public EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setSecondName(employee.getSecondName());
        response.setPaternalLastName(employee.getPaternalLastName());
        response.setMaternalLastName(employee.getMaternalLastName());
        response.setAge(employee.getEdad());
        response.setGender(employee.getGender());
        response.setBirthDate(employee.getBirthDate());
        response.setPosition(employee.getPosition());
        response.setCreatedAt(employee.getCreatedAt());
        response.setEstado(employee.isEstado());
        return response;
    }
}