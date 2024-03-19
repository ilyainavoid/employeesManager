package com.ilyanavoid.secondpractice.models;

import lombok.Data;

@Data
public class EmployeeDto {
    String id;
    String name;
    String position;

    public EmployeeDto(String id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }
}
