package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.botapi.BotStateContextDogShelter;
import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.impl.UserDataCacheDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.handlersDogShelterImpl.HandlerSendReportDogShelter;
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
    private final HandlerSendReportDogShelter handlerSendReport;

    public DogShelterServiceImpl(TelegramBotConfiguration tBotConfig, @Lazy TelegramBotListener listener,
                                 UserDataCacheDogShelter userDataCache, BotStateContextDogShelter botStateContext,
                                 HandlerSendReportDogShelter handlerSendReport) {
        this.tBotConfig = tBotConfig;
        this.listener = listener;
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
        this.handlerSendReport = handlerSendReport;
    }
    
    @Override
    public void handleUpdate(Update update) {
        Message message = update.message();

        if (message.photo() != null) {
            handlerSendReport.processUsersInput(message);
        }

        if (update.message().text() == null) {
            return;
        }

        String text = update.message().text();
        Long chatId = update.message().chat().id();
        Long userId = message.from().id();

        BotStateDogShelter botState;
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
                break;
            case RECOMMENDATIONS_COM:
                botState = BotStateDogShelter.RECOMMENDATIONS_COM;
                break;
            case MAIN_COME_BACK_COM:
                botState = BotStateDogShelter.MAIN_COME_BACK_COM;
                break;
            case RECOMM_FOR_TRANSPORTING_THE_ANIMAL:
                botState = BotStateDogShelter.RECOMM_FOR_TRANSPORTING_THE_ANIMAL;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/RECOMM_FOR_TRANSPORTING_THE_ANIMAL.jpg");
                break;
            case TO_SET_UP_HOME_FOR_PUPPY:
                botState = BotStateDogShelter.TO_SET_UP_HOME_FOR_PUPPY;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/dog1.png");
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/dog2.png");
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/dog3.png");
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/dog4.png");
                break;
            case SETTING_UP_HOME_FOR_AN_ADULT_PET:
                botState = BotStateDogShelter.SETTING_UP_HOME_FOR_AN_ADULT_PET;
                listener.sendPhoto(chatId,tBotConfig.getSelectRecommMsg(),
                        "static/images/DogCat1.png");
                listener.sendPhoto(chatId,tBotConfig.getSelectRecommMsg(),
                        "static/images/DogCat2.png");
                listener.sendPhoto(chatId,tBotConfig.getSelectRecommMsg(),
                        "static/images/DogCat3.png");
                break;
            case PROVIDING_HOME_FOR_ANIMAL_DISABILITY:
                botState = BotStateDogShelter.PROVIDING_HOME_FOR_ANIMAL_DISABILITY;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/DisDog.png");
                break;
            case ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL:
                botState = BotStateDogShelter.ADVICE_HANDLER_INITIAL_COMMUN_ANIMAL;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/tipsSpecDogCat.png");
                break;
            case CONTACT_DETAILS_HANDLER:
                botState = BotStateDogShelter.CONTACT_DETAILS_HANDLER;
                listener.sendPhoto(chatId, tBotConfig.getSelectRecommMsg(),
                        "static/images/SpecDog.png");
                break;
            case RECOMM_COME_BACK_COM:
                botState = BotStateDogShelter.RECOMM_COME_BACK_COM;
                break;
            case REPORT_COM:
                botState = BotStateDogShelter.REPORT_COM;
                break;
            case REPORT_FORMAT:
                botState = BotStateDogShelter.REPORT_FORMAT;
                break;
            case SEND_PHOTO_REPORT:
                botState = BotStateDogShelter.SEND_PHOTO_REPORT;
                break;
            case LIST_DOCUMENTS_COM:
                botState = BotStateDogShelter.DOCUMENT_FOR_TAKE_ANIMAL_COM;
                break;
            case MEETING_ANIMALS_COM:
                botState = BotStateDogShelter.RULES_FOR_ANIMAL;
                break;
            case REASONS_REFUSAL_COM:
                botState = BotStateDogShelter.REASON_REFUSAL_COM;
                break;
            case REPORT_COME_BACK_COM:
                botState = BotStateDogShelter.REPORT_COME_BACK_COM;
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
