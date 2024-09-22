package com.example.dev_pro.impl;

import com.example.dev_pro.config.TelegramBotConfiguration;
import com.example.dev_pro.model.Adopter;
import com.example.dev_pro.model.TelegramUser;
import com.example.dev_pro.model.Volunteer;
import com.example.dev_pro.service.AdopterService;
import com.example.dev_pro.service.SchedulerNotificationService;
import com.example.dev_pro.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Реализация сервиса планировщика уведомлений для отправки уведомлений усыновителям и волонтерам.
 */
@Service
@RequiredArgsConstructor
public class SchedulerNotificationServiceImpl implements SchedulerNotificationService {

    // Сервис для работы с усыновителями.
    private final AdopterService adopterService;

    // Сервис для работы с волонтерами.
    private final VolunteerService volunteerService;

    // Конфигурация для работы с TelegramBot.
    private final TelegramBotConfiguration telegramBotConfiguration;

    // Экземпляр TelegramBot для отправки сообщений.
    private final TelegramBot bot;

    /**
     * Отправляет уведомления усыновителям, которые не отправили отчет за текущий день.
     * Метод вызывается ежедневно в 21:00.
     */
    @Override
    @Scheduled(cron = "0 0 21 * * ?")
    public void sendNotificationsWhoNotSendReport() {
        // Получаем текущую дату.
        LocalDate localDate = LocalDate.now();

        // Получаем список усыновителей, которые не отправили отчет за текущий день.
        List<Adopter> adopters = adopterService.findAllWhoNotSendReportBeforeDay(localDate);

        // Отправляем каждому усыновителю уведомление через Telegram.
        adopters.forEach(e ->
                sendMessage(e.getTelegramUser().getChatId(), telegramBotConfiguration.getOneDayReportMissing())
        );
    }

    /**
     * Уведомляет волонтеров о том, что некоторые усыновители не отправляли отчет в течение двух дней.
     * Метод вызывается ежедневно в 21:00.
     */
    @Override
    @Scheduled(cron = "0 0 21 * * ?")
    public void notifyVolunteer() {
        // Определяем дату, которая является двумя днями ранее текущей.
        LocalDate localDate = LocalDate.now().minusDays(2);

        // Получаем список усыновителей, которые не отправляли отчет более двух дней.
        List<Adopter> adopters = adopterService.findAllWhoNotSendReportBeforeDay(localDate);

        // Получаем список всех волонтеров.
        Collection<Volunteer> volunteers = volunteerService.findAllVolunteers();

        // Отправляем уведомление волонтеру о каждом усыновителе, который не отправлял отчет два дня.
        adopters.forEach(e -> {
            // Выбираем любого волонтера из списка (можно улучшить логику распределения).
            Volunteer volunteer = volunteers.stream().findAny().get();

            // Получаем информацию о пользователе Telegram.
            TelegramUser telegramUser = e.getTelegramUser();

            // Формируем сообщение для волонтера с информацией об усыновителе.
            String msg = String.format("Этот усыновитель уже два дня не высылает отчет: %s %s, номер телефона %s",
                    telegramUser.getFirstName(), telegramUser.getLastName(), telegramUser.getPhoneNumber());

            // Отправляем сообщение волонтеру.
            sendMessage(volunteer.getChatId(), msg);
        });
    }

    @Override
    @Scheduled(cron = "0 0 21 * * ?")
    public void sendCongratulationMessage() {
        List<Adopter> adopters = adopterService.findAllWhoPasserProbationPeriod();
        adopters.forEach(
                e -> {
                    sendMessage(e.getTelegramUser().getChatId(),
                            "Поздравляем с прошедшим испытательным сроком");
                }
        );
    }

    // Использовать этот метод для оповещения после того, как волонтер установит дополнительное время
    @Override
    public void notifyAboutAdditionalProbationTime(Long chatId, int days) {
        //
        String msg = telegramBotConfiguration.getNotificationAboutAdditionalProbationTime().formatted(days);
        sendMessage(chatId, msg);
    }

    public void sendMessage(Long chatId, String msg) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        bot.execute(sendMessage);

    }
}
