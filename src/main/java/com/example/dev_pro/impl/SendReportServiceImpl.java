package com.example.dev_pro.impl;

import com.example.dev_pro.model.Report;
import com.example.dev_pro.repository.ReportRepository;
import com.example.dev_pro.service.SendReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
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
    private Report report;
    private ReportRepository reportRepository;
    private TelegramBot telegramBot;
    private static final Logger logger = LoggerFactory.getLogger(SendReportServiceImpl.class);

    @Override
    public Report handleReport(Message message) {
        Report report = new Report();
        String textReport = message.text();

        PhotoSize[] photos = message.photo();
        if (photos != null && photos.length > 0) {
            PhotoSize photo = photos[photos.length - 1];
            String fileId = photo.fileId();
            String filePath = telegramBot.execute(new GetFile(fileId)).file().filePath();
            String localFilePath = savePhoto(filePath);
            String mediaType = getMediaType(localFilePath);

            report.setDateReport(LocalDate.now());
            report.setTextReport(textReport);
            report.setFilePath(localFilePath);
            report.setFileSize(photo.fileSize());
            report.setMediaType(mediaType);
            report.setIsViewed(false);
            reportRepository.save(report);

        }

        return report;
    }

    private byte[] downloadFile(String filePath) {
        String url = "https://api.telegram.org/file/bot" + telegramBot.getToken() + "/" + filePath;
        try {
            return new URL(url).openStream().readAllBytes();
        } catch (IOException e) {
            logger.error("Ошибка при скачивании файла: {}", e.getMessage());
            return null;
        }
    }

    private String savePhoto(String filePath) {
        try {
            byte[] fileBytes = downloadFile(filePath);
            Path photoPath = Paths.get(photoDir, filePath);
            Files.createDirectories(photoPath.getParent());
            try (FileOutputStream fos = new FileOutputStream(photoPath.toFile())) {
                fos.write(fileBytes);
            }
            return photoPath.toString();
        } catch (IOException e) {
            logger.error("Ошибка при сохранении фото: {}", e.getMessage());
            return null;
        }
    }

    private String getMediaType(String localFilePath) {
        try {
            Path path = Paths.get(localFilePath);
            return Files.probeContentType(path);
        } catch (IOException e) {
            logger.error("Ошибка при определении типа медиа: {}", e.getMessage());
            return "unknown";
        }
    }

}



