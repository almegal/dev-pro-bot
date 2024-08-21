package com.example.dev_pro.impl;

import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.service.AdopterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdopterServiceImpl implements AdopterService {
    @Override
    public Adopter findByTelegramUserId(Long userId) {
        return null;
    }
}
