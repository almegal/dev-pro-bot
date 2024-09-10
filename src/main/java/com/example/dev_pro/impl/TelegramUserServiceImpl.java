package com.example.dev_pro.impl;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.repository.TelegramUserRepository;
import com.example.dev_pro.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository repository;

    @Override
    public TelegramUser getTelegramById(Long id) {
        Optional<TelegramUser> userOptional = repository.findByTelegramId(id);
        return userOptional.orElseGet(TelegramUser::new);
    }

    @Override
    public TelegramUser getById(Long id) {
        Optional<TelegramUser> userOptional = repository.findById(id);
        return userOptional.orElseGet(TelegramUser::new);
    }

    @Override
    public void save(TelegramUser user) {
        repository.save(user);
    }

    @Override
    public void update(TelegramUser user) {
        repository.save(user);
    }

    @Override
    public TelegramUser findTelegramUserByAdopter(Adopter adopter) {
        return repository.findTelegramUserByAdopter(adopter).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<TelegramUser> getAll() {
        return repository.findAll();
    }


}
