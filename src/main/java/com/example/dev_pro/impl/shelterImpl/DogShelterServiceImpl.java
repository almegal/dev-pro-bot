package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.listener.TelegramBotListener;

import com.example.dev_pro.service.shelter.DogShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.context.annotation.Lazy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dev_pro.component.impl.ShelterKeyBoardsButtons.*;


@Service
public class DogShelterServiceImpl implements DogShelterService {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;
    private final TelegramBotListener listener;

    public DogShelterServiceImpl(TelegramBotConfiguration tBotConfig, TelegramBot telegramBot,
                                 ShelterKeyBoardsButtons buttons, @Lazy TelegramBotListener listener) {
        this.tBotConfig = tBotConfig;
        this.telegramBot = telegramBot;
        this.buttons = buttons;
        this.listener = listener;
    }


    @Override
    public Keyboard getKeyboardButtons() {
        return buttons.getKeyboardButtons();
    }

    @Override
    public Keyboard getInfoKeyboardButtons() {
        return buttons.getInfoKeyboardButtons();
    }

    @Override
    public void handleUpdate(Update update) {
        // получим идентификтор чата и команду
        Long chatId = update.message().chat().id();
        String text = update.message().text();
        // в зависимости от команды получим результат
        String resultMsg = handleCommand(text);
        SendMessage sendMessage;
        if (resultMsg.equalsIgnoreCase(tBotConfig.getInfoMsgDogShelter())) {
            // Отправим пользователю
            sendMessage = new SendMessage(chatId, resultMsg);
            sendMessage.replyMarkup(getInfoKeyboardButtons());
        } else if (resultMsg.equalsIgnoreCase(tBotConfig.getAddressMsgDogShelter())) {
            sendMessage = new SendMessage(chatId, resultMsg);
            listener.sendPhoto(chatId, tBotConfig.getDirectionsMsgDogShelter(),
                    "static/images/driving_directions_dog.jpg");
        } else if (resultMsg.equalsIgnoreCase(tBotConfig.getUserContactMsgDogShelter())) {
            sendMessage = new SendMessage(chatId, resultMsg);
            // нужно создать метод по получению от пользователя данных и сохранению их в базу данных

        } else if (resultMsg.equalsIgnoreCase(tBotConfig.getComeBackMsgDogShelter())) {
            sendMessage = new SendMessage(chatId, resultMsg);
            sendMessage.replyMarkup(getKeyboardButtons());
        } else {
            sendMessage = new SendMessage(chatId, resultMsg);
        }
        telegramBot.execute(sendMessage);
    }

    /**
     * Метод обработка команды и возврат результата.
     *
     * @param text текст команды.
     * @return сообщение для отправки.
     */
    @Override
    public String handleCommand(String text) {
        return switch (text) {
            case INFO_COM -> tBotConfig.getInfoMsgDogShelter();
            case OVERVIEW_COM -> tBotConfig.getOverviewMsgDogShelter();
            case ADDRESS_COM -> tBotConfig.getAddressMsgDogShelter();
            case CAR_PASS_COM -> tBotConfig.getCarPassMsgDogShelter();
            case SAFETY_RULES_COM -> tBotConfig.getSafetyRulesMsgDogShelter();
            case USER_CONTACT_COM -> tBotConfig.getUserContactMsgDogShelter();
            case COME_BACK_COM -> tBotConfig.getComeBackMsgDogShelter();
            case TAKE_COM -> tBotConfig.getTakeMsg();
            case REPORT_COM -> null; // Добавим в процессе создание база данных!
            default -> tBotConfig.getErrorMsg();
        };
    }          
}
