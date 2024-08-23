package com.example.dev_pro.cache.impl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.cache.DataCacheCatShelter;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.model.TelegramUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDataCacheCatShelter implements DataCacheCatShelter {

    private final TelegramUser telegramUser;
    private final TelegramUserService service;
    private Map<Long, BotStateCatShelter> usersBotStates = new HashMap<>();
    private Map<Long, TelegramUser> telegramUsers = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotStateCatShelter botState) {
        usersBotStates.put(userId, botState);
        TelegramUser telegramUser = getTelegramUser(userId);
        if (telegramUser != null) {
            telegramUser.setBotState(botState);
            service.update(telegramUser);
        }
    }

    @Override
    public BotStateCatShelter getUsersCurrentBotState(Long userId) {
        TelegramUser telegramUser = getTelegramUser(userId);
        if (telegramUser != null) {
            return telegramUser.getBotState();
        }
        return usersBotStates.get(userId);
    }

    @Override
    public TelegramUser getTelegramUser(Long userId) {
        TelegramUser telegramUser = telegramUsers.get(userId);
        if (telegramUser == null) {
            telegramUser = service.getById(userId);
            if (telegramUser != null) {
                telegramUsers.put(userId, telegramUser);
            }
        }
        return telegramUser;
    }

    @Override
    public void saveTelegramUser(Long userId, TelegramUser telegramUser) {
        telegramUsers.put(userId, telegramUser);
        service.save(telegramUser);
    }
}