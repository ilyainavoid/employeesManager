package com.ilyanavoid.secondpractice.models.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Employee {
    @Id
    private UUID id;
    private String name;
    private String position;

    public Employee(UUID id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Employee() {

    }
}
