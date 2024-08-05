package com.example.dev_pro.cache.impl;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.DataCacheDogShelter;
import com.example.dev_pro.model.TelegramUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * usersBotStates: user_id and user's bot state
 * telegramUsers: user_id and telegram user
 */

@Component
@Slf4j
public class UserDataCacheDogShelter implements DataCacheDogShelter {

    private Map<Long, BotStateDogShelter> usersBotStates = new HashMap<>();
    private Map<Long, TelegramUser> telegramUsers = new HashMap<>();

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
            telegramUser = new TelegramUser();
        }
        return telegramUser;
    }

    @Override
    public void saveTelegramUser(Long userId, TelegramUser telegramUser) {
        telegramUsers.put(userId, telegramUser);
    }

}
