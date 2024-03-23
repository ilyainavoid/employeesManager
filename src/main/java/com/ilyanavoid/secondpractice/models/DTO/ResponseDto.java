package com.ilyanavoid.secondpractice.models.DTO;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private UUID id;
}
