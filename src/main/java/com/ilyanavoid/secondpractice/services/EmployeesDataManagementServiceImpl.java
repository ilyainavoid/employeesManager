package com.ilyanavoid.secondpractice.services;

import com.ilyanavoid.secondpractice.models.Entities.Employee;
import com.ilyanavoid.secondpractice.models.DTO.EmployeeDto;
import com.ilyanavoid.secondpractice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeesDataManagementServiceImpl implements EmployeesDataManagementService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UUID createEmployee(String idString, String name, String position) {
        UUID id = idString == null ? UUID.randomUUID() : UUID.fromString(idString);
        Employee employee = new Employee(id, name, position);
        employeeRepository.save(employee);
        return id;
    }

    @Override
    public List<Employee> getAllEmployees(String name, String position) {
        if (name != null && position != null) {
            return employeeRepository.findByNameAndPosition(name, position);
        }
        else if (name == null && position != null) {
            return employeeRepository.findByPosition(position);
        }
        else if (name != null && position == null) {
            return employeeRepository.findByName(name);
        }
        else {
            List<Employee> list = employeeRepository.findAll();
            return list;
        }
    }

    @Override
    public UUID deleteEmployee(String id) {
        employeeRepository.deleteById(UUID.fromString(id));
        return UUID.fromString(id);
    }

    @Override
    public Employee getEmployee(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(UUID.fromString(id));
        if (employeeOptional.isPresent()) {
            return employeeOptional.get();
        }
        else {
            return null;
        }
    }

    @Override
    public UUID updateEmployee(String id, EmployeeDto data) {
        Optional<Employee> employeeOptional = employeeRepository.findById(UUID.fromString(id));
        if (employeeOptional.isPresent()) {
            Employee target = employeeOptional.get();
            String name = data.getName();
            String position = data.getPosition();
            if (name != null) {
                target.setName(name);
            }
            if (position != null) {
                target.setPosition(position);
            }
            employeeRepository.save(target);
            return target.getId();
        }
        else {
            return null;
        }
    }
}
