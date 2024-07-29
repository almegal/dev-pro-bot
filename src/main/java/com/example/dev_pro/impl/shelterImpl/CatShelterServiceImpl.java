package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.shelter.CatShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dev_pro.component.impl.ShelterKeyBoardsButtons.*;

@RequiredArgsConstructor
@Service
public class CatShelterServiceImpl implements CatShelterService {

    private final TelegramBotConfiguration tBotConfig;
    private final ShelterKeyBoardsButtons buttons;
    private final TelegramBot telegramBot;

    @Override
    public Keyboard getKeyboardButtons() {
        return buttons.getKeyboardButtons();
    }

    @Override
    public void handleUpdate(Update update) {
        // получим идентификтор чата и команду
        Long chatId = update.message().chat().id();
        String text = update.message().text();
        // в зависимости от команды получим результат
        String resultMsg = handleCommand(text);
        // Отправим пользователю
        SendMessage sendMessage = new SendMessage(chatId, resultMsg);
        telegramBot.execute(sendMessage);
    }

    /**
     * Метод обработка команды и возврат результата.
     *
     * @param text текст команды.
     * @return сообщение для отправки.
     */
    public String handleCommand(String text) {
        return switch (text) {
            case INFO_COM -> tBotConfig.getInfoMsgCatShelter();
            case TAKE_COM -> tBotConfig.getTakeMsg();
            case REPORT_COM -> null; // Добавим процессе создание база данных!
            default -> tBotConfig.getErrorMsg();
        };
    }
}
