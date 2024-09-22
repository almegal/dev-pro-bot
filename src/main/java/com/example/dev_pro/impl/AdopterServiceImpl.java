package com.example.dev_pro.impl;

import com.example.dev_pro.dto.AdopterDTO;
import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.repository.AdopterRepository;
import com.example.dev_pro.repository.PetRepository;
import com.example.dev_pro.repository.TelegramUserRepository;
import com.example.dev_pro.service.AdopterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdopterServiceImpl implements AdopterService {
    private final AdopterRepository adopterRepository;
    private final TelegramUserRepository telegramUserRepository;
    private final PetRepository petRepository;

    @Override
    public List<Adopter> findAllWhoNotSendReportBeforeDay(LocalDate localDate) {
        return adopterRepository.findAdoptersWithoutReportForCurrentDate(localDate);
    }

    @Override
    public List<Adopter> findAllWhoPasserProbationPeriod() {
        return adopterRepository.findByProbationPeriodTrue();
    }

    @Override
    public Adopter create(AdopterDTO adopter) {
        TelegramUser telegramUser = telegramUserRepository.findById(adopter.getTelegramUserId())
                .orElseThrow(EntityNotFoundException::new);
        Adopter ad = new Adopter();
        ad.setTelegramUser(telegramUser);
        List<Pet> pets = new ArrayList<>();
        for (Long petId : adopter.getPetIds()) {
            Pet pet = petRepository.findById(petId).orElseThrow(EntityNotFoundException::new);
            pets.add(pet);
        }
        ad.setPets(pets);

        Adopter adopterDb = adopterRepository.save(ad);
        telegramUser.setAdopter(adopterDb);
        telegramUserRepository.save(telegramUser);
        for (Pet pet : pets) {
            pet.setAdopter(adopterDb);
        }
        petRepository.saveAll(pets);
        return adopterDb;
    }

    @Override
    public Adopter update(Adopter adopter) {
        boolean isExist = adopterRepository.existsById(adopter.getId());
        if (!isExist) {
            adopterRepository.save(adopter);
        }
        return adopter;
    }

    @Override
    public Adopter getById(Long id) {
        return adopterRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        adopterRepository.deleteById(id);
    }

    @Override
    public Adopter findAdopterById(Long id) {
        return adopterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Adopter findAdopterByTelegramUserId(Long telegramUserId) {
        return adopterRepository.findAdopterByTelegramUserId(telegramUserId).orElseThrow(EntityNotFoundException::new);
    }
}
