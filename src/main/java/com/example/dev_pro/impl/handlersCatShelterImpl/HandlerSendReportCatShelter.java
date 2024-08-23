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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerSendReportCatShelter implements InputMessageHandlerCatShelter {

    private static final Logger logger = LoggerFactory.getLogger(HandlerSendReportCatShelter.class);

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
                logger.info("Фото получено от пользователя: {}", userId);
                String photoFilePath = sendReportService.savePhoto(message.photo());
                if (photoFilePath != null) {
                    logger.info("Фото успешно сохранено: {}", photoFilePath);
                    TelegramUser telegramUser = userDataCache.getTelegramUser(userId);
                    if (telegramUser == null) {
                        telegramUser = new TelegramUser();
                        telegramUser.setTelegramId(userId);
                    }
                    telegramUser.setPhotoFilePath(photoFilePath);
                    userDataCache.saveTelegramUser(userId, telegramUser);
                    userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.SEND_TEXT);
                    replyToUser = new SendMessage(chatId, "Фото получено. Теперь отправьте текст для отчета.");
                } else {
                    logger.error("Ошибка при сохранении фото для пользователя: {}", userId);
                    replyToUser = new SendMessage(chatId, "Ошибка при сохранении фото. Пожалуйста, попробуйте снова.");
                }
            } else {
                logger.warn("Пользователь не отправил фото: {}", userId);
                replyToUser = new SendMessage(chatId, "Пожалуйста, отправьте фото.");
            }
        } else if (botState.equals(BotStateCatShelter.SEND_TEXT)) {
            if (message.text() != null) {
                TelegramUser telegramUser = userDataCache.getTelegramUser(userId);
                if (telegramUser == null) {
                    logger.error("Пользователь Telegram не найден: {}", userId);
                    return new SendMessage(chatId, "Ошибка: пользователь не найден. Пожалуйста, начните сначала.");
                }

                Adopter adopter = adopterService.findByTelegramUserId(userId);
                if (adopter == null) {
                    logger.error("Усыновитель не найден для пользователя: {}", userId);
                    return new SendMessage(chatId, "Ошибка: усыновитель не найден. Пожалуйста, начните сначала.");
                }

                Pet pet = petService.findByAdopterId(adopter.getId());
                if (pet == null) {
                    logger.error("Питомец не найден для усыновителя: {}", adopter.getId());
                    return new SendMessage(chatId, "Ошибка: питомец не найден. Пожалуйста, начните сначала.");
                }

                Report report = sendReportService.createReport(telegramUser.getPhotoFilePath(), message.text(), adopter, pet);
                if (report != null) {
                    logger.info("Отчет успешно создан для пользователя: {}", userId);
                    replyToUser = new SendMessage(chatId, "Отчет успешно сохранен!");
                } else {
                    logger.error("Ошибка при создании отчета для пользователя: {}", userId);
                    replyToUser = new SendMessage(chatId, "Ошибка при сохранении отчета. Пожалуйста, попробуйте снова.");
                }
                userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.SEND_REPORT);
            } else {
                logger.warn("Пользователь не отправил текст: {}", userId);
                replyToUser = new SendMessage(chatId, "Пожалуйста, отправьте текст.");
            }
        } else {
            logger.warn("Неизвестное состояние для пользователя: {}", userId);
            replyToUser = new SendMessage(chatId, "Неизвестное состояние. Пожалуйста, начните сначала.");
            userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.SEND_REPORT);
        }

        telegramBot.execute(replyToUser);
        return replyToUser;
    }
}