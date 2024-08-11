package com.example.dev_pro.service;


import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Сервис по созданию логики по работе с приютами из базы данных
 */

public interface ShelterService {

    /**
     * Метод по созданию приюта в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Shelter, не может быть null
     * @return объект класса Shelter, сохраненный в базу данных
     */

    Shelter createShelter(Shelter shelter);

    /**
     * Метод по обновлению приюта в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Shelter, не может быть null
     * @return объект класса Shelter, обновленный в базе данных
     */

    Shelter updateShelter(Shelter shelter);

    /**
     * Метод по поиску приюта в базе данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * @param id идентификатор объекта класса Shelter, не может быть null
     * @throws EntityNotFoundException если объект класса Shelter с указанным id не был найден в БД
     * @return объект класса Shelter
     */

    Shelter findShelterById(Integer id);

    /**
     * Метод по удалению приюта из базы данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     * @param id идентификатор объекта класса Shelter, не может быть null
     * @throws EntityNotFoundException если объект класса Shelter с указанным id не был найден в БД
     * @return удаленный из базы данных объект класса Shelter
     */

    Shelter deleteShelterById(Integer id);

    /**
     * Метод по получению из базы данных всех приютов.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return коллекцию объектов класса Shelter
     */

    Collection<Shelter> findAllShelters();

    /**
     * Метод по получению питомцев приюта по идентификатору приюта
     * @param shelterId идентификатор приюта
     * @return список питомцев приюта
     */

    Collection<Pet> findPetsByShelterId(Integer shelterId);
}
