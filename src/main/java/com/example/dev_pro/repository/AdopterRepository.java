package com.example.dev_pro.repository;

import com.example.dev_pro.model.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    @Query("SELECT a FROM Adopter a WHERE NOT EXISTS (" +
            "SELECT r FROM Report r WHERE r.adopter = a AND r.dateReport = :currentDate)")
    List<Adopter> findAdoptersWithoutReportForCurrentDate(@Param("currentDate") LocalDate currentDate);

    List<Adopter> findByProbationPeriodTrue();
}
