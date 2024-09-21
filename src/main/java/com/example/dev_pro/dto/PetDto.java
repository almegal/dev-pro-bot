package com.example.dev_pro.dto;

import com.example.dev_pro.enums.PetType;
import com.example.dev_pro.enums.Sex;
import lombok.Data;

@Data
public class PetDto {
    private PetType petType;
    private String name;
    private Sex sex;
    private Integer age;
    private Boolean isFreeStatus;
    private Long shelterId;
}