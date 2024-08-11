package com.example.dev_pro.repository;

import com.example.dev_pro.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    /**
     * Метод по получению всех питомцев приюта по идентификатору приюта
     * @param shelterId идентификатор приюта
     * @return список питомцев приюта
     */
    List<Pet> findAllByShelterId(Integer shelterId);


    /**
     * Метод по получению всех свободных питомцев приюта по идентификатору приюта
     * @param shelterId идентификатор приюта
     * @return список питомцев приюта
     */
    List<Pet> findAllByShelterIdAndIsFreeStatus(Integer shelterId, boolean isFreeStatus);


}
