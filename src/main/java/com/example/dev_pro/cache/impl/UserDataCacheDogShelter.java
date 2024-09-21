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

    private final TelegramUserService service;
    private final Map<Long, BotStateDogShelter> usersBotStates = new HashMap<>();
    private final Map<Long, TelegramUser> telegramUsers = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotStateDogShelter botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotStateDogShelter getUsersCurrentBotState(Long userId) {
        BotStateDogShelter botState = usersBotStates.get(userId);
        return botState;
    }

    @Override
    public TelegramUser getTelegramUser(Long userId) {
        TelegramUser telegramUser = telegramUsers.get(userId);
        if (telegramUser == null) {
            telegramUser = service.getTelegramById(userId);
        }
        return telegramUser;
    }

    @Override
    public void saveTelegramUser(Long userId, TelegramUser telegramUser) {
        telegramUsers.put(userId, telegramUser);
    }

}
