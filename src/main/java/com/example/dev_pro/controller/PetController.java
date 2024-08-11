package com.example.dev_pro.controller;


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

    private final PetService service;

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        Pet p = service.createPet(pet);
        return ResponseEntity.ok(p);
    }

    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        Pet p = service.updatePet(pet);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable("id") Long id) {
        Pet p = service.findPetById(id);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deletePetById(@PathVariable("id") Long id) {
        Pet p = service.deletePetById(id);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Pet>> getAllPets() {
        Collection<Pet> pets = service.findAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-pets-by-shelter/{shelterId}")
    public ResponseEntity<List<Pet>> getAllByShelterId(@PathVariable("shelterId") Integer shelterId) {
        List<Pet> pets = service.findAllByShelterId(shelterId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all-free-pets-by-shelter")
    public ResponseEntity<List<Pet>> getAllByShelterIdAndIsFreeStatus(
            @RequestParam Integer shelterId,
            @RequestParam boolean isFreeStatus
    ) {
        List<Pet> pets = service.findAllByShelterIdAndIsFreeStatus(shelterId, isFreeStatus);
        return ResponseEntity.ok(pets);
    }

}
