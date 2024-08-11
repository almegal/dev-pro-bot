package com.example.dev_pro.controller;


import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Shelter;
import com.example.dev_pro.service.ShelterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/shelter")
@Tag(name = "API для работы с приютами")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterService service;

    @PostMapping
    public ResponseEntity<Shelter> createShelter(@RequestBody Shelter shelter) {
        Shelter sh = service.createShelter(shelter);
        return ResponseEntity.ok(sh);
    }

    @PutMapping
    public ResponseEntity<Shelter> updateShelter(@RequestBody Shelter shelter) {
        Shelter sh = service.updateShelter(shelter);
        return ResponseEntity.ok(sh);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shelter> getShelterById(@PathVariable("id") Integer id) {
        Shelter sh = service.findShelterById(id);
        return ResponseEntity.ok(sh);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Shelter> deleteShelterById(@PathVariable("id") Integer id) {
        Shelter sh = service.deleteShelterById(id);
        return ResponseEntity.ok(sh);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Shelter>> getAllShelters() {
        Collection<Shelter> shelters = service.findAllShelters();
        return ResponseEntity.ok(shelters);
    }

    @GetMapping("/all-pets-by-shelter/{shelterId}")
    public ResponseEntity<Collection<Pet>> getPetsByShelterId(@PathVariable("shelterId") Integer shelterId) {
        Collection<Pet> pets = service.findPetsByShelterId(shelterId);
        return ResponseEntity.ok(pets);
    }

}
