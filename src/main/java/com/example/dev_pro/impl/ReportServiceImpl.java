package com.example.dev_pro.impl;

import com.example.dev_pro.model.Report;
import com.example.dev_pro.service.ReportService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.dev_pro.repository.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository reportRepository;

    @Override
    public SendMessage handleReport(Long chatId, String[] text) {
        logger.info("Вызван метод handleReport у класса ReportServiceImpl");
        if (text == null) {
            return new SendMessage(chatId, "Сообщение пустое. Не сохранено!");
        }

        try {
            Report report = parseReport(text);
            return new SendMessage(chatId, "Успешно сохранено!");
        } catch (IllegalArgumentException e) {
            // Возвращаем сообщение с указанием, какие поля не были заполнены
            return new SendMessage(chatId, e.getMessage());
        }
    }

    private Report parseReport(String[] text) {
        String nameAnimal = null;
        Long mode = null;
        String ration = null;
        Long weight = null;
        String wellBeing = null;

        for (String part : text) {
            String[] keyValue = part.split("-");
            if (keyValue.length != 2) {
                continue;
            }
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            switch (key) {
                case "nameAnimal":
                    nameAnimal = value.isEmpty() ? null : value;
                    break;
                case "mode":
                    mode = value.isEmpty() ? null : Long.parseLong(value);
                    break;
                case "ration":
                    ration = value.isEmpty() ? null : value;
                    break;
                case "weight":
                    weight = value.isEmpty() ? null : Long.parseLong(value);
                    break;
                case "wellBeing":
                    wellBeing = value.isEmpty() ? null : value;
                    break;
                default:
                    break;
            }
        }

        // Формируем сообщение об ошибках
        StringBuilder errorMessage = new StringBuilder("Не все поля заполнены: ");
        boolean hasError = false;

        if (nameAnimal == null) {
            errorMessage.append("nameAnimal, ");
            hasError = true;
        }
        if (mode == null) {
            errorMessage.append("mode, ");
            hasError = true;
        }
        if (ration == null) {
            errorMessage.append("ration, ");
            hasError = true;
        }
        if (weight == null) {
            errorMessage.append("weight, ");
            hasError = true;
        }
        if (wellBeing == null) {
            errorMessage.append("wellBeing, ");
            hasError = true;
        }

        if (hasError) {
            // Удаляем последнюю запятую и пробел
            errorMessage.setLength(errorMessage.length() - 2);
            throw new IllegalArgumentException(errorMessage.toString());
        } else {
            Report report1 = new Report();
            report1.setNameAnimal(nameAnimal);
            report1.setMode(String.valueOf(mode));
            report1.setRation(ration);
            report1.setWeight(weight);
            report1.setWellBeing(wellBeing);
            reportRepository.save(report1);
            logger.info("Отчет успешно сохранен:" + report1);
            return report1;
        }
    }

}


