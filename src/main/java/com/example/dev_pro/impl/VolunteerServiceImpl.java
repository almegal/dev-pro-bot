package com.example.dev_pro.impl;


import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.repository.VolunteerRepository;
import com.example.dev_pro.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, создающий логику по работе с волонтерами в базе данных
 */

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl  implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    /**
     * Метод по созданию волонтера в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Volunteer, не может быть null
     * @return объект класса Volunteer, сохраненный в базу данных
     */

    @Override
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Метод по обновлению волонтера в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Volunteer, не может быть null
     * @return объект класса Volunteer, обновленный в базе данных
     */

    @Override
    public Volunteer updateVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Метод по поиску волонтера в базе данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * @param id идентификатор объекта класса Volunteer, не может быть null
     * @throws EntityNotFoundException если объект класса Volunteer с указанным id не был найден в БД
     * @return объект класса Volunteer
     */

    @Override
    public Volunteer findVolunteerById(Long id) {
        return volunteerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Метод по удалению волонтера из базы данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     * @param id идентификатор объекта класса Volunteer, не может быть null
     * @throws EntityNotFoundException если объект класса Volunteer с указанным id не был найден в БД
     * @return удаленный из базы данных объект класса Volunteer
     */

    @Override
    public Volunteer deleteVolunteerById(Long id) {
        Volunteer volunteer = findVolunteerById(id);
        volunteerRepository.deleteById(id);
        return volunteer;
    }

    /**
     * Метод по получению из базы данных всех волонтеров.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return коллекцию объектов класса Volunteer
     */

    @Override
    public Collection<Volunteer> findAllVolunteers() {
        return volunteerRepository.findAll();
    }

    /**
     * Метод по получению из базы данных списка никнеймов волонтеров.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return список никнеймов объектов класса Volunteer
     */

    @Override
    public List<String> getListNickNamesOfVolunteers() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        List<String> nickNames = volunteers.stream()
                .map(Volunteer::getNickName)
                .collect(Collectors.toList());
        return  nickNames;
    }

    /**
     * Метод возвращает true, если пользователь укажет команду - никнейм волонтера, или false, если пользователь
     * не укажет эту команду.
     * @param text команда пользователя / никнейм волонтера, не может быть null
     * @return true или false
     */

    @Override
    public boolean isSelectVolunteer(String text) {
        return getListNickNamesOfVolunteers().stream()
                .anyMatch(nickName -> nickName.equals(text));
    }

    /**
     * Метод по получению идентификатора чата волонтера.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @param text команда пользователя / никнейм волонтера, не может быть null
     * @return идентификатор чата волонтера
     */

    @Override
    public Long getChatIdOfVolunteer(String text) {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        Long chatIdVolunteer = volunteers.stream()
                .filter(volunteer -> volunteer.getNickName().equals(text))
                .mapToLong(Volunteer::getChatId)
                .findFirst()
                .orElse(0L);
        return chatIdVolunteer;

    }

}
