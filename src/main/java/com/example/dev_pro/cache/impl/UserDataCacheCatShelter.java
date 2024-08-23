package com.example.dev_pro.cache.impl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.cache.DataCacheCatShelter;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserDataCacheCatShelter implements DataCacheCatShelter {

    private final TelegramUserService telegramUserService;
    private Map<Long, BotStateCatShelter> usersBotStates = new HashMap<>();
    private Map<Long, TelegramUser> telegramUsers = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotStateCatShelter botState) {
        usersBotStates.put(userId, botState);
        TelegramUser telegramUser = getTelegramUser(userId);
        if (telegramUser != null) {
            telegramUser.setBotStateCatShelter(botState);
            telegramUserService.save(telegramUser);
        }
    }

    @Override
    public BotStateCatShelter getUsersCurrentBotState(Long userId) {
        TelegramUser telegramUser = getTelegramUser(userId);
        if (telegramUser != null) {
            return telegramUser.getBotStateCatShelter();
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