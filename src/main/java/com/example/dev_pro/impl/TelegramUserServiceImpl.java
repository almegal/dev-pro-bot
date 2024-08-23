package com.example.dev_pro.impl;

import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.repository.TelegramUserRepository;
import com.example.dev_pro.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;

    @Override
    public TelegramUser getById(Long id) {
        Optional<TelegramUser> userOptional = telegramUserRepository.findByTelegramId(id);
        return userOptional.orElseGet(TelegramUser::new);
    }

    @Override
    public void save(TelegramUser user) {
        telegramUserRepository.save(user);
    }

    @Override
    public void update(TelegramUser user) {
        telegramUserRepository.save(user);
    }

    @Override
    public Optional<TelegramUser> findById(Long userId) {
        return telegramUserRepository.findById(userId);
    }
}
