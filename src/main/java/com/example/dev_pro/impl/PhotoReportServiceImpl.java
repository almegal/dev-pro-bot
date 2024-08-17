package com.example.dev_pro.impl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.model.PhotoReport;
import com.example.dev_pro.repository.PhotoReportRepository;
import com.example.dev_pro.service.PhotoReportService;
import com.example.dev_pro.service.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoReportServiceImpl implements PhotoReportService {

    @Value("${path.to.photo.folder}")
    private String photoReportDir;

    private static final Logger logger = LoggerFactory.getLogger(PhotoReportServiceImpl.class);
    private TelegramBot telegramBot;
    private TelegramBotConfiguration tBotConfig;
    private ReportService reportService;
    private PhotoReportRepository photoReportRepository;

    @Override
    public SendMessage handleReport(Long chatId, PhotoSize[] photoSize) {
        logger.info("Вызван метод handleReport у класса PhotoReportServiceImpl");
        if (photoSize != null && photoSize.length > 0) {
            String fileId = photoSize[0].fileId();
            String photoUrl = getPhotoUrl(fileId);

            try {
                PhotoReport photoReport = createPhotoReport(photoUrl);

                // Сохраняем объект в репозитории
                logger.info("Отчет успешно сохранен:");
                photoReportRepository.save(photoReport);
                return new SendMessage(chatId, "Фотография успешно сохранена.");
            } catch (IOException e) {
                e.printStackTrace();
                return new SendMessage(chatId, "Ошибка при получении информации о файле.");
            }
        } else {
            throw new IllegalArgumentException("Сообщение должно содержать фотографию.");
        }
    }

    private PhotoReport createPhotoReport(String photoUrl) throws IOException {
        URL url = new URL(photoUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");


        Long fileSize = connection.getContentLengthLong();
        String mediaType = connection.getContentType();


        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        byte[] imageData = byteArrayOutputStream.toByteArray();

        PhotoReport photoReport = new PhotoReport();
        photoReport.setFilePath(photoUrl);
        photoReport.setFileSize(fileSize);
        photoReport.setMediaType(mediaType);
        photoReport.setData(imageData);
        return photoReport;
    }

    private String getPhotoUrl(String fileId) {
        File file = telegramBot.execute(new GetFile(fileId)).file();

        String filePath = file.filePath();
        return  "https://api.telegram.org/file/bot" + tBotConfig.getTELEGRAM_BOT_TOKEN() + "/" + filePath;
    }

}
