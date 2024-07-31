package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.listener.TelegramBotListener;
import com.example.dev_pro.service.shelter.CatShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.context.annotation.Lazy;
import com.example.dev_pro.service.shelter.CatShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dev_pro.component.impl.ShelterKeyBoardsButtons.*;

@Service
public class CatShelterServiceImpl implements CatShelterService {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;
    private final TelegramBotListener listener;

    public CatShelterServiceImpl(TelegramBotConfiguration tBotConfig, TelegramBot telegramBot,
                                 ShelterKeyBoardsButtons buttons, @Lazy TelegramBotListener listener) {
        this.tBotConfig = tBotConfig;
        this.telegramBot = telegramBot;
        this.buttons = buttons;
        this.listener = listener;
    }

    // устанавливаем главное меню приюта с 4 кнопками
 
    @Override
    public Keyboard getKeyboardButtons() {
        return buttons.getKeyboardButtons();
    }

    // устанавливаем меню info с 6 кнопками
 
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
        if (resultMsg.equalsIgnoreCase(tBotConfig.getInfoMsgCatShelter())) {
            // Отправим пользователю
            sendMessage = new SendMessage(chatId, resultMsg);
            sendMessage.replyMarkup(getInfoKeyboardButtons());
        } else if (resultMsg.equalsIgnoreCase(tBotConfig.getAddressMsgCatShelter())) {
            sendMessage = new SendMessage(chatId, resultMsg);
            listener.sendPhoto(chatId, tBotConfig.getDirectionsMsgCatShelter(),
                    "static/images/driving_directions_cat.jpg");
        } else if (resultMsg.equalsIgnoreCase(tBotConfig.getUserContactMsgCatShelter())) {
            sendMessage = new SendMessage(chatId, resultMsg);
            // нужно создать метод по получению от пользователя данных и сохранению их в базу данных

        } else if (resultMsg.equalsIgnoreCase(tBotConfig.getComeBackMsgCatShelter())) {
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
    public String handleCommand (String text){
        return switch (text) {
            case INFO_COM -> tBotConfig.getInfoMsgCatShelter();
            case OVERVIEW_COM -> tBotConfig.getOverviewMsgCatShelter();
            case ADDRESS_COM -> tBotConfig.getAddressMsgCatShelter();
            case CAR_PASS_COM -> tBotConfig.getCarPassMsgCatShelter();
            case SAFETY_RULES_COM -> tBotConfig.getSafetyRulesMsgCatShelter();
            case USER_CONTACT_COM -> tBotConfig.getUserContactMsgCatShelter();
            case COME_BACK_COM -> tBotConfig.getComeBackMsgCatShelter();
            case TAKE_COM -> tBotConfig.getTakeMsg();
            case REPORT_COM -> null; // Добавим в процессе создание база данных!
        };
    }
}
