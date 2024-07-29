package com.example.dev_pro.impl;

import com.example.dev_pro.component.Buttons;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.CallbackService;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.service.shelter.CatShelterService;
import com.example.dev_pro.service.shelter.DogShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CallbackServiceMsgFromBtn implements CallbackService {

    private final TelegramBot telegramBot;
    private final CatShelterService catShelterService;
    private final DogShelterService dogShelterService;
    private final TelegramUserService userService;

    @Override
    public void handleCallback(Update update) {
        //Получаем идентификатор пользователя и сообщения
        Long userId = update.message().from().id();
        String shelter = update.message().text();
        Long chatId = update.message().chat().id();

        // создаем нового пользователя и устанавливаем ему значения полей
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setTelegramId(userId);
        telegramUser.setShelter(shelter);

        Keyboard keyboardToUser = null;
        // если выбрали приют
        if (shelter.equalsIgnoreCase(Buttons.CAT_BUTTON)) {
            // устанавливаем команды для приюта кошек
            keyboardToUser = catShelterService.getKeyboardCommands();
        } else if (shelter.equalsIgnoreCase(Buttons.DOG_BUTTON)) {
            // или для приюта собак
            keyboardToUser = dogShelterService.getKeyboardCommands();
        }
        SendMessage sendMessage = new SendMessage(chatId, "Nice choose, please choose button on keyboard and follow instructions")
                .replyMarkup(keyboardToUser);
        telegramBot.execute(sendMessage);
        userService.save(telegramUser);
    }
}
