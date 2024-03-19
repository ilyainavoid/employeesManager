package com.ilyanavoid.secondpractice.models;

import lombok.Data;

import java.util.List;
@Data
public class EmployeeListDto {
    private List<Employee> members;
    private int totalElements;

    public EmployeeListDto(List<Employee> members, int totalElements) {
        this.members = members;
        this.totalElements = totalElements;
    }
}
