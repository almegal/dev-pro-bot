package com.example.dev_pro.impl.handlersDogShelterImpl;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.cache.impl.UserDataCacheDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.exception.InvalidInputException;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.TelegramUserService;
import com.example.dev_pro.service.VolunteerService;
import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.example.dev_pro.util.MessageUtil;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HandlerFillingUserProfileDogShelter implements InputMessageHandlerDogShelter {

    private final UserDataCacheDogShelter userDataCache;
    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final TelegramUserService telegramUserService;
    private final VolunteerService volunteerService;


    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.from().id()).equals(BotStateDogShelter.FILLING_PROFILE)) {
            // Если для пользователя текущее состояние бота равно FILLING_PROFILE
            userDataCache.setUsersCurrentBotState(message.from().id(), BotStateDogShelter.ASK_PERSONAL_DATA);
            // то устанавливаем для этого пользователя текущее состояние бота - ASK_PERSONAL_DATA,
            // то есть состояние для принятия ботом от пользователя своих личных данных в определенном формате
        }
        return processUsersInput(message);
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.text();
        Long userId = inputMsg.from().id();
        String userName = inputMsg.chat().username();
        Long chatId = inputMsg.chat().id();

        TelegramUser telegramUser = userDataCache.getTelegramUser(userId);
        // Извлекаем из мапы пользователя телеграм по идентификатору, извлеченному из сообщения
        BotStateDogShelter botState = userDataCache.getUsersCurrentBotState(userId);
        // Получаем из мапы для данного пользователя текущее состояние бота

        SendMessage replyToUser = null;

        if (botState.equals(BotStateDogShelter.ASK_PERSONAL_DATA)) {
            // Если для пользователя текущее состояние бота равно ASK_PERSONAL_DATA
            replyToUser = new SendMessage(chatId,tBotConfig.getUserContactMsgDogShelter());
            // то создаем ответное сообщение от бота к пользователю о введении своих личных данных в определенном формате
            userDataCache.setUsersCurrentBotState(userId, BotStateDogShelter.PROFILE_FILLED);
            // устанавливаем для пользователя состояние бота "Данные введены"
        }

        if (botState.equals(BotStateDogShelter.PROFILE_FILLED)) {

            try {
                // Распарсиваем строку - ответ пользователя о своих данных
                telegramUser = MessageUtil.parseCreateCommand(chatId, userName, usersAnswer);
                // Сохраняем новые данные пользователя в базу данных
                telegramUserService.update(telegramUser);

                // Отправляем всем волонтерам сообщение о внесенном в базу данных пользователе и его ожидании связи от волонтера
                messageToVolunteers(telegramUser);

                // Создаем сообщение пользователю об успешном создании анкеты и ожидании им связи с волонтером
                // и отправляем его
                replyToUser = new SendMessage(chatId, String.format(tBotConfig.getSuccessUserProfileMsgDogShelter(),
                        telegramUser.getFirstName()));

            } catch (InvalidInputException e) {
                replyToUser = new SendMessage(chatId, tBotConfig.getErrorMsg());

            }

            // Устанавливаем для пользователя состояние бота - меню /info из шести кнопок
            userDataCache.setUsersCurrentBotState(userId, BotStateDogShelter.INFO_COM);
        }
        // Сохраняем пользователя в мапу "telegramUsers"
        userDataCache.saveTelegramUser(userId, telegramUser);
        telegramBot.execute(replyToUser);
        return replyToUser;
    }

    /**
     * Метод по отправлению всем волонтерам сообщений о том, что пользователь оставил свои данные и ожидает обратной
     * связи от волонтеров. В сообщениях также содержится идентификатор пользователя, по которому волонтер может
     * отыскать этого пользователя в базе данных
     * @param telegramUser пользователь телеграм, оставивший свои данные для связи и внесенный в базу данных
     */
    private void messageToVolunteers(TelegramUser telegramUser) {
        List<String> listNickNamesVolunteers = volunteerService.getListNickNamesOfVolunteers();
        List<Volunteer> volunteers = (List<Volunteer>) volunteerService.findAllVolunteers();
        volunteers.forEach(volunteer -> telegramBot.execute(new SendMessage(volunteer.getChatId(),
                String.format(tBotConfig.getMessageToVolunteerMsg2(), telegramUser.getId()))));
    }

}
