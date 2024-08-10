package com.example.dev_pro.impl.handlersCatShelterImpl;

import com.example.dev_pro.botapi.BotStateCatShelter;
import com.example.dev_pro.component.impl.ShelterKeyBoardsButtons;
import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.model.AvatarPet;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.service.AvatarPetService;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.handlers.InputMessageHandlerCatShelter;
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
public class HandlerListAnimalsCatShelter implements InputMessageHandlerCatShelter {

    private final TelegramBotConfiguration tBotConfig;
    private final TelegramBot telegramBot;
    private final PetService petService;
    private final AvatarPetService avatarService;

    private final static Integer CAT_SHELTER_ID = 1;
    private final static Boolean IS_FREE_STATUS = true;

    /**
     * Метод по обработке сообщения от пользователя, соответствующего состоянию бота - LIST_ANIMALS_COM. В результате
     * нажатия пользователем на кнопку list_animals_com бот отправляет пользователю сообщение с фотографиями
     * животных и прикрепленным к нему меню из кнопок для выбора понравившегося животного.
     * @param message сообщение от пользователя
     * @return ответное сообщение пользователю от бота
     */

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.chat().id();
        SendMessage replyMessage = new SendMessage(chatId, tBotConfig.getListAnimalsMsgCatShelter());
        telegramBot.execute(replyMessage);
        return replyMessage;
    }

    @Override
    public BotStateCatShelter getHandlerName() {
        return BotStateCatShelter.LIST_ANIMALS_COM;
    }

    public void sendPhotoByDataBase(Long chatId) {
        List<Pet> freeCats = petService.findAllByShelterIdAndIsFreeStatus(CAT_SHELTER_ID, IS_FREE_STATUS);
        // Получили из базы данных список свободных питомцев из приютов для котов / кошек

        for (Pet cat : freeCats) {
            AvatarPet avatar = avatarService.findAvatarByPetId(cat.getId());
            if (avatar != null) {
                try {
                    byte[] photo = Files.readAllBytes(Path.of(avatar.getFilePath()));
                    String caption = cat.getPetType() + " " + cat.getName() + "\n " +
                            "возраст " + cat.getAge() + " лет" + "\n " +
                            "пол " + cat.getSex();
                    SendPhoto sendPhoto = new SendPhoto(chatId, photo)
                            // создаем сообщение с файлом - фото
                            .caption(caption);
                    // по цепочке добавляем надпись под изображением
                    telegramBot.execute(sendPhoto);

                } catch (IOException e) {
                    log.error("Error reading avatar file for pet with id: {} ", cat.getId(), e);
                }
            }
        }



    }
}
