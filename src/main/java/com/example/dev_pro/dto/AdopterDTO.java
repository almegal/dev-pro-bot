package com.example.dev_pro.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdopterDTO {

    private Long telegramUserId;

    private List<Long> petIds;


}
