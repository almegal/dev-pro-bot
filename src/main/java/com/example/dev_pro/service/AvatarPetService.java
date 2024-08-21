package com.example.dev_pro.service;


import com.example.dev_pro.model.AvatarPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;


/**
 * Сервис по созданию логики по работе с аватарками питомцев из базы данных
 */

public interface AvatarPetService {

    /**
     * Метод по получению из базы данных аватарок всех питомцев.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return коллекцию объектов класса AvatarPet
     */

    Collection<AvatarPet> findAllAvatars();

    /**
     * Метод по поиску аватара питомца по его идентификатору
     * @param petId идентификатор питомца
     * @return аватар питомца
     */

    AvatarPet findAvatarByPetId(Long petId);

    /**
     * Метод по загрузке файла с аватаркой питомца и сохранению его в базу данных
     * @param petId идентификатор питомца
     * @param avatarFile файл с аватаркой питомца
     * @throws IOException
     */

    void uploadAvatar(Long petId, MultipartFile avatarFile) throws IOException;


}
