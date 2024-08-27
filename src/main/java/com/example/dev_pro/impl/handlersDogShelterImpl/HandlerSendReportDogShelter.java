package com.example.dev_pro.impl.handlersDogShelterImpl;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.ReportDataCache;
import com.example.dev_pro.cache.impl.UserDataCacheDogShelter;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.AdopterService;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.ReportService;
import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerSendReportDogShelter implements InputMessageHandlerDogShelter {

    private final UserDataCacheDogShelter userDataCache;
    private final ReportDataCache reportDataCache;
    private final TelegramBot telegramBot;
    private final ReportService reportService;
    private final AdopterService adopterService;
    private final PetService petService;


    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.from().id()).equals(BotStateDogShelter.SEND_PHOTO_REPORT)) {
            userDataCache.setUsersCurrentBotState(message.from().id(), BotStateDogShelter.ASK_PET_ID_REPORT);
        }
        return processUsersInput(message);
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.SEND_PHOTO_REPORT;
    }

    public SendMessage processUsersInput(Message inputMsg) {
        Long userId = inputMsg.from().id();
        Long chatId = inputMsg.chat().id();
        String text = inputMsg.text();
        PhotoSize[] photoSizes = inputMsg.photo();

        TelegramUser telegramUser = userDataCache.getTelegramUser(userId);
        BotStateDogShelter botState = userDataCache.getUsersCurrentBotState(userId);
        Adopter adopter = adopterService.findAdopterByTelegramUserId(telegramUser.getId());
        Report report = reportDataCache.getReport(adopter.getId());

        SendMessage replyToUser = null;
        SendMessage replyToUser2 = null;

        if (botState.equals(BotStateDogShelter.ASK_PET_ID_REPORT)) {
            replyToUser = new SendMessage(chatId, "Введите идентификатор питомца в формате: 12345");
            userDataCache.setUsersCurrentBotState(userId, BotStateDogShelter.ASK_TEXT_REPORT);
        }

        if (botState.equals(BotStateDogShelter.ASK_TEXT_REPORT)) {
            try {
                report.setDateReport(LocalDate.now());
                report.setAdopter(adopter);
                report.setPet(petService.findPetById(Long.parseLong(text)));
                log.info("Report has been initialized! date_report = {}, adopter_id = {}, pet_id = {}",
                        report.getDateReport(), report.getAdopter(), report.getPet());

                replyToUser = new SendMessage(chatId, "Спасибо, идентификатор питомца загружен в отчет");
                replyToUser2 = new SendMessage(chatId, "Загрузите текст с описанием питомца");

            } catch (IllegalArgumentException e) {
                replyToUser = new SendMessage(chatId, "Сообщение должно содержать цифры! Кликните на кнопку " +
                        "\"send_photo_report\" и введите идентификатор питомца еще раз " + e);
            }
            userDataCache.setUsersCurrentBotState(userId, BotStateDogShelter.ASK_PHOTO_REPORT);
        }

        if (botState.equals(BotStateDogShelter.ASK_PHOTO_REPORT)) {
            try {
                report.setTextReport(text);
                log.info("Report has been initialized! text_report = {}", report.getTextReport());

                replyToUser = new SendMessage(chatId, "Спасибо, текст загружен в отчет");
                replyToUser2 = new SendMessage(chatId, "Загрузите файл с фотографией питомца");

            } catch (IllegalArgumentException e) {
                replyToUser = new SendMessage(chatId, "Сообщение должно содержать текст! Кликните на кнопку " +
                        "\"send_photo_report\" и введите все данные еще раз " + e);
            }
            userDataCache.setUsersCurrentBotState(userId, BotStateDogShelter.PHOTO_UPLOADED);
        }

        if (botState.equals(BotStateDogShelter.PHOTO_UPLOADED)) {
            try {
                Path path = reportService.uploadReportPhoto(photoSizes);
                report.setFilePath(path.toString());
                report.setMediaType(Files.probeContentType(path));
                report.setFileSize(photoSizes[photoSizes.length - 1].fileSize());
                log.info("Report has been initialized! file_path = {} ", report.getFilePath());

                replyToUser = new SendMessage(chatId, "Спасибо, фотография загружена в отчет");

            } catch (IllegalArgumentException e) {
                replyToUser = new SendMessage(chatId, "Сообщение должно содержать файл с фотографией! Кликните на кнопку " +
                        "\"send_photo_report\" и введите все данные еще раз " + e);
            } catch (IOException e) {
                replyToUser = new SendMessage(chatId, "Не удалось загрузить файл!");
                log.error("Error uploading photo file for report with fileId: {} ",
                        photoSizes[photoSizes.length - 1].fileId(), e);
            }

            reportService.createReport(report);
            log.info("Report has been saved! id = {}, path = {}", report.getId(), report.getFilePath());

            userDataCache.setUsersCurrentBotState(userId, BotStateDogShelter.REPORT_COM);
        }

        reportDataCache.saveReport(adopter.getId(), report);
        telegramBot.execute(replyToUser);
        telegramBot.execute(replyToUser2);
        return replyToUser;
    }
}
