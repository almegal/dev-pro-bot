package com.example.dev_pro.repository;

import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    /**
     * Метод по получению из базы данных отчета конкретного усыновителя по идентификатору питомца и на конкретную дату.
     * @return отчет усыновителя
     */

    Optional<Report> findReportByPetIdAndDateReport(Long petId, LocalDate dateReport);

    /**
     * Метод по получению из базы данных всех непросмотренных отчетов.
     * @return список всех непросмотренных отчетов
     */

    @Query(value = "select * from reports where is_viewed is null", nativeQuery = true)
    List<Report> findAllByIsViewedNull();

    /**
     * Метод по получению всех отчетов конкретного усыновителя
     * @param adopterId идентификатор усыновителя
     * @return список отчетов конкретного усыновителя
     */
    List<Report> findAllByAdopterId(Long adopterId);

    /**
     * Метод по получению отчетов конкретного усыновителя по конкретному питомцу
     * @param adopterId идентификатор усыновителя
     * @param petId идентификатор питомца
     * @return список отчетов
     */
    List<Report> findAllByAdopterIdAndPetId(Long adopterId, Long petId);

    /**
     * Метод по удалению всех отчетов конкретного усыновителя
     * @param adopterId идентификатор усыновителя, отчеты которого необходимо удалить
     */

    void deleteAllByAdopterId(Long adopterId);

}
