package com.example.dev_pro.impl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.CommandHandlerService;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.service.VolunteerService;
import com.example.dev_pro.service.shelter.CatShelterService;
import com.example.dev_pro.service.shelter.DogShelterService;
import com.example.dev_pro.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {

    private static final String START_COM = "/start";
    private static final String INFO_COM = "/info";
    private static final String TAKE_COM = "/take";
    private static final String REPORT_COM = "/report";
    private static final String CALL_VOLUNTEER = "/call";

    private final TelegramBot telegramBot;
    private final TelegramBotConfiguration tBotConfig;
    private final VolunteerService volunteerService;
    private final TelegramUserService telegramUserService;
    private final CatShelterService catShelterService;
    private final DogShelterService dogShelterService;

    /**
     * Метод обработка команды и возврат результата.
     * @param chatId идентификатор чата.
     * @param text текст команды.
     * @return сообщение для отправки.
     */
    public String handleCommand(String text) {
        return switch (text) {
            case START_COM -> tBotConfig.getStartMsg();
            case INFO_COM -> tBotConfig.getInfoMsg();
            case TAKE_COM -> tBotConfig.getTakeMsg();
            case REPORT_COM -> null; // Добавим процессе создание база данных!
            case CALL_VOLUNTEER -> tBotConfig.getCallVolunteerMsg();
            default -> tBotConfig.getErrorMsg();
        };
    }

    /**
     * Данный метод извлекает из сообщения / команды пользователя идентификатор чата, никнейм и текст команды.
     * В результате вызова метода handleCommand() создается ответное сообщение пользователю от бота.
     * Если пользователь наберет команду /call, то в результате вызова методов из класса VolunteerServiceImpl
     * из базы данных будет получен список никнеймов волонтеров и отправлен пользователю, одновременно всем волонтерам
     * будет отправлено сообщение о вызове и никнейм и идентификатор чата данного пользователя.
     * В отсутствие приведенных выше условий пользователю отправляется ранее созданное ответное сообщение.
     *
     * @param update сообщение / команда пользователя
     */
    @Override
    public void commandProcessing(Update update) {
        Long chatId = update.message().chat().id();
        String userName = update.message().chat().username();
        String text = update.message().text();
        String resultMsg = handleCommand(text);

        // Получаем идентификатор пользователя
        Long userId = update.message().from().id();
        // Получаем пользователя телеграмм, если он не найден вернется новый
        TelegramUser telegramUser = telegramUserService.getById(userId);
        // Если такого пользователя нет, значит он ранее не обращался
        if (telegramUser.getTelegramId() == null) {
            // отправляем стартовое сообщение
            sendStartMessage(chatId);
            return;
        }
        // Если дошли до сюда значит пользователь уже обращался
        // Получаем приют который выбрал пользователь
        String shelter = telegramUser.getShelter();
        // в зависимости от приюта уже вызываем соответствующий сервис для обработки команд
        if (shelter.equalsIgnoreCase("cat")) {
            catShelterService.handleUpdate(update);
            return;
        } else if (shelter.equalsIgnoreCase("dog")) {
            dogShelterService.handleUpdate(update);
            return;
        }

        if (text.equals(CALL_VOLUNTEER)) {
            List<String> listNickNamesVolunteers = volunteerService.getListNickNamesOfVolunteers();
            sendMsg(chatId, resultMsg + " " + listNickNamesVolunteers);
            List<Volunteer> volunteers = (List<Volunteer>) volunteerService.findAllVolunteers();
            volunteers.forEach(volunteer -> sendMsg(volunteer.getChatId(),
                    String.format(tBotConfig.getMessageToVolunteerMsg(), userName, chatId)));
        } else {
            sendMsg(chatId, resultMsg);
        }
    }

    /**
     * Метод отправки сообщения.
     * @param chatId идентификатор чата.
     * @param msg сообщение для отправки.
     */
    @Override
    public void sendMsg(Long chatId, String msg) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        telegramBot.execute(sendMessage);
    }

    // Приветственное сообщение для  новых пользователей
    private void sendStartMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, tBotConfig.getStartMsg());
        // Установим кнопки для пользователя чтобы мог выбрать приют
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Cat").callbackData("cat"),
                new InlineKeyboardButton("Dog").callbackData("dog")
        );
        // записываем это в наше сообщение и отправляем
        sendMessage.replyMarkup(inlineKeyboardMarkup);
        telegramBot.execute(sendMessage);
    }

}
