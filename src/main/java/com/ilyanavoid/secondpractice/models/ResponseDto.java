package com.ilyanavoid.secondpractice.models;

import lombok.Data;

import java.util.UUID;
@Data
public class ResponseDto {
    private String status;
    private UUID id;

   public ResponseDto(String status, UUID id) {
        this.status = status;
        this.id = id;
   }
}
