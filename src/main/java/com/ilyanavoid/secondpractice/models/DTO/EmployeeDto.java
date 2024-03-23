package com.ilyanavoid.secondpractice.models.DTO;

import io.micrometer.observation.transport.Propagator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    String id;
    String name;
    String position;
}
