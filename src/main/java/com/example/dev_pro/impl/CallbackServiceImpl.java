package com.example.dev_pro.impl;

import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.repository.TelegramUserRepository;
import com.example.dev_pro.service.CallbackService;
import com.example.dev_pro.service.shelter.CatShelterService;
import com.example.dev_pro.service.shelter.DogShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CallbackServiceImpl implements CallbackService {

    private final TelegramBot telegramBot;
    private final TelegramUserRepository telegramUserRepository;
    private final CatShelterService catShelterService;
    private final DogShelterService dogShelterService;

    @Override
    public void handleCallback(Update update) {
        //Получаем идентификатор пользователя, сообщения и чата
        Long userId = update.callbackQuery().from().id();
        Long chatId = update.callbackQuery().message().chat().id();
        Integer messageId = update.callbackQuery().message().messageId();

        // Получаем переменную с данными из колбека
        String shelter = update.callbackQuery().data();

        // создаем нового пользователя и устанавливаем ему значения полей
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setTelegramId(userId);
        telegramUser.setShelter(shelter);

        // если из колбека выбрали приют
        if (shelter.equalsIgnoreCase("cat")) {
            // устанавливаем команды для приюта кошек
            catShelterService.setCommands();
        } else if (shelter.equalsIgnoreCase("dog")) {
            // или для приюта собак
            dogShelterService.setCommands();
        }

        // Удаляем кнопкки
        removeButtons(chatId, messageId);
        // Сообщаем пользователю о хорошем выборе
        SendMessage sendMessage = new SendMessage(chatId, "Nice choose! Use Command to make request");
        telegramBot.execute(sendMessage);

        // сохраняем пользователя
        telegramUserRepository.save(telegramUser);
    }

    private void removeButtons(Long chatId, Integer messageId) {
        // удаляем кнопки
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup(chatId, messageId);
        telegramBot.execute(editMessageReplyMarkup);
    }
}
