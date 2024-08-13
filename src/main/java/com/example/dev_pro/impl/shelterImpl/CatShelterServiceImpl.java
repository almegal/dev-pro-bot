package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.botapi.BotStateContextCatShelter;
import com.example.dev_pro.cache.impl.UserDataCacheCatShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.listener.TelegramBotListener;
import com.example.dev_pro.service.shelter.ShelterService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static com.example.dev_pro.component.impl.ShelterKeyBoardsButtons.*;

@Service
public class CatShelterServiceImpl implements ShelterService {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBotListener listener;
    private final UserDataCacheCatShelter userDataCache;
    private final BotStateContextCatShelter botStateContext;

    public CatShelterServiceImpl(TelegramBotConfiguration tBotConfig, @Lazy TelegramBotListener listener,
                                 UserDataCacheCatShelter userDataCache, BotStateContextCatShelter botStateContext) {
        this.tBotConfig = tBotConfig;
        this.listener = listener;
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
    }

    @Override
    public void handleUpdate(Update update) {
        Message message = update.message();
        Long chatId = update.message().chat().id();
        Long userId = message.from().id();
        String text = update.message().text();

        BotStateCatShelter botState;
        SendMessage replyMessage;

        switch (text) {
            case INFO_COM:
                botState = BotStateCatShelter.INFO_COM;
                break;
            case OVERVIEW_COM:
                botState = BotStateCatShelter.OVERVIEW_COM;
                break;
            case ADDRESS_COM:
                botState = BotStateCatShelter.ADDRESS_COM;
                listener.sendPhoto(chatId, tBotConfig.getDirectionsMsgCatShelter(),
                        "static/images/driving_directions_cat.jpg");
                break;
            case CAR_PASS_COM:
                botState = BotStateCatShelter.CAR_PASS_COM;
                break;
            case SAFETY_RULES_COM:
                botState = BotStateCatShelter.SAFETY_RULES_COM;
                break;
            case USER_CONTACT_COM:
                botState = BotStateCatShelter.FILLING_PROFILE;
                break;
            case COME_BACK_COM:
                botState = BotStateCatShelter.COME_BACK_COM;
                break;
            case TAKE_COM:
                botState = BotStateCatShelter.TAKE_COM;
                break;
            case LIST_ANIMALS_COM:
                botState = BotStateCatShelter.LIST_ANIMALS_COM;
                break;
            case RECOMMENDATIONS_COM:
                botState = BotStateCatShelter.RECOMMENDATIONS_COM;
                break;
            case MAIN_COME_BACK_COM:
                botState = BotStateCatShelter.MAIN_COME_BACK_COM;
                break;
            case RECOMM_FOR_TRANSPORTING_THE_ANIMAL:
                botState = BotStateCatShelter.RECOMM_FOR_TRANSPORTING_THE_ANIMAL;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/RECOMM_FOR_TRANSPORTING_THE_ANIMAL.jpg");
                break;
            case TO_SET_UP_HOME_FOR_PUPPY:
                botState = BotStateCatShelter.TO_SET_UP_HOME_FOR_PUPPY;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/cat1.png");
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/cat2.png");
                break;
            case SETTING_UP_HOME_FOR_AN_ADULT_PET:
                botState = BotStateCatShelter.SETTING_UP_HOME_FOR_AN_ADULT_PET;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/DogCat1.png");
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/DogCat2.png");
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/DogCat3.png");
                break;
            case PROVIDING_HOME_FOR_ANIMAL_DISABILITY:
                botState = BotStateCatShelter.PROVIDING_HOME_FOR_ANIMAL_DISABILITY;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/DisCat.png");
                break;
            case ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL:
                botState = BotStateCatShelter.ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/tipsSpecDogCat.png");
                break;
            case CONTACT_DETAILS_HANDLER:
                botState = BotStateCatShelter.CONTACT_DETAILS_HANDLER;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/SpecCat.png");
            case RECOMM_COME_BACK_COM:
                botState = BotStateCatShelter.RECOMM_COME_BACK_COM;
                break;
            case REPORT_COM:
                botState = BotStateCatShelter.REPORT_COM;
                final Object o = null; // Добавим в процессе создание база данных!
                break;
            case MEETING_ANIMALS_COM:
                botState = BotStateCatShelter.RULES_FOR_ANIMAL;
                break;
            case LIST_DOCUMENTS_COM:
                botState = BotStateCatShelter.DOCUMENT_FOR_TAKE_ANIMAL_COM;
                break;
            case REASONS_REFUSAL_COM:
                botState = BotStateCatShelter.REASON_REFUSAL_COM;
                break;
            case REPORT_COME_BACK_COM:
                botState = BotStateCatShelter.REPORT_COME_BACK_COM;
                break;
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
