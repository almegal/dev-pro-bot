package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.service.shelter.DogShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DogShelterServiceImpl implements DogShelterService {
    private static final String INFO_COM = "/info";
    private static final String TAKE_COM = "/take";
    private static final String REPORT_COM = "/report";
    private final TelegramBotConfiguration tBotConfig;

    private final TelegramBot telegramBot;

    @Override
    public void setCommands() {
        // Создадим список комманд
        BotCommand[] commands = {
                new BotCommand("/info", "Get info about dogs shelter")
                , new BotCommand("/report", "Get report about dogs")
                , new BotCommand("/take", "Get instruction to take dogs")
        };
        // Установим комманды в чате с пользователем
        SetMyCommands set = new SetMyCommands(commands).scope(new BotCommandScopeDefault());
        telegramBot.execute(set);
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
            case INFO_COM -> tBotConfig.getInfoMsgDogShelter();
            case TAKE_COM -> tBotConfig.getTakeMsg();
            case REPORT_COM -> null; // Добавим процессе создание база данных!
            default -> tBotConfig.getErrorMsg();
        };
    }
}
