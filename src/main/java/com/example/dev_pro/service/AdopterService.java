package com.example.dev_pro.service;

import com.example.dev_pro.dto.AdopterDTO;
import com.example.dev_pro.model.Adopter;

import java.time.LocalDate;
import java.util.List;

public interface AdopterService {

    List<Adopter> findAllWhoNotSendReportBeforeDay(LocalDate localDate);

    List<Adopter> findAllWhoPasserProbationPeriod();

    /**
     * Метод по созданию усыновителя
     * @param adopter усыновитель
     * @return сохраненного в базу данных усыновителя
     */

    Adopter create(AdopterDTO adopter);

    Adopter update(Adopter adopter);

    Adopter getById(Long id);

    void deleteById(Long id);

    /**
     * Метод по поиску усыновителя по его идентификатору
     * @param id идентификатор усыновителя
     * @return объект Optional, содержащий усыновителя
     */

    Adopter findAdopterById(Long id);


    /**
     * Метод по поиску усыновителя по идентификатору пользователя телеграм
     * @param telegramUserId идентификатор пользователя телеграм
     * @return объект Optional, содержащий усыновителя
     */

    Adopter findAdopterByTelegramUserId(Long telegramUserId);

}
