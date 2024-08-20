package com.example.dev_pro.service;

import com.example.dev_pro.model.Adopter;

import java.time.LocalDate;
import java.util.List;

public interface AdopterService {

    List<Adopter> findAllWhoNotSendReportBeforeDay(LocalDate localDate);

    List<Adopter> findAllWhoPasserProbationPeriod();

    Adopter create(Adopter adopter);

    Adopter update(Adopter adopter);

    Adopter getById(Long id);

    void deleteById(Long id);


}
