package com.example.dev_pro.service;

import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Report;
import com.pengrad.telegrambot.model.PhotoSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Сервис по созданию логики по работе с отчетами из базы данных
 */

public interface ReportService {

    /**
     * Метод по созданию отчета в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Report, не может быть null
     * @return объект класса Report, сохраненный в базу данных
     */

    Report createReport(Report report);

    /**
     * Метод по обновлению отчета в базе данных.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param - объект класса Report, не может быть null
     * @return объект класса Report, обновленный в базе данных
     */

    Report updateReport(Report report);

    /**
     * Метод по поиску отчета в базе данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * @param id идентификатор объекта класса Report, не может быть null
     * @throws EntityNotFoundException если объект класса Report с указанным id не был найден в БД
     * @return объект класса Report
     */

    Report findReportById(Integer id);

    /**
     * Метод по получению из базы данных отчета конкретного усыновителя по идентификатору питомца и на конкретную дату.
     * @return отчет усыновителя
     */

    Report findReportByPetIdAndDateReport(Long petId, LocalDate dateReport);

    /**
     * Метод по удалению отчета из базы данных по идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     * @param id идентификатор объекта класса Report, не может быть null
     * @throws EntityNotFoundException если объект класса Report с указанным id не был найден в БД
     * @return удаленный из базы данных объект класса Report
     */

    Report deleteReportById(Integer id);

    /**
     * Метод по получению из базы данных всех непросмотренных отчетов.
     * @return список всех непросмотренных отчетов
     */

    List<Report> findAllByIsViewedNull();

    /**
     * Метод по получению всех отчетов конкретного усыновителя.
     * @param adopterId идентификатор усыновителя
     * @return список отчетов конкретного усыновителя
     */
    List<Report> findAllByAdopterId(Long adopterId);

    /**
     * Метод по получению отчетов конкретного усыновителя по конкретному питомцу.
     * @param adopterId идентификатор усыновителя
     * @param petId идентификатор питомца
     * @return список отчетов.
     *
     */
    List<Report> findAllByAdopterIdAndPetId(Long adopterId, Long petId);

    /**
     * Метод по удалению всех отчетов конкретного усыновителя.
     * @param adopterId усыновитель, отчеты которого необходимо удалить
     */

    void deleteAllByAdopterId(Adopter adopterId);

    /**
     * Метод по загрузке файла с фотографией
     * @param report идентификатор усыновителя
     * @param photoSizes массив байт, извлеченный из загружаемого фото
     */

    Report uploadReportPhoto(Report report, PhotoSize[] photoSizes) throws IOException;

    Report savePhoto(Report report, PhotoSize[] photos);


}
