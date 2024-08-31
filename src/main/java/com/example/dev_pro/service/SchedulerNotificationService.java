package com.example.dev_pro.service;

public interface SchedulerNotificationService {
    void sendNotificationsWhoNotSendReport();

    void notifyVolunteer();

    void sendCongratulationMessage();

    void notifyAboutAdditionalProbationTime(Long chatId, int days);

    void sendMessage(Long chatId, String msg);

}
