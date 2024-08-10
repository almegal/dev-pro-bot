package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.botapi.BotStateContextDogShelter;
import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.impl.UserDataCacheDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersDogShelterImpl.HandlerListAnimalsDogShelter;
import com.example.dev_pro.listener.TelegramBotListener;
import com.example.dev_pro.service.shelter.ShelterService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static com.example.dev_pro.component.impl.ShelterKeyBoardsButtons.*;

@Service
public class DogShelterServiceImpl implements ShelterService {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBotListener listener;
    private final UserDataCacheDogShelter userDataCache;
    private final BotStateContextDogShelter botStateContext;
    private final HandlerListAnimalsDogShelter handlerDogShelter;

    public DogShelterServiceImpl(TelegramBotConfiguration tBotConfig, @Lazy TelegramBotListener listener,
                                 UserDataCacheDogShelter userDataCache, BotStateContextDogShelter botStateContext,
                                 HandlerListAnimalsDogShelter handlerDogShelter) {
        this.tBotConfig = tBotConfig;
        this.listener = listener;
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
        this.handlerDogShelter = handlerDogShelter;
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
            case LIST_ANIMALS_COM:
                botState = BotStateDogShelter.LIST_ANIMALS_COM;
                handlerDogShelter.sendPhotoByDataBase(chatId);
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
