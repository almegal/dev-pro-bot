package com.example.dev_pro.impl;

import com.example.dev_pro.component.Buttons;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.service.CallbackService;
import com.example.dev_pro.service.TelegramUserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dev_pro.component.impl.ChoosingKeyboardButtons.CAT_BUTTON;
import static com.example.dev_pro.component.impl.ChoosingKeyboardButtons.DOG_BUTTON;

@RequiredArgsConstructor
@Service
public class CallbackServiceMsgFromBtn implements CallbackService {

    private final TelegramBot telegramBot;
    private final TelegramUserService userService;
    private final Buttons buttons;


    @Override
    public void handleCallback(Update update) {
        // Получаем идентификатор пользователя и сообщения
        Long userId = update.message().from().id();
        Long chatId = update.message().chat().id();
        String nickName = update.message().chat().username();
        String shelter = update.message().text();

        // создаем нового пользователя и устанавливаем ему значения полей
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setId(0L);
        telegramUser.setTelegramId(userId);
        telegramUser.setChatId(chatId);
        telegramUser.setNickName(nickName);
        telegramUser.setShelter(shelter);
        
        Keyboard keyboardToUser = null;
        // если выбрали приют
        if (shelter.equalsIgnoreCase(CAT_BUTTON)) {
            // устанавливаем команды для приюта кошек
            keyboardToUser = buttons.getKeyboardButtons();
        } else if (shelter.equalsIgnoreCase(DOG_BUTTON)) {
            // или для приюта собак
            keyboardToUser = buttons.getKeyboardButtons();
        }
        //
        SendMessage sendMessage = new SendMessage(chatId, "Nice choose, please choose button on keyboard and follow instructions")
                .replyMarkup(keyboardToUser);
        telegramBot.execute(sendMessage);
        //
        userService.save(telegramUser);
    }
}
