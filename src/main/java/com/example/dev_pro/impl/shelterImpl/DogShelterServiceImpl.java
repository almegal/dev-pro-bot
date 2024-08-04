package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.botapi.BotStateContextDogShelter;
import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.impl.UserDataCacheDogShelter;
import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.exception.InvalidInputException;
import com.example.dev_pro.listener.TelegramBotListener;

import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.service.VolunteerService;
import com.example.dev_pro.service.shelter.ShelterService;
import com.example.dev_pro.util.MessageUtil;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.dev_pro.component.impl.ShelterKeyBoardsButtons.*;


@Service
public class DogShelterServiceImpl implements ShelterService {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;
    private final TelegramBotListener listener;
    private final TelegramUserService telegramUserService;
    private final VolunteerService volunteerService;
    private final UserDataCacheDogShelter userDataCache;
    private final BotStateContextDogShelter botStateContext;

    public DogShelterServiceImpl(TelegramBotConfiguration tBotConfig, TelegramBot telegramBot,
                                 ShelterKeyBoardsButtons buttons, @Lazy TelegramBotListener listener,
                                 TelegramUserService telegramUserService, VolunteerService volunteerService,
                                 UserDataCacheDogShelter userDataCache, BotStateContextDogShelter botStateContext) {
        this.tBotConfig = tBotConfig;
        this.telegramBot = telegramBot;
        this.buttons = buttons;
        this.listener = listener;
        this.telegramUserService = telegramUserService;
        this.volunteerService = volunteerService;
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
    }

    @Override
    public void handleUpdate(Update update) {
        Message message = update.message();
        Long chatId = update.message().chat().id();
        Long userId = message.from().id();
        String text = update.message().text();

        BotStateDogShelter botState = null;
        SendMessage replyMessage;

        switch (text) {
            case INFO_COM:
                botState = BotStateDogShelter.INFO_COM;
                break;
            case OVERVIEW_COM:
                botState = BotStateDogShelter.OVERVIEW_COM;
                break;
            case ADDRESS_COM:
                botState = BotStateDogShelter.ADDRESS_COM;
                listener.sendPhoto(chatId, tBotConfig.getDirectionsMsgDogShelter(),
                        "static/images/driving_directions_dog.jpg");
                break;
            case CAR_PASS_COM:
                botState = BotStateDogShelter.CAR_PASS_COM;
                break;
            case SAFETY_RULES_COM:
                botState = BotStateDogShelter.SAFETY_RULES_COM;
                break;
            case USER_CONTACT_COM:
                botState = BotStateDogShelter.FILLING_PROFILE;
                break;
            case COME_BACK_COM:
                botState = BotStateDogShelter.COME_BACK_COM;
                break;
            case TAKE_COM:
                botState = BotStateDogShelter.TAKE_COM;
                break;
            case REPORT_COM:
                botState = BotStateDogShelter.REPORT_COM;
                final Object o = null; // Добавим в процессе создание база данных!
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);
        // Устанавливаем для пользователя по его id, извлеченного из update, соответствующее состояние бота
        replyMessage = botStateContext.processInputMessage(botState, message);
        // Создаем ответное сообщение бота, исходя из состояния бота
    }


}
