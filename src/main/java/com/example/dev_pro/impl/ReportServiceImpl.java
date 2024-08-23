package com.example.dev_pro.impl;


import com.example.dev_pro.cache.ReportDataCache;
import com.example.dev_pro.exception.EntityNotFoundException;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Pet;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private final ReportDataCache reportDataCache;


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
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Report findReportByPetIdAndDateReport(Long petId, LocalDate dateReport) {
        return repository.findReportByPetIdAndDateReport(petId, dateReport).orElseThrow(EntityNotFoundException::new);
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
    public void deleteAllByAdopterId(Adopter adopterId) {
        repository.deleteAllByAdopterId(adopterId);
    }


    // Загружаем файл с фото и сохраняем его в папку с именем photosDir
    @Override
    public Report uploadReportPhoto(Report report, PhotoSize[] photoSizes) throws IOException {

        log.info("Вызов метода uploadReportPhoto(Long adopterId, PhotoSize[] photoSizes) для загрузки фотографий");

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
                log.error("Error uploading photo file for report with id: {}", report.getId(), e);
            }

            log.info("File has been uploaded!");

            report.setFilePath(newFilePath.toString());

        } else {
            throw new IllegalArgumentException();
        }
        return report;
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private byte[] GenerateImagePreview(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public Report savePhoto(Report report, PhotoSize[] photos) {

        log.info("Вызов метода savePhoto(Long adopterId, PhotoSize[] photoSizes) для загрузки фотографий");

        if (photos != null && photos.length > 0) {
            PhotoSize photo = photos[photos.length - 1];
            String fileId = photo.fileId();
            String filePath = telegramBot.execute(new GetFile(fileId)).file().filePath();

            Path photoPath = savePhotoToLocal(filePath);
            // новый путь у загружаемого файла
            report.setFilePath(photoPath.toString());
            // сохраняем в переменную report путь к файлу
        } else {
            log.error("Фото не найдено в сообщении");
            return null;
        }
        return report;
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

    public Report createReport(String photoFilePath, String textReport, Adopter adopter, Pet pet) {
        try {
            Path path = Path.of(photoFilePath);
            String mediaType = Files.probeContentType(path);
            long fileSize = Files.size(path);

            Report report = new Report();
            report.setDateReport(LocalDate.now());
            report.setTextReport(textReport);
            report.setFilePath(photoFilePath);
            report.setAdopter(adopter);
            report.setPet(pet);

            // сохраняем отчет в базу данных

            return report;
        } catch (IOException e) {
            log.error("Ошибка при создании отчета: {}", e.getMessage());
            return null;
        }
    }

}
