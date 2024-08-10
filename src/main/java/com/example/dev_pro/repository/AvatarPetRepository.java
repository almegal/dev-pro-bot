package com.example.dev_pro.repository;

import com.example.dev_pro.model.AvatarPet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarPetRepository extends JpaRepository<AvatarPet, Long> {

    /**
     * Метод по поиску аватара питомца по идентификатору питомца
     * @param petId идентификатор питомца
     * @return объект Optional, содержащий аватар питомца
     */
    Optional<AvatarPet> findAvatarByPetId(Long petId);
}
