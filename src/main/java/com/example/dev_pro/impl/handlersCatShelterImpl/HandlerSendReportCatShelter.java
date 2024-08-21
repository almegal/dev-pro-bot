package com.example.dev_pro.impl.handlersCatShelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.cache.impl.UserDataCacheCatShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.AdopterService;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.SendReportService;
import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerSendReportCatShelter implements InputMessageHandlerCatShelter {

    private final UserDataCacheCatShelter userDataCache;
    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final SendReportService sendReportService;
    private final AdopterService adopterService;
    private final PetService petService;

    @Override
    public SendMessage handle(Message message) {
        Long userId = message.from().id();
        BotStateCatShelter botState = userDataCache.getUsersCurrentBotState(userId);

        if (botState == null || botState.equals(BotStateCatShelter.SEND_REPORT)) {
            userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.SEND_PHOTO);
            return new SendMessage(message.chat().id(), "Пожалуйста, отправьте фото для отчета.");
        }

        return processUsersInput(message);
    }

    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.SEND_REPORT;
    }

    private SendMessage processUsersInput(Message message) {
        Long userId = message.from().id();
        Long chatId = message.chat().id();
        BotStateCatShelter botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotStateCatShelter.SEND_PHOTO)) {
            if (message.photo() != null) {
                String photoFilePath = sendReportService.savePhoto(message.photo());
                if (photoFilePath != null) {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setPhotoFilePath(photoFilePath);
                    userDataCache.saveTelegramUser(userId, telegramUser);
                    userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.SEND_TEXT);
                    replyToUser = new SendMessage(chatId, "Фото получено. Теперь отправьте текст для отчета.");
                } else {
                    replyToUser = new SendMessage(chatId, "Ошибка при сохранении фото. Пожалуйста, попробуйте снова.");
                }
            } else {
                replyToUser = new SendMessage(chatId, "Пожалуйста, отправьте фото.");
            }
        } else if (botState.equals(BotStateCatShelter.SEND_TEXT)) {
            if (message.text() != null) {
                TelegramUser telegramUser = userDataCache.getTelegramUser(userId);
                Adopter adopter = adopterService.findByTelegramUserId(userId);
                Pet pet = petService.findByAdopterId(adopter.getId());

                Report report = sendReportService.createReport(telegramUser.getPhotoFilePath(), message.text(), adopter, pet);
                if (report != null) {
                    replyToUser = new SendMessage(chatId, "Отчет успешно сохранен!");
                } else {
                    replyToUser = new SendMessage(chatId, "Ошибка при сохранении отчета. Пожалуйста, попробуйте снова.");
                }
                userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.SEND_REPORT);
            } else {
                replyToUser = new SendMessage(chatId, "Пожалуйста, отправьте текст.");
            }
        } else {
            replyToUser = new SendMessage(chatId, "Неизвестное состояние. Пожалуйста, начните сначала.");
            userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.SEND_REPORT);
        }

        telegramBot.execute(replyToUser);
        return replyToUser;
    }
}