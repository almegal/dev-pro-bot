package com.example.dev_pro.impl.handlersDogShelterImpl;

import com.example.dev_pro.botapi.BotStateDogShelter;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.model.AvatarPet;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.service.AvatarPetService;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.handlers.InputMessageHandlerDogShelter;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerListAnimalsDogShelter implements InputMessageHandlerDogShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final PetService petService;
    private final AvatarPetService avatarService;

    private final static Integer DOG_SHELTER_ID = 2;
    private final static Boolean IS_FREE_STATUS = true;

    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - LIST_ANIMALS_COM. В результате
     * нажатия пользователем на кнопку list_animals_com бот отправляет пользователю сообщение с фотографиями
     * животных.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю от бота
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getListAnimalsMsgDogShelter());
        telegramBot.execute(replyMessage);
        sendPhotoByDataBase(chatId);
        return replyMessage;
    }

    @Override
    public BotStateDogShelter getHandlerName() {
        return BotStateDogShelter.LIST_ANIMALS_COM;
    }

    /**
     * Метод по получению из базы данных фотографий всех свободных питомцев из приюта для собак и отправлению их
     * пользователю
     * @param chatId идентификатор чата
     */

    public void sendPhotoByDataBase(Long chatId) {
        List<Pet> freeDogs = petService.findAllByShelterIdAndIsFreeStatus(DOG_SHELTER_ID, IS_FREE_STATUS);
        // Получили из базы данных список свободных питомцев из приюта для собак

        for (Pet dog : freeDogs) {
            AvatarPet avatar = avatarService.findAvatarByPetId(dog.getId());
            if (avatar != null) {
                try {
                    byte[] photo = Files.readAllBytes(Path.of(avatar.getFilePath()));
                    String caption = String.format("%s %s, возраст %d лет, пол %s",
                            dog.getPetType(), dog.getName(), dog.getAge(), dog.getSex());
                    SendPhoto sendPhoto = new SendPhoto(chatId, photo)
                            // создаем сообщение с файлом - фото
                            .caption(caption);
                    // по цепочке добавляем надпись под изображением
                    telegramBot.execute(sendPhoto);

                } catch (IOException e) {
                    log.error("Error reading avatar file for pet with id: {} ", dog.getId(), e);
                }
            }
        }
    }
}
