package com.example.dev_pro.service;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Сервис по созданию логики по работе с волонтерами из базы данных
 */


public interface VolunteerService {

    /**
     * Метод по созданию волонтера в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Volunteer, не может быть null
     * @return объект класса Volunteer, сохраненный в базу данных
     */

    Volunteer createVolunteer(Volunteer volunteer);

    /**
     * Метод по обновлению волонтера в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Volunteer, не может быть null
     * @return объект класса Volunteer, обновленный в базе данных
     */

    Volunteer updateVolunteer(Volunteer volunteer);

    /**
     * Метод по поиску волонтера в базе данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * @param id идентификатор объекта класса Volunteer, не может быть null
     * @throws EntityNotFoundException если объект класса Volunteer с указанным id не был найден в БД
     * @return объект класса Volunteer
     */

    Volunteer findVolunteerById(Long id);

    /**
     * Метод по удалению волонтера из базы данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     * @param id идентификатор объекта класса Volunteer, не может быть null
     * @throws EntityNotFoundException если объект класса Volunteer с указанным id не был найден в БД
     * @return удаленный из базы данных объект класса Volunteer
     */

    Volunteer deleteVolunteerById(Long id);

    /**
     * Метод по получению из базы данных всех волонтеров.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return коллекцию объектов класса Volunteer
     */

    Collection<Volunteer> findAllVolunteers();

    /**
     * Метод по получению из базы данных списка никнеймов волонтеров.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return список никнеймов объектов класса Volunteer
     */

    List<String> getListNickNamesOfVolunteers();

}
