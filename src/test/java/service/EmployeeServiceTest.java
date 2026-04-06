package service;

import com.example.employeeservice.dto.CreateEmployeeRequest;
import com.example.employeeservice.dto.EmployeeResponse;
import com.example.employeeservice.dto.UpdateEmployeeRequest;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.entity.Gender;
import com.example.employeeservice.exception.BadRequestException;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import com.example.employeeservice.mapper.EmployeeMapper;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del service.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper empleadoMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee empleado;
    private EmployeeResponse response;

    @BeforeEach
    void setUp() {
        empleado = new Employee(
                1L,
                "Luis",
                "Alberto",
                "Aguilar",
                "Andrade",
                Gender.MACULINO,
                LocalDate.of(1995, 1, 10),
                "Desarrollador",
                LocalDateTime.now(),
                true
        );

        response = new EmployeeResponse();
        response.setId(1L);
        response.setFirstName("Luis");
        response.setSecondName("Alberto");
        response.setPaternalLastName("Aguilar");
        response.setMaternalLastName("Andrade");
        response.setGender(Gender.MACULINO);
        response.setBirthDate(LocalDate.of(1995, 1, 10));
        response.setPosition("Desarrollador");
        response.setEstado(true);
    }

    @Test
    void shouldReturnEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(empleadoMapper.toResponse(empleado)).thenReturn(response);

        EmployeeResponse result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Luis", result.getFirstName());

        verify(employeeRepository).findById(1L);
        verify(empleadoMapper).toResponse(empleado);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(99L));

        verify(employeeRepository).findById(99L);
        verify(empleadoMapper, never()).toResponse(any());
    }

    @Test
    void shouldCreateEmployees() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("Luis");
        request.setSecondName("Alberto");
        request.setPaternalLastName("Aguilar");
        request.setMaternalLastName("Andrade");
        request.setGender(Gender.MACULINO);
        request.setBirthDate(LocalDate.of(1995, 1, 10));
        request.setAge(LocalDate.now().getYear() - 1995);
        request.setPosition("Desarrollador");
        request.setEstado(true);

        when(empleadoMapper.toEntity(request)).thenReturn(empleado);
        when(employeeRepository.saveAll(anyList())).thenReturn(List.of(empleado));
        when(empleadoMapper.toResponse(empleado)).thenReturn(response);

        List<EmployeeResponse> result = employeeService.createEmployees(List.of(request));

        assertEquals(1, result.size());
        assertEquals("Luis", result.get(0).getFirstName());

        verify(empleadoMapper).toEntity(request);
        verify(employeeRepository).saveAll(anyList());
        verify(empleadoMapper).toResponse(empleado);
    }

    @Test
    void shouldThrowBadRequestWhenAgeDoesNotMatchBirthDate() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("Luis");
        request.setPaternalLastName("Aguilar");
        request.setMaternalLastName("Andrade");
        request.setGender(Gender.MACULINO);
        request.setBirthDate(LocalDate.of(2000, 1, 1));
        request.setAge(99);
        request.setPosition("Desarrollador");

        assertThrows(BadRequestException.class, () -> employeeService.createEmployees(List.of(request)));

        verify(employeeRepository, never()).saveAll(anyList());
    }

    @Test
    void shouldUpdateEmployee() {
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setPosition("Desarrollador Java");
        request.setEstado(false);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(empleadoMapper.toResponse(any(Employee.class))).thenAnswer(invocation -> {
            Employee updated = invocation.getArgument(0);
            EmployeeResponse updatedResponse = new EmployeeResponse();
            updatedResponse.setId(updated.getId());
            updatedResponse.setFirstName(updated.getFirstName());
            updatedResponse.setPosition(updated.getPosition());
            updatedResponse.setEstado(updated.isEstado());
            return updatedResponse;
        });

        EmployeeResponse result = employeeService.updateEmployee(1L, request);

        assertEquals("Desarrollador Java", result.getPosition());
        assertFalse(result.isEstado());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(empleado);
        verify(empleadoMapper).toResponse(empleado);
    }
}