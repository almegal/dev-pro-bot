package com.example.dev_pro.impl;


import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.repository.AdopterRepository;
import com.example.dev_pro.service.AdopterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdopterServiceImpl implements AdopterService {
    private final AdopterRepository repository;

    @Override
    public List<Adopter> findAllWhoNotSendReportBeforeDay(LocalDate localDate) {
        return repository.findAdoptersWithoutReportForCurrentDate(localDate);
    }

    @Override
    public List<Adopter> findAllWhoPasserProbationPeriod() {
        return repository.findByProbationPeriodTrue();
    }

    @Override
    public Adopter create(Adopter adopter) {
        return repository.save(adopter);
    }

    @Override
    public Adopter update(Adopter adopter) {
        boolean isExist = repository.existsById(adopter.getId());
        if (!isExist) {
            repository.save(adopter);
        }
        return adopter;
    }

    @Override
    public Adopter getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Adopter findAdopterById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Adopter findAdopterByTelegramUserId(Long telegramUserId) {
        return repository.findAdopterByTelegramUserId(telegramUserId).orElseThrow(EntityNotFoundException::new);
    }
}
