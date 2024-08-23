package com.example.dev_pro.impl;


import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.repository.AdopterRepository;
import com.example.dev_pro.service.AdopterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdopterServiceImpl implements AdopterService {

    private final AdopterRepository repository;


    @Override
    public Adopter findAdopterById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public Adopter findAdopterByTelegramUserId(Long telegramUserId) {
        return repository.findAdopterByTelegramUserId(telegramUserId).orElseThrow(EntityNotFoundException::new);
    }
}
