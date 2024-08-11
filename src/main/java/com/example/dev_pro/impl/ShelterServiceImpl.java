package com.example.dev_pro.impl;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Shelter;
import com.example.dev_pro.repository.ShelterRepository;
import com.example.dev_pro.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс, создающий логику по работе с приютами в базе данных
 */

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository repository;

    @Override
    public Shelter createShelter(Shelter shelter) {
        return repository.save(shelter);
    }


    @Override
    public Shelter updateShelter(Shelter shelter) {
        return repository.save(shelter);
    }


    @Override
    public Shelter findShelterById(Integer id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public Shelter deleteShelterById(Integer id) {
        Shelter shelter = findShelterById(id);
        repository.deleteById(id);
        return shelter;
    }


    @Override
    public Collection<Shelter> findAllShelters() {
        return repository.findAll();
    }

    @Override
    public Collection<Pet> findPetsByShelterId(Integer shelterId) {
        return findShelterById(shelterId).getPets();
    }

}
