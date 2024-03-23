package com.ilyanavoid.secondpractice.repositories;

import com.ilyanavoid.secondpractice.models.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByName(String name);
    List<Employee> findByPosition(String position);
    List<Employee> findByNameAndPosition(String name, String position);
}
