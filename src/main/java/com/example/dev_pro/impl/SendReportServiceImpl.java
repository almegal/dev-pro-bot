package com.example.dev_pro.impl;

import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.Pet;
import com.example.dev_pro.model.Report;
import com.example.dev_pro.repository.ReportRepository;
import com.example.dev_pro.service.SendReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SendReportServiceImpl implements SendReportService {

    @Value("${path.to.photo.folder}")
    private String photoDir;
    private final ReportRepository reportRepository;
    private final TelegramBot telegramBot;
    private static final Logger logger = LoggerFactory.getLogger(SendReportServiceImpl.class);

    @Override
    public String savePhoto(PhotoSize[] photos) {
        if (photos != null && photos.length > 0) {
            PhotoSize photo = photos[photos.length - 1];
            String fileId = photo.fileId();
            String filePath = telegramBot.execute(new GetFile(fileId)).file().filePath();
            return savePhotoToLocal(filePath);
        } else {
            logger.error("Фото не найдено в сообщении");
            return null;
        }
    }

    private String savePhotoToLocal(String filePath) {
        try {
            byte[] fileBytes = downloadFile(filePath);
            if (fileBytes == null) {
                throw new IOException("Не удалось скачать файл");
            }
            Path photoPath = Paths.get(photoDir, filePath);
            Files.createDirectories(photoPath.getParent());
            try (FileOutputStream fos = new FileOutputStream(photoPath.toFile())) {
                fos.write(fileBytes);
            }
            return photoPath.toString();
        } catch (IOException e) {
            logger.error("Ошибка при сохранении фото: ", e.getMessage());
            return null;
        }
    }

    private byte[] downloadFile(String filePath) {
        String url = "https://api.telegram.org/file/bot" + telegramBot.getToken() + "/" + filePath;
        try {
            return new URL(url).openStream().readAllBytes();
        } catch (IOException e) {
            logger.error("Ошибка при скачивании файла: ", e.getMessage());
            return null;
        }
    }

    @Override
    public Report createReport(String photoFilePath, String textReport, Adopter adopter, Pet pet) {
        try {
            Path path = Paths.get(photoFilePath);
            String mediaType = Files.probeContentType(path);
            long fileSize = Files.size(path);

            Report report = new Report();
            report.setDateReport(LocalDate.now());
            report.setTextReport(textReport);
            report.setFilePath(photoFilePath);
            report.setFileSize(fileSize);
            report.setMediaType(mediaType);
            report.setIsViewed(false);
            report.setAdopter(adopter);
            report.setPet(pet);
            reportRepository.save(report);

            return report;
        } catch (IOException e) {
            logger.error("Ошибка при создании отчета: ", e.getMessage());
            return null;
        }
    }
}