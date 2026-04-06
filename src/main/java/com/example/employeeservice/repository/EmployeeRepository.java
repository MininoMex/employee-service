package com.example.employeeservice.repository;

import com.example.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
            SELECT e
            FROM Employee e
            WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(COALESCE(e.secondName, '')) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(e.paternalLastName) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(e.maternalLastName) LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    List<Employee> searchByName(@Param("name") String name);
}