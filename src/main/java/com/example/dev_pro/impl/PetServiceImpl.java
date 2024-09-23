package com.example.dev_pro.impl;

import com.example.dev_pro.dto.PetDto;
import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Shelter;
import com.example.dev_pro.repository.PetRepository;
import com.example.dev_pro.service.PetService;
import com.example.dev_pro.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ShelterService shelterService;

    @Override
    public Pet createPet(PetDto petDto) {
        Shelter shelter = shelterService.findShelterById(Math.toIntExact(petDto.getShelterId()));
        if (shelter == null) {
            throw new EntityNotFoundException("Shelter not found");
        }

        Pet pet = new Pet();
        pet.setPetType(petDto.getPetType());
        pet.setName(petDto.getName());
        pet.setSex(petDto.getSex());
        pet.setAge(petDto.getAge());
        pet.setIsFreeStatus(petDto.getIsFreeStatus());
        pet.setShelter(shelter);

        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long id, PetDto petDto) {
        Pet existingPet = findPetById(id);
        if (existingPet == null) {
            throw new EntityNotFoundException("Pet not found");
        }

        Shelter shelter = shelterService.findShelterById(Math.toIntExact(petDto.getShelterId()));
        if (shelter == null) {
            throw new EntityNotFoundException("Shelter not found");
        }

        existingPet.setPetType(petDto.getPetType());
        existingPet.setName(petDto.getName());
        existingPet.setSex(petDto.getSex());
        existingPet.setAge(petDto.getAge());
        existingPet.setIsFreeStatus(petDto.getIsFreeStatus());
        existingPet.setShelter(shelter);

        return petRepository.save(existingPet);
    }

    @Override
    public Pet findPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
    }

    @Override
    public Pet deletePetById(Long id) {
        Pet pet = findPetById(id);
        petRepository.deleteById(id);
        return pet;
    }

    @Override
    public Collection<Pet> findAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> findAllByShelterId(Integer shelterId) {
        return petRepository.findAllByShelterId(shelterId);
    }

    @Override
    public List<Pet> findAllByShelterIdAndIsFreeStatus(Integer shelterId, boolean isFreeStatus) {
        return petRepository.findAllByShelterIdAndIsFreeStatus(shelterId, isFreeStatus);
    }

    @Override
    public List<Pet> findAllByAdopterId(Long adopterId) {
        return petRepository.findAllByAdopterId(adopterId);
    }
}