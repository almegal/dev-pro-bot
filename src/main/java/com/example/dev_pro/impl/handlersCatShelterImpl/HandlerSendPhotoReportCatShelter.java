package com.example.dev_pro.impl.handlersCatShelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.cache.ReportDataCache;
import com.example.dev_pro.cache.impl.UserDataCacheCatShelter;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.AdopterService;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.ReportService;
import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;


@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerSendPhotoReportCatShelter implements InputMessageHandlerCatShelter {


    private final UserDataCacheCatShelter userDataCache;
    private final ReportDataCache reportDataCache;
    private final TelegramBot telegramBot;
    private final ReportService reportService;
    private final AdopterService adopterService;
    private final PetService petService;


    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.from().id()).equals(BotStateCatShelter.SEND_PHOTO_REPORT)) {
            // Если для пользователя текущее состояние бота равно SEND_PHOTO_REPORT
            userDataCache.setUsersCurrentBotState(message.from().id(), BotStateCatShelter.ASK_PET_ID_REPORT);
            // то устанавливаем для этого пользователя текущее состояние бота - ASK_PET_ID_REPORT,
            // то есть состояние для принятия ботом от усыновителя идентификатора питомца
        }
        return processUsersInput(message);
    }

    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.SEND_PHOTO_REPORT;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        Long userId = inputMsg.from().id();
        Long chatId = inputMsg.chat().id();
        String text = inputMsg.text();
        PhotoSize[] photoSizes = inputMsg.photo();

        TelegramUser telegramUser = userDataCache.getTelegramUser(userId);
        // Извлекаем из мапы пользователя телеграм по идентификатору, извлеченному из сообщения
        BotStateCatShelter botState = userDataCache.getUsersCurrentBotState(userId);
        // Получаем из мапы для данного пользователя текущее состояние бота
        Adopter adopter = adopterService.findAdopterByTelegramUserId(telegramUser.getId());
        Report report = reportDataCache.getReport(adopter.getId());
        // Извлекаем из мапы отчет по идентификатору усыновителя
        // Если в мапе отчет отсутствует, то он будет создан заново

        SendMessage replyToUser = null;
        SendMessage replyToUser2 = null;

        if (botState.equals(BotStateCatShelter.ASK_PET_ID_REPORT)) {
            // Если для пользователя текущее состояние бота равно ASK_PET_ID_REPORT
            replyToUser = new SendMessage(chatId, "Введите идентификатор питомца в формате: 12345");
            // то создаем ответное сообщение от бота к пользователю об отправлении идентификатора питомца
            userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.ASK_TEXT_REPORT);
            // устанавливаем для пользователя состояние бота по приему текста
        }

        if (botState.equals(BotStateCatShelter.ASK_TEXT_REPORT)) {
            try {
                report.setDateReport(LocalDate.now());
                report.setAdopter(adopter);
                report.setPet(petService.findPetById(Long.parseLong(text)));
                log.info("Report has been initialized! date_report = {}, adopter_id = {}, pet_id = {}",
                        report.getDateReport(), report.getAdopter(), report.getPet());

                replyToUser = new SendMessage(chatId, "Спасибо, идентификатор питомца загружен в отчет");
                replyToUser2 = new SendMessage(chatId, "Загрузите текст с описанием питомца");

            } catch (IllegalArgumentException e) {
                replyToUser = new SendMessage(chatId, "Сообщение должно содержать текст! " + e);
                userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.REPORT_COM);
            }
            userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.ASK_PHOTO_REPORT);
        }

        if (botState.equals(BotStateCatShelter.ASK_PHOTO_REPORT)) {
            try {
                report.setTextReport(text);
                log.info("Report has been initialized! text_report = {}", report.getTextReport());

                replyToUser = new SendMessage(chatId, "Спасибо, текст загружен в отчет");
                replyToUser2 = new SendMessage(chatId, "Загрузите файл с фотографией питомца");

            } catch (IllegalArgumentException e) {
                replyToUser = new SendMessage(chatId, "Сообщение должно содержать текст! " + e);
                userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.REPORT_COM);
            }

            userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.PHOTO_UPLOADED);
        }

        if (botState.equals(BotStateCatShelter.PHOTO_UPLOADED)) {
            try {
                Path path = reportService.uploadReportPhoto(photoSizes);
                // загружаем фото в папку на диске photos и сохраняем путь к этому файлу в переменную path
                report.setFilePath(path.toString());
                log.info("Report has been initialized! file_path = {} ", report.getFilePath());

                replyToUser = new SendMessage(chatId, "Спасибо, фотография загружена в отчет");

            } catch (IllegalArgumentException e) {
                replyToUser = new SendMessage(chatId, "Сообщение должно содержать фотографию! " + e);
                userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.REPORT_COM);
            } catch (IOException e) {
                replyToUser = new SendMessage(chatId, "Ошибка при загрузке фотографии! " + e);
                userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.REPORT_COM);
            }

            reportService.createReport(report);
            log.info("Report has been saved! id = {}, path = {}", report.getId(), report.getFilePath());

            userDataCache.setUsersCurrentBotState(userId, BotStateCatShelter.REPORT_COM);
            // Устанавливаем для пользователя состояние бота - меню /report из четырех кнопок
        }

        reportDataCache.saveReport(adopter.getId(), report);
        telegramBot.execute(replyToUser);
        telegramBot.execute(replyToUser2);
        return replyToUser;
    }

}
