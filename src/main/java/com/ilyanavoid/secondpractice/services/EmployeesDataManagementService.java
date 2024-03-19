package com.ilyanavoid.secondpractice.services;

import com.ilyanavoid.secondpractice.models.Employee;
import com.ilyanavoid.secondpractice.models.EmployeeDto;

import java.util.List;
import java.util.UUID;

public interface EmployeesDataManagementService {
    UUID createEmployee(String id, String name, String position);

    List<Employee> getAllEmployees(String name, String position);

    UUID deleteEmployee(String id);
    Employee getEmployee(String id);
    UUID updateEmployee(String id, EmployeeDto data);
}
