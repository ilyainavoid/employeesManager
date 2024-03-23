package com.ilyanavoid.secondpractice.models.DTO;

import com.ilyanavoid.secondpractice.models.Entities.Employee;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListDto {
    private List<Employee> members;
    private int totalElements;
}
