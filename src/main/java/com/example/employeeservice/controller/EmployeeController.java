package com.example.employeeservice.controller;

import com.example.employeeservice.dto.CreateEmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.dto.UpdateEmployeeRequest;
import com.example.employeeservice.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * GET /employees
     * Devuelve todos los empleados.
     */
    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * GET /employees/{id}
     * Devuelve el detalle de un empleado por ID.
     */
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    /**
     * POST /employees
     * Permite insertar uno o varios empleados.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<EmployeeResponse> createEmployees(@Valid @RequestBody List<@Valid CreateEmployeeRequest> requests) {
        return employeeService.createEmployees(requests);
    }

    /**
     * PUT /employees/{id}
     * Permite actualizar total o parcialmente un empleado.
     */
    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Long id,
                                           @Valid @RequestBody UpdateEmployeeRequest request) {
        return employeeService.updateEmployee(id, request);
    }

    /**
     * DELETE /employees/{id}
     * Elimina un empleado por su ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    /**
     * GET /employees/search?name=...
     * Busca por nombre o apellidos de forma parcial.
     */
    @GetMapping("/search")
    public List<EmployeeResponse> searchEmployeesByName(
            @RequestParam @NotBlank(message = "name parameter is required") String name) {
        return employeeService.searchEmployeesByName(name);
    }
}