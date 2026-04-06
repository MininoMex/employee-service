package com.example.employeeservice.service;

import com.example.employeeservice.dto.CreateEmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.dto.UpdateEmployeeRequest;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.exception.BadRequestException;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import com.example.employeeservice.mapper.EmployeeMapper;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Capa de servicio.
 */
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper empleadoMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = empleadoMapper;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeMapper.toResponse(employee);
    }

    public List<EmployeeResponse> createEmployees(List<CreateEmployeeRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new BadRequestException("El cuerpo de la solicitud debe contener al menos un empleado.");
        }

        List<Employee> employees = requests.stream()
                .map(request -> {
                    validateAgeConsistency(request.getAge(), request.getBirthDate());
                    return employeeMapper.toEntity(request);
                })
                .collect(Collectors.toList());

        List<Employee> savedEmployees = employeeRepository.saveAll(employees);

        return savedEmployees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse updateEmployee(Long id, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        LocalDate effectiveBirthDate = request.getBirthDate() != null
                ? request.getBirthDate()
                : employee.getBirthDate();

        validateAgeConsistency(request.getAge(), effectiveBirthDate);

        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        if (request.getSecondName() != null) {
            employee.setSecondName(request.getSecondName());
        }
        if (request.getPaternalLastName() != null) {
            employee.setPaternalLastName(request.getPaternalLastName());
        }
        if (request.getMaternalLastName() != null) {
            employee.setMaternalLastName(request.getMaternalLastName());
        }
        if (request.getGender() != null) {
            employee.setGender(request.getGender());
        }
        if (request.getBirthDate() != null) {
            employee.setBirthDate(request.getBirthDate());
        }
        if (request.getPosition() != null) {
            employee.setPosition(request.getPosition());
        }
        if (request.getEstado() != null) {
            employee.setEstado(request.getEstado());
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponse(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeRepository.delete(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> searchEmployeesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("El parámetro de nombre es obligatorio.");
        }

        return employeeRepository.searchByName(name.trim())
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void validateAgeConsistency(Integer age, LocalDate birthDate) {
        if (age == null || birthDate == null) {
            return;
        }

        int calculatedAge = Period.between(birthDate, LocalDate.now()).getYears();

        if (age != calculatedAge) {
            throw new BadRequestException(
                    "La edad indicada no coincide con la fecha de nacimiento. Edad prevista: " + calculatedAge
            );
        }
    }
}