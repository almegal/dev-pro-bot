package com.example.dev_pro.impl;

import com.example.dev_pro.cache.ReportDataCache;
import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.repository.ReportRepository;
import com.example.dev_pro.service.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

/**
 * Класс, создающий логику по работе с отчетами в базе данных
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Value("${path.to.photos.folder}")
    private String photosDir;
    // Название папки для хранения файлов с фотографиями из отчетов
    // В аннотации @Value указываем ключ, который находится в application.properties

    private final ReportRepository repository;
    private final TelegramBot telegramBot;

    @Override
    public Report createReport(Report report) {
        return repository.save(report);
    }

    @Override
    public Report updateReport(Report report) {
        return repository.save(report);
    }

    @Override
    public Report findReportById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Report with id " + id + " not found"));
    }

    @Override
    public Report findReportByPetIdAndDateReport(Long petId, LocalDate dateReport) {
        return repository.findReportByPetIdAndDateReport(petId, dateReport)
                .orElseThrow(() -> new EntityNotFoundException("Report for pet with id " + petId + " and date " + dateReport + " not found"));
    }

    @Override
    public Report deleteReportById(Integer id) {
        Report report = findReportById(id);
        repository.deleteById(id);
        return report;
    }

    @Transactional
    @Override
    public List<Report> findAllByIsViewedNull() {
        return repository.findAllByIsViewedNull();
    }

    @Override
    public List<Report> findAllByAdopterId(Long adopterId) {
        return repository.findAllByAdopterId(adopterId);
    }

    @Override
    public List<Report> findAllByAdopterIdAndPetId(Long adopterId, Long petId) {
        return repository.findAllByAdopterIdAndPetId(adopterId, petId);
    }

    @Transactional
    @Override
    public void deleteAllByAdopterId(Long adopterId) {
        repository.deleteAllByAdopterId(adopterId);
    }

    /**
     * Метод по загрузке фотографии на диск и возвращению пути к этому файлу
     * Первый вариант
     */

    @Override
    public Path uploadReportPhoto(PhotoSize[] photoSizes) {

        log.info("Вызов метода uploadReportPhoto(PhotoSize[] photoSizes) для загрузки фотографий");

        if (photoSizes != null && photoSizes.length > 0) {
            int maxIndex = photoSizes.length - 1;
            // Фото в последней ячейке будет оригинальным по размеру
            String fileId = photoSizes[maxIndex].fileId();
            // Получаем из объекта PhotoSize идентификатор файла
            GetFile getFile = new GetFile(fileId);

            Path newFilePath = null;
            // Создаем переменную для нового имени загружаемого файла
            byte[] fileData = null;
            // Создаем переменную для массива байт, который будет получен из загружаемого изображения

            try {
                GetFileResponse getFileResponse = telegramBot.execute(getFile);
                File file = getFileResponse.file();
                // Из объекта GetFileResponse телеграм-бот возвращает объект File
                String filePath = file.filePath();
                // Из объекта File получаем имя файла (путь)
                fileData = telegramBot.getFileContent(file);
                // В переменную fileData сохраняем ссылку на массив байт, полученный из загружаемого файла

                newFilePath = Path.of(photosDir, filePath);
                // Создаем новое имя (путь) для загружаемого файла

                Files.createDirectories(newFilePath.getParent());
                // Данный метод проверяет, есть ли по созданному пути newFilePath папка
                // Если по этому пути папки нет, то этот метод ее создаст

                log.info("Converting bytes ....");
                Files.write(newFilePath, fileData);

            } catch (IOException e) {
                log.error("Error uploading photo file for report with fileId: {} ", photoSizes[maxIndex].fileId(), e);
                return null;
            }

            return newFilePath;

        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Метод по загрузке фотографии на диск и возвращению пути к этому файлу
     * Второй вариант
     */

    @Override
    public Path savePhoto(PhotoSize[] photos) {

        log.info("Вызов метода savePhoto(PhotoSize[] photoSizes) для загрузки фотографий");

        if (photos != null && photos.length > 0) {
            PhotoSize photo = photos[photos.length - 1];
            String fileId = photo.fileId();
            String filePath = telegramBot.execute(new GetFile(fileId)).file().filePath();

            Path photoPath = savePhotoToLocal(filePath);
            // новый путь у загружаемого файла
            return photoPath;

        } else {
            log.error("Фото не найдено в сообщении");
            throw new IllegalArgumentException();
        }
    }

    private Path savePhotoToLocal(String filePath) {
        try {
            byte[] fileBytes = downloadFile(filePath);
            if (fileBytes == null) {
                throw new IOException("Не удалось скачать файл");
            }
            Path photoPath = Path.of(photosDir, filePath);
            // Создаем новое имя (путь) для загружаемого файла
            Files.createDirectories(photoPath.getParent());
            try (FileOutputStream fos = new FileOutputStream(photoPath.toFile())) {
                fos.write(fileBytes);
            }
            return photoPath;
        } catch (IOException e) {
            log.error("Ошибка при сохранении фото: {}", e.getMessage());
            return null;
        }
    }

    private byte[] downloadFile(String filePath) {
        String url = "https://api.telegram.org/file/bot" + telegramBot.getToken() + "/" + filePath;
        try {
            return new URL(url).openStream().readAllBytes();
        } catch (IOException e) {
            log.error("Ошибка при скачивании файла: {}", e.getMessage());
            return null;
        }
    }

}