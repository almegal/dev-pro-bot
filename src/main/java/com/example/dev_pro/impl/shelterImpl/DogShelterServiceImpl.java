package com.example.dev_pro.impl.shelterImpl;

import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.exception.InvalidInputException;
import com.example.dev_pro.listener.TelegramBotListener;

import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.service.VolunteerService;
import com.example.dev_pro.service.shelter.DogShelterService;
import com.example.dev_pro.util.MessageUtil;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.context.annotation.Lazy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.dev_pro.component.impl.ShelterKeyBoardsButtons.*;


@Service
public class DogShelterServiceImpl implements DogShelterService {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final ShelterKeyBoardsButtons buttons;
    private final TelegramBotListener listener;
    private final TelegramUserService telegramUserService;
    private final VolunteerService volunteerService;

    public DogShelterServiceImpl(TelegramBotConfiguration tBotConfig, TelegramBot telegramBot,
                                 ShelterKeyBoardsButtons buttons, @Lazy TelegramBotListener listener,
                                 TelegramUserService telegramUserService, VolunteerService volunteerService) {
        this.tBotConfig = tBotConfig;
        this.telegramBot = telegramBot;
        this.buttons = buttons;
        this.listener = listener;
        this.telegramUserService = telegramUserService;
        this.volunteerService = volunteerService;
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
    public void handleUpdate (Update update) {
        Long chatId = update.message().chat().id();
        String text = update.message().text();
        SendMessage sendMessage;

        switch (text) {
            case INFO_COM:
                sendMessage = new SendMessage(chatId, tBotConfig.getInfoMsgDogShelter());
                sendMessage.replyMarkup(getInfoKeyboardButtons());
                telegramBot.execute(sendMessage);
                break;
            case OVERVIEW_COM:
                sendMessage = new SendMessage(chatId,tBotConfig.getOverviewMsgDogShelter());
                telegramBot.execute(sendMessage);
                break;
            case ADDRESS_COM:
                sendMessage = new SendMessage(chatId,tBotConfig.getDirectionsMsgDogShelter());
                listener.sendPhoto(chatId, tBotConfig.getDirectionsMsgCatShelter(),
                        "static/images/driving_directions_dog.jpg");
                telegramBot.execute(sendMessage);
                break;
            case CAR_PASS_COM:
                sendMessage = new SendMessage(chatId,tBotConfig.getCarPassMsgDogShelter());
                telegramBot.execute(sendMessage);
                break;
            case SAFETY_RULES_COM:
                sendMessage = new SendMessage(chatId,tBotConfig.getSafetyRulesMsgDogShelter());
                telegramBot.execute(sendMessage);
                break;
            case USER_CONTACT_COM:
                sendMessage = new SendMessage(chatId,tBotConfig.getUserContactMsgDogShelter());
                telegramBot.execute(sendMessage);
                break;
            case COME_BACK_COM:
                sendMessage = new SendMessage(chatId, tBotConfig.getComeBackMsgDogShelter());
                sendMessage.replyMarkup(getKeyboardButtons());
                telegramBot.execute(sendMessage);
                break;
            case TAKE_COM:
                sendMessage = new SendMessage(chatId,tBotConfig.getTakeMsg());
                telegramBot.execute(sendMessage);
                break;
            case REPORT_COM:
                final Object o = null; // Добавим в процессе создание база данных!
            default:
                createProfileUser(update);
        }
    }

    /**
     * Метод по созданию / обновлению пользователя и сохранению его в базу данных, извещению волонтеров о вновь
     * созданной анкете пользователя и отправлению пользователю сообщения об успешном создании его анкеты
     * @param update команду / сообщение от пользователя
     */

    private void createProfileUser(Update update) {
        Long chatId = update.message().chat().id();
        String userName = update.message().chat().username();
        String text = update.message().text();

        try {
            TelegramUser telegramUser = MessageUtil.parseCreateCommand(chatId, userName, text);
            // Сохраняем пользователя в базу данных
            telegramUserService.save(telegramUser);

            // Отправляем всем волонтерам сообщение о внесенном в базу данных пользователе и его ожидании связи от волонтера
            messageToVolunteers(telegramUser);

            // Создаем сообщение пользователю об успешном создании анкеты и ожидании им связи с волонтером
            // и отправляем его
            SendMessage sendMessage = new SendMessage(chatId, String.format(tBotConfig.getSuccessUserProfileMsgCatShelter(),
                    telegramUser.getFirstName()));
            telegramBot.execute(sendMessage);

        } catch (InvalidInputException e) {
            SendMessage sendMessage = new SendMessage(chatId, tBotConfig.getErrorMsg());
            telegramBot.execute(sendMessage);
        }
    }

    private void messageToVolunteers(TelegramUser telegramUser) {
        List<String> listNickNamesVolunteers = volunteerService.getListNickNamesOfVolunteers();
        List<Volunteer> volunteers = (List<Volunteer>) volunteerService.findAllVolunteers();
        volunteers.forEach(volunteer -> telegramBot.execute(new SendMessage(volunteer.getChatId(),
                String.format(tBotConfig.getMessageToVolunteerMsg2(), telegramUser.getId()))));
    }

}
