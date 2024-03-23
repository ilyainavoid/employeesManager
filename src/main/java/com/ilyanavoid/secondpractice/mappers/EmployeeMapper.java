package com.ilyanavoid.secondpractice.mappers;

import com.ilyanavoid.secondpractice.models.DTO.EmployeeDto;
import com.ilyanavoid.secondpractice.models.Entities.Employee;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface EmployeeMapper {
    EmployeeDto employeeToEmployeeDTO(Employee source);

    Employee employeeDTOToEmployee(EmployeeDto destination);

    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
}
