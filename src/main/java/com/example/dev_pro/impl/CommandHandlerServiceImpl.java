package com.example.dev_pro.impl;

import com.example.dev_pro.component.impl.ChoosingKeyboardButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.impl.shelterImpl.CatShelterServiceImpl;
import com.example.dev_pro.impl.shelterImpl.DogShelterServiceImpl;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.CommandHandlerService;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {

    private static final String CALL_VOLUNTEER = "/call";
    private static Boolean IS_CAT;

    private final TelegramBot telegramBot;
    private final TelegramBotConfiguration tBotConfig;
    private final VolunteerService volunteerService;
    private final TelegramUserService telegramUserService;
    private final CatShelterServiceImpl catShelterService;
    private final DogShelterServiceImpl dogShelterService;
    private final ChoosingKeyboardButtons choosingKeyboardButtons;

    /**
     * Метод обработка команды и возврат результата.
     *
     * @param text текст команды.
     * @return сообщение для отправки.
     */
    public String handleCommand(String text) {
        if (text.equalsIgnoreCase(CALL_VOLUNTEER)) {
            return tBotConfig.getCallVolunteerMsg();

        }
        return tBotConfig.getErrorMsg();
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

        if (update.message().photo() != null) {
            if (IS_CAT) {
                catShelterService.handleUpdate(update);
            } else {
                dogShelterService.handleUpdate(update);
            }
        }

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
        // Если отправлена команда вызова волонтера
        if (text.equals(CALL_VOLUNTEER)) {
            List<String> listNickNamesVolunteers = volunteerService.getListNickNamesOfVolunteers();
            sendMsg(chatId, resultMsg + " " + listNickNamesVolunteers);
            List<Volunteer> volunteers = (List<Volunteer>) volunteerService.findAllVolunteers();
            volunteers.forEach(volunteer -> sendMsg(volunteer.getChatId(),
                    String.format(tBotConfig.getMessageToVolunteerMsg(), userName, chatId)));
            return;
        }

        // Если дошли до сюда, значит пользователь уже обращался
        // Получаем приют, который выбрал пользователь
        String shelter = telegramUser.getShelter();
        // в зависимости от приюта уже вызываем соответствующий сервис для обработки команд
        if (shelter.equalsIgnoreCase(ChoosingKeyboardButtons.CAT_BUTTON)) {
            catShelterService.handleUpdate(update);
            IS_CAT = true;
        } else if (shelter.equalsIgnoreCase(ChoosingKeyboardButtons.DOG_BUTTON)) {
            dogShelterService.handleUpdate(update);
            IS_CAT = false;
        }
    }

    /**
     * Метод отправки сообщения.
     *
     * @param chatId идентификатор чата.
     * @param msg    сообщение для отправки.
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
        Keyboard buttons = choosingKeyboardButtons.getKeyboardButtons();
        // записываем это в наше сообщение и отправляем
        sendMessage.replyMarkup(buttons);
        telegramBot.execute(sendMessage);
    }
}
