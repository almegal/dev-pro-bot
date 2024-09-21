package com.example.dev_pro.service;

import com.example.dev_pro.dto.PetDto;
import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Сервис по созданию логики по работе с питомцами из базы данных
 */
public interface PetService {

    /**
     * Метод по созданию питомца в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param petDto объект класса PetDto, не может быть null
     * @return объект класса Pet, сохраненный в базу данных
     */
    Pet createPet(PetDto petDto);

    /**
     * Метод по обновлению питомца в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param id идентификатор объекта класса Pet, не может быть null
     * @param petDto объект класса PetDto, не может быть null
     * @return объект класса Pet, обновленный в базе данных
     */
    Pet updatePet(Long id, PetDto petDto);

    /**
     * Метод по поиску питомца в базе данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * @param id идентификатор объекта класса Pet, не может быть null
     * @throws EntityNotFoundException если объект класса Pet с указанным id не был найден в БД
     * @return объект класса Pet
     */
    Pet findPetById(Long id);

    /**
     * Метод по удалению питомца из базы данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     * @param id идентификатор объекта класса Pet, не может быть null
     * @throws EntityNotFoundException если объект класса Pet с указанным id не был найден в БД
     * @return удаленный из базы данных объект класса Pet
     */
    Pet deletePetById(Long id);

    /**
     * Метод по получению из базы данных всех питомцев.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return коллекцию объектов класса Pet
     */
    Collection<Pet> findAllPets();

    /**
     * Метод по получению всех питомцев приюта по его идентификатору
     * @param shelterId идентификатор приюта
     * @return список питомцев приюта
     */
    List<Pet> findAllByShelterId(Integer shelterId);

    /**
     * Метод по получению всех свободных питомцев приюта по идентификатору приюта
     * @param shelterId идентификатор приюта
     * @param isFreeStatus статус питомца (свободный или закрепленный за усыновителем)
     * @return список питомцев приюта
     */
    List<Pet> findAllByShelterIdAndIsFreeStatus(Integer shelterId, boolean isFreeStatus);

    /**
     * Метод по получению всех питомцев по идентификатору усыновителя
     * Используется метод репозитория {@link JpaRepository#findAllByAdopterId(Long)}
     * @param adopterId идентификатор усыновителя
     * @return список питомцев усыновителя
     */
    List<Pet> findAllByAdopterId(Long adopterId);
}