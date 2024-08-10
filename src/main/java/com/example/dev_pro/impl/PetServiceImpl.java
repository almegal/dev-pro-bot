package com.example.dev_pro.impl;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.repository.PetRepository;
import com.example.dev_pro.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Класс, создающий логику по работе с питомцами в базе данных
 */

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository repository;


    @Override
    public Pet createPet(Pet pet) {
        return repository.save(pet);
    }


    @Override
    public Pet updatePet(Pet pet) {
        return repository.save(pet);
    }


    @Override
    public Pet findPetById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public Pet deletePetById(Long id) {
        Pet pet = findPetById(id);
        repository.deleteById(id);
        return pet;
    }


    @Override
    public Collection<Pet> findAllPets() {
        return repository.findAll();
    }

    @Override
    public List<Pet> findAllByShelterId(Integer shelterId) {
        return repository.findAllByShelterId(shelterId);
    }

    @Override
    public List<Pet> findAllByShelterIdAndIsFreeStatus(Integer shelterId, boolean isFreeStatus) {
        return repository.findAllByShelterIdAndIsFreeStatus(shelterId, isFreeStatus);
    }

}
