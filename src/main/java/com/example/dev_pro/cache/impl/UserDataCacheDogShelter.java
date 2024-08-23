package com.example.dev_pro.cache.impl;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.DataCacheDogShelter;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * usersBotStates: user_id and user's bot state
 * telegramUsers: user_id and telegram user
 */

@Component
@RequiredArgsConstructor
public class UserDataCacheDogShelter implements DataCacheDogShelter {

    private final TelegramUserService telegramUserService;
    private Map<Long, BotStateDogShelter> usersBotStates = new HashMap<>();
    private Map<Long, TelegramUser> telegramUsers = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotStateDogShelter botState) {
        usersBotStates.put(userId, botState);
        TelegramUser telegramUser = getTelegramUser(userId);
        if (telegramUser != null) {
            telegramUser.setBotStateDogShelter(botState);
            telegramUserService.save(telegramUser);
        }
    }

    @Override
    public BotStateDogShelter getUsersCurrentBotState(Long userId) {
        TelegramUser telegramUser = getTelegramUser(userId);
        if (telegramUser != null) {
            return telegramUser.getBotStateDogShelter();
        }
        return usersBotStates.get(userId);
    }

    @Override
    public TelegramUser getTelegramUser(Long userId) {
        TelegramUser telegramUser = telegramUsers.get(userId);
        if (telegramUser == null) {
            telegramUser = telegramUserService.findById(userId).orElse(null);
            if (telegramUser != null) {
                telegramUsers.put(userId, telegramUser);
            }
        }
        return telegramUser;
    }

    @Override
    public void saveTelegramUser(Long userId, TelegramUser telegramUser) {
        telegramUsers.put(userId, telegramUser);
        telegramUserService.save(telegramUser);
    }
}
