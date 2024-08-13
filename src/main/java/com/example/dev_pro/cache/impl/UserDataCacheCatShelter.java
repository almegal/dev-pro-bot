package com.example.dev_pro.cache.impl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.cache.DataCacheCatShelter;
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
public class UserDataCacheCatShelter implements DataCacheCatShelter {

    private final TelegramUserService service;
    private Map<Long, BotStateCatShelter> usersBotStates = new HashMap<>();
    private Map<Long, TelegramUser> telegramUsers = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotStateCatShelter botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotStateCatShelter getUsersCurrentBotState(Long userId) {
        BotStateCatShelter botState = usersBotStates.get(userId);
        return botState;
    }

    @Override
    public TelegramUser getTelegramUser(Long userId) {
        TelegramUser telegramUser = telegramUsers.get(userId);
        if (telegramUser == null) {
            telegramUser = service.getById(userId);
        }
        return telegramUser;
    }

    @Override
    public void saveTelegramUser(Long userId, TelegramUser telegramUser) {
        telegramUsers.put(userId, telegramUser);
    }

}
