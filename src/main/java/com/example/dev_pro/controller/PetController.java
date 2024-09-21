package com.example.dev_pro.controller;

import com.example.dev_pro.dto.PetDto;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.service.PetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/pet")
@Tag(name = "API для работы с питомцами")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody PetDto petDto) {
        Pet createdPet = petService.createPet(petDto);
        return ResponseEntity.ok(createdPet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody PetDto petDto) {
        Pet updatedPet = petService.updatePet(id, petDto);
        return ResponseEntity.ok(updatedPet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable("id") Long id) {
        Pet pet = petService.findPetById(id);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deletePetById(@PathVariable("id") Long id) {
        Pet pet = petService.deletePetById(id);
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Pet>> getAllPets() {
        Collection<Pet> pets = petService.findAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-pets-by-shelter/{shelterId}")
    public ResponseEntity<List<Pet>> getAllByShelterId(@PathVariable("shelterId") Integer shelterId) {
        List<Pet> pets = petService.findAllByShelterId(shelterId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-free-pets-by-shelter")
    public ResponseEntity<List<Pet>> getAllByShelterIdAndIsFreeStatus(
            @RequestParam Integer shelterId,
            @RequestParam boolean isFreeStatus
    ) {
        List<Pet> pets = petService.findAllByShelterIdAndIsFreeStatus(shelterId, isFreeStatus);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-pets-by-adopter/{adopterId}")
    public ResponseEntity<List<Pet>> getAllByAdopterId(@PathVariable("adopterId") Long adopterId) {
        List<Pet> pets = petService.findAllByAdopterId(adopterId);
        return ResponseEntity.ok(pets);
    }
}