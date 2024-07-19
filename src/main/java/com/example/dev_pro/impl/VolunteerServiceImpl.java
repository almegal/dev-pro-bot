package com.example.dev_pro.impl;


import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.repository.VolunteerRepository;
import com.example.dev_pro.service.VolunteerService;
import lombok.RequiredArgsConstructor;
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


    @Override
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }


    @Override
    public Volunteer updateVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }


    @Override
    public Volunteer findVolunteerById(Long id) {
        return volunteerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public Volunteer deleteVolunteerById(Long id) {
        Volunteer volunteer = findVolunteerById(id);
        volunteerRepository.deleteById(id);
        return volunteer;
    }


    @Override
    public Collection<Volunteer> findAllVolunteers() {
        return volunteerRepository.findAll();
    }


    @Override
    public List<String> getListNickNamesOfVolunteers() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        List<String> nickNames = volunteers.stream()
                .map(Volunteer::getNickName)
                .collect(Collectors.toList());
        return  nickNames;
    }

}
