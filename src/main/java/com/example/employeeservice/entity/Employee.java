package com.example.employeeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Entidad que representa la tabla Empleado.
 */
@Entity
@Table(
        name = "Employee",
        indexes = {
                @Index(name = "idx_employee_first_name", columnList = "first_name"),
                @Index(name = "idx_employee_paternal_last_name", columnList = "paternal_last_name")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "second_name", length = 100)
    private String secondName;

    @Column(name = "paternal_last_name", nullable = false, length = 100)
    private String paternalLastName;

    @Column(name = "maternal_last_name", nullable = false, length = 100)
    private String maternalLastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 20)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "position", nullable = false, length = 120)
    private String position;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "estado", nullable = false)
    private boolean estado = true;
    @Transient
    public Integer getEdad() {
        if (birthDate == null) {
            return null;
        }
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }
}